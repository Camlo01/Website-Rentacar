
package com.retos.rentacar.servicios;

import com.retos.rentacar.interfaces.ClientInterface;
import com.retos.rentacar.modelo.Entity.Client.Client;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.retos.rentacar.modelo.Entity.Client.ClientType;
import com.retos.rentacar.modelo.Entity.Client.KeyClient;
import com.retos.rentacar.repositorio.ClientRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientServices {
    @Autowired
    private ClientRepository crudMethods;
    @Autowired
    private ClientInterface clientInterface;


    // GET

    public List<Client> getAllClients(KeyClient keyClient) {
        if (hasPermissions(keyClient, false)) {
            return crudMethods.getAll();
        }
        return null;
    }
    public Optional<Client> getClientById(int id){
        return crudMethods.getClientById(id);
    }

    public Optional<Client> getClientByIdWithAuthorization(int idCar, KeyClient key) {
        if (hasPermissions(key, false)) {
            return crudMethods.getClientById(idCar);
        }
        return Optional.empty();
    }

    // POST

    public Client createAccount(Client clientToCreate) {
        if (isValidClient(clientToCreate)) {
            clientToCreate.setType(ClientType.CLIENT);
            clientToCreate.setKeyClient(new KeyClient().getKeyClient());
            return crudMethods.save(clientToCreate);
        }
        return new Client("XXXXX", "XXXXX");
    }

    public Client saveClient(Client clientToSave, KeyClient key) {
        if (hasPermissions(key, false)) {

            if (isValidClient(clientToSave)) {
                clientToSave.setType(ClientType.CLIENT);
                clientToSave.setKeyClient(new KeyClient().getKeyClient());
                return crudMethods.save(clientToSave);
            }

            return null;
        }
        return null;
    }

    //PUT

    public Client updateClient(Client client, KeyClient key) {

        if (hasPermissions(key, false) || isAccountOwner(client, key)) {
            return update(client);
        }
        return new Client("No fue posible actualizar el cliente");
    }

    //DELETE

    public Boolean deleteClient(int idClient, KeyClient key) {

        //Try to delete account as administrator
        if (hasPermissions(key, false)) {
            return crudMethods.getClientById(idClient).map(clientGetted -> {
                crudMethods.delete(clientGetted);
                return true;
            }).orElse(false);
        }
        Client deleteMyAccount = crudMethods.getClientById(idClient).get();

        //Delete account as the owner
        if (Objects.equals(deleteMyAccount.getKeyClient(), key.getKeyClient())) {
            return crudMethods.getClientById(idClient).map(clientGetted -> {
                crudMethods.delete(clientGetted);
                return true;
            }).orElse(false);
        }

        return false;

    }

    // LOGIN
    public Optional<Client> login(Client clientToLogin) {

        try {

            //Client consulted by email to verify
            Optional<Client> clientGetted = crudMethods.getClientByEmail(clientToLogin.getEmail());

            boolean hasSameEmail = Objects.equals(clientGetted.get().getEmail(), clientToLogin.getEmail());
            boolean hasSamePassword = Objects.equals(clientGetted.get().getPassword(), clientToLogin.getPassword());

            if (hasSameEmail && hasSamePassword) {
                return clientGetted;
            } else {
                return Optional.of(new Client("Hubo un problema! La contraseña no coincide"));
            }
        } catch (Exception exception) {
            return Optional.of(new Client("Oops! Parece que no tienes una cuenta creada"));
        }

    }

    //Resources

    private Client update(Client client) {
        Optional<Client> evt = crudMethods.getClientById(client.getIdClient());
        if (client.getIdClient() != null) {


            //Keeping the same keyClient
            client.setKeyClient(evt.get().getKeyClient());

            if (evt.isPresent()) {
                if (client.getName() != null || Objects.equals(client.getName(), evt.get().getName())) {
                    evt.get().setName(client.getName());
                }
                if (client.getEmail() != null) {
                    evt.get().setEmail(client.getEmail());
                }
                if (client.getPassword() != null) {
                    evt.get().setPassword(client.getPassword());
                }
                if (client.getBirthDate() != null) {
                    evt.get().setBirthDate(client.getBirthDate());
                }

                // User who's trying change the ClientType need to be ADMIN or DEVELOPER
                if (
                        Objects.equals(evt.get().getType(), ClientType.ADMIN) ||
                                Objects.equals(evt.get().getType(), ClientType.DEVELOPER)
                ) {
                    evt.get().setType(client.getType());
                }
                crudMethods.save(evt.get());
            }
        }
        return evt.get();
    }


    //--------- UTILS


    /**
     * Validate if age and email are valid
     *
     * @param client to evaluate age and email
     * @return true if both are valid
     */
    private boolean isValidClient(Client client) {
        return (isOldEnough(client) && isValidEmail(client));
    }

    /**
     * @param client
     * @return
     */
    private boolean isValidEmail(Client client) {
        //pattern to validate
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        Matcher email = pattern.matcher(client.getEmail());
        return email.find();
    }


    /**
     * validate if the client's age is over 18 years
     *
     * @param client
     * @return
     */
    private boolean isOldEnough(Client client) {
        if (client.getBirthDate() == null) {
            return false;
        }
        Date birthDate = client.getBirthDate();
        Date actualDate = new Date();
        int miliSegundosDia = 24 * 60 * 60 * 1000;
        float miliSegundosTranscurridos = Math.abs(birthDate.getTime() - actualDate.getTime());
        int diasTranscurridos = Math.round(miliSegundosTranscurridos / miliSegundosDia);
        return diasTranscurridos >= 6575;
    }


    /**
     * @param client received
     * @param key of whom made the query
     * @return true if the key is the same of the client
     */
    private Boolean isAccountOwner(Client client, KeyClient key) {
        return (Objects.equals(client.getKeyClient(), key.getKeyClient()));
    }

    /**
     * Method that find user by the Client´s Key to validate if the Type of Client
     * has permissions of ADMIN or DEVELOPER
     *
     * @param toEvaluate
     * @return true if type client is ADMIN or DEVELOPER
     */
    public Boolean hasPermissions(KeyClient toEvaluate, Boolean includeSupport) {
        String key = toEvaluate.getKeyClient();
        Optional<Client> clientToEvaluate = clientInterface.findByKeyClient(key);

        System.out.println(clientToEvaluate.get().toString());
        if (includeSupport) {
            return true;
        }
        if (clientToEvaluate.isPresent()) {
            Client typeClient = clientToEvaluate.get();
            return (typeClient.getType() == ClientType.ADMIN) ||
                    (typeClient.getType() == ClientType.DEVELOPER);
        }
        return false;
    }


}
