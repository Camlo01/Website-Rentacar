
package com.retos.rentacar.servicios;

import com.retos.rentacar.interfaces.ClientInterface;
import com.retos.rentacar.modelo.Entity.Client.Client;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.retos.rentacar.modelo.Entity.Client.ClientType;
import com.retos.rentacar.modelo.Entity.Client.KeyClient;
import com.retos.rentacar.modelo.Entity.Reservation.Reservation;
import com.retos.rentacar.repositorio.ClientRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientServices {
    @Autowired
    private ClientRepository metodosCrudClient;
    @Autowired
    private ClientInterface clientInterface;


    // GET

    public List<Client> getAllClients(KeyClient keyClient) {

        if (hasPermissions(keyClient, false)) {
            return metodosCrudClient.getAll();
        }
        return null;
    }

    public Optional<Client> getClientByid(int idCar, KeyClient key) {
        if (hasPermissions(key, false)) {
            return metodosCrudClient.getClientById(idCar);
        }
        return Optional.empty();
    }

    // POST

    public Client saveClient(Client client, KeyClient key) {

        if (hasPermissions(key, false)) {
            if (client.getbirthDate() == null) {
                return metodosCrudClient.save(client);
            }
            if (isOldEnough(client)) {
                return metodosCrudClient.save(client);
            }
            return null;
        }
        return null;
    }

    //PUT

    public Client updateClient(Client client, KeyClient key) {


        if (hasPermissions(key, false)) {
            return update(client);
        }
        return new Client("No fue posible actualizar el cliente");
    }

    //DELETE

    public Boolean deleteClient(int idClient, KeyClient key) {
        if (hasPermissions(key, false)) {
            return metodosCrudClient.getClientById(idClient).map(clientGetted -> {
                metodosCrudClient.delete(clientGetted);
                return true;
            }).orElse(false);
        }
        return false;

    }

    // LOGIN
    public Optional<Client> login(Client clientToLogin) {

        if (!(clientToLogin.getbirthDate() == null)) {
            if (!isOldEnough(clientToLogin)) {
                return Optional.of(new Client("Lo sentimos, tienes que ser mayor de 18 años para crear tu cuenta"));
            }
        }
        try {
            Optional<Client> clientGetted = metodosCrudClient.getClientByEmail(clientToLogin.getEmail());

            boolean hasSameEmail = Objects.equals(clientGetted.get().getEmail(), clientToLogin.getEmail());
            boolean hasSamePassword = Objects.equals(clientGetted.get().getPassword(), clientToLogin.getPassword());

            if (hasSameEmail && hasSamePassword) {
                return clientGetted;
            } else {
                return Optional.of(new Client("Hubo un problema! Verifica que la contraseña sea la correcta"));
            }
        } catch (Exception exception) {
            return Optional.of(new Client("Oops! Verifia que el correo sea el correcto o crea una cuenta"));
        }

    }

    //Resources

    private Client update(Client client) {
        if (client.getIdClient() != null) {
            Optional<Client> evt = metodosCrudClient.getClientById(client.getIdClient());
            if (evt.isPresent()) {
                if (client.getName() != null) {
                    evt.get().setName(client.getName());
                }
                if (client.getEmail() != null) {
                    evt.get().setEmail(client.getEmail());
                }
                if (client.getEmail() != null) {
                    evt.get().setPassword(client.getPassword());
                }
                if (client.getbirthDate() != null) {
                    evt.get().setbirthDate(client.getbirthDate());
                }
                metodosCrudClient.save(evt.get());
            }
        }
        return client;
    }


    //--------- UTILS


    private boolean isOldEnough(Client client) {

        Date birthDate = client.getbirthDate();
        Date actualDate = new Date();
        int miliSegundosDia = 24 * 60 * 60 * 1000;
        float miliSegundosTranscurridos = Math.abs(birthDate.getTime() - actualDate.getTime());
        int diasTranscurridos = Math.round(miliSegundosTranscurridos / miliSegundosDia);
        return diasTranscurridos >= 6575;
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
        Optional<Client> clientToEvaluate = clientInterface.getClientByKeyClient(key);

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
