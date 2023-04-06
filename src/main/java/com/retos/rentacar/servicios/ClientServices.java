
package com.retos.rentacar.servicios;

import com.retos.rentacar.interfaces.ClientInterface;
import com.retos.rentacar.modelo.Entity.Client.Client;
import com.retos.rentacar.modelo.Entity.Client.ClientType;
import com.retos.rentacar.modelo.Entity.Client.KeyClient;
import com.retos.rentacar.repositorio.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ClientServices {

    @Autowired
    private ClientRepository repository;
    @Autowired
    private ClientInterface clientInterface;

    /**
     * Method in charge of returning all the users of the database
     *
     * @param key key of who request
     * @return the list if who make the request has permissions
     */
    public List<Client> getAllClientsWithAuthorization(KeyClient key) {
        if (hasPermissions(key, false)) {
            return repository.getAll();
        }
        return null;
    }

    /**
     * Method in charge to return a client by its id
     *
     * @param id of client to find
     * @return client if exists
     */
    public Optional<Client> getClientById(int id) {
        return repository.getClientById(id);
    }

    /**
     * Method in charge of get a Client by KeyClient
     *
     * @param key of client to find
     * @return Optional of Client
     */
    public Optional<Client> getClientByKey(String key) {
        return repository.getClientByKey(key);
    }

    /**
     * Method in charge of return a client by its id
     *
     * @param id of client to find
     * @return Optional of Client
     */
    public Optional<Client> getClientByIdWithAuthorization(int id) {
        return getClientById(id);
    }

    /**
     * Method responsible for creating an account if it meets
     * - the user has the minimum age
     * - the email to use is valid
     * - the password is valid
     *
     * @param clientToCreate account to create
     * @return return the client if the account was created successfully
     */
    public Client createAccount(Client clientToCreate) {
        if (isValidClient(clientToCreate)) {
            clientToCreate.setType(ClientType.CLIENT);
            clientToCreate.setKeyClient(new KeyClient().getKeyClient());
            return repository.save(clientToCreate);
        }
        return null;
    }

    /**
     * Method that allows Admins and Developers to create accounts
     *
     * @param account account to create
     * @return the client if it was successfully saved
     */
    public Client saveClient(Client account) {
        if (isValidClient(account)) {
            account.setKeyClient(new KeyClient().getKeyClient());
            return repository.save(account);
        }
        return null;
    }

    /**
     * Method in charge of updating an account
     * <p>
     * - It only allows updating an account if the person making the request is Admin or Developer
     * or if the person making the request is the owner of the account
     *
     * @param client to update already updated
     * @return the client if was successfully updated
     */
    public Client updateClient(Client client) {
        if (isValidClient(client)) {
            return update(client);
        }
        return null;
    }

    /**
     * Method in charge of deleting an account if the person doing it is the owner
     * or has Admin or Developer permissions
     *
     * @param idClient to delete
     * @return true if it was successfully deleted
     */
    public boolean deleteClient(int idClient) {
        Optional<Client> accountToDelete = repository.getClientById(idClient);
        if (accountToDelete.isPresent()) {
            Client account = accountToDelete.get();
            repository.delete(account);
            return true;
        }
        return false;
    }

    /**
     * Method in charge of managing the login by means of an email and password passed by the Client object
     *
     * @param clientToLogin client with credentials
     * @return full client if credentials were correct
     */
    public Optional<Client> login(Client clientToLogin) {

        if (isValidEmail(clientToLogin)) {

            Optional<Client> clientObtained = repository.getClientByEmail(clientToLogin.getEmail());

            //Check if the account doesn't exist
            if (clientObtained.isEmpty()) {
                return Optional.empty();
            }
            boolean hasSameEmail = Objects.equals(clientObtained.get().getEmail(), clientToLogin.getEmail());
            boolean hasSamePassword = Objects.equals(clientObtained.get().getPassword(), clientToLogin.getPassword());

            if (hasSameEmail && hasSamePassword) {
                return clientObtained;
            }
        }
        return Optional.empty();
    }

    //Resources

    /**
     * Method in charge of updating a client only if it exists in the database
     * <p>
     * - the assigned email is valid
     * - the date of birth is an adult
     * - the password is valid
     *
     * @param clientUpdated client with updated information
     * @return if the client updated successfully, it will be returned
     */
    private Client update(Client clientUpdated) {

        int idOfClientToUpdate = clientUpdated.getId();

        Optional<Client> clientObtained = repository.getClientById(idOfClientToUpdate);

        if (clientObtained.isPresent()) {
            boolean isAnyChange = false;

            Client clientInDB = clientObtained.get();

            if (clientInDB.getType() == ClientType.ADMIN ||
                    clientInDB.getType() == ClientType.DEVELOPER) {
                clientInDB.setType(clientUpdated.getType());
                isAnyChange = true;
            }
            if (isValidEmail(clientUpdated)) {
                clientInDB.setEmail(clientUpdated.getEmail());
                isAnyChange = true;
            }
            if (isOldEnough(clientUpdated)) {
                clientInDB.setBirthDate(clientUpdated.getBirthDate());
                isAnyChange = true;
            }
            if (isValidPassword(clientUpdated)) {
                clientInDB.setPassword(clientUpdated.getPassword());
                isAnyChange = true;
            }
            if (isAnyChange) return repository.save(clientInDB);
        }
        return null;
    }

    //--------- UTILS

    /**
     * Method in charge of verifying that the key of the person making the requests is the
     * same as the client that is sent, this is achieved by verifying both keyClient
     *
     * @param client received
     * @param key    of whom made the query
     * @return true if the key is the same of the client
     */
    public boolean isAccountOwner(Client client, KeyClient key) {
        return (Objects.equals(client.getKeyClient(), key.getKeyClient()));
    }

    /**
     * Method that find user by the Client´s Key to validate if the Type of Client
     * has permissions of ADMIN or DEVELOPER
     *
     * @param keyClientToEvaluate keyClient of the client to validate its permissions
     * @param includeSupport      value to consider in case a Support type client has permissions
     * @return true if type client is ADMIN or DEVELOPER and
     * in case that includeSupport was passed as true, if is SUPPORT
     */
    public boolean hasPermissions(KeyClient keyClientToEvaluate, Boolean includeSupport) {

        Optional<Client> clientObtained = clientInterface.findClientByKeyClient(keyClientToEvaluate.getKeyClient());

        if (clientObtained.isEmpty()) {
            return false;
        } else {

            ClientType type = clientObtained.get().getType();

            if (type == ClientType.CLIENT) {
                return false;
            } else if (type == ClientType.ADMIN || type == ClientType.DEVELOPER) {
                return true;
            } else return includeSupport && type == ClientType.SUPPORT;
        }
    }

    /**
     * Method responsible for validating a password that meets the following requirements
     * <p>
     * - at least one digit
     * - at least one lowercase letter
     * - at least one capital letter
     * - at least one special character
     * - no empty space
     * - size from 8 to 26 characters
     *
     * @param client with the password to validate
     * @return true if it matches the pattern
     */
    private boolean isValidPassword(Client client) {
        Pattern PASSWORD_PATTERN = Pattern.compile("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[*@#$%^&+=])(?=\\S+$).{8,26}$");
        Matcher password = PASSWORD_PATTERN.matcher(client.getPassword());
        return password.find();
    }

    /**
     * Validate if age, email and password of a client are valid
     *
     * @param client to evaluate age and email
     * @return true if both are valid
     */
    private boolean isValidClient(Client client) {
        return (isOldEnough(client) && isValidEmail(client) && isValidPassword(client));
    }

    /**
     * Method in charge of validating if an email exists with a generic verification pattern
     *
     * @param client to verify if it has a valid email
     * @return true the email matches the pattern
     */
    private boolean isValidEmail(Client client) {
        if (client.getEmail() == null) {
            return false;
        }
        Pattern EMAIL_PATTERN = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher email = EMAIL_PATTERN.matcher(client.getEmail());
        return email.find();
    }

    /**
     * validate if the client's age is over 18 years
     *
     * @param client to verify
     * @return true if he is of legal age
     */
    private boolean isOldEnough(Client client) {
        if (client.getBirthDate() == null) {
            return false;
        }
        Date birthDate = client.getBirthDate();
        Date actualDate = new Date();
        int millisecondsPerDay = 24 * 60 * 60 * 1000;
        float milliSecondsElapsed = Math.abs(birthDate.getTime() - actualDate.getTime());
        int daysElapsed = Math.round(milliSecondsElapsed / millisecondsPerDay);
        return daysElapsed >= 6575;
    }


}
