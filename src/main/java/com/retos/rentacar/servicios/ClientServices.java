
package com.retos.rentacar.servicios;

import com.retos.rentacar.modelo.Entity.Client.Client;
import com.retos.rentacar.repositorio.ClientRepository;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientServices {
    @Autowired
    private ClientRepository metodosCrudClient;

    public List<Client> getAll() {
        return metodosCrudClient.getAll();
    }

    public Optional<Client> getClient(int idClient) {
        return metodosCrudClient.getClient(idClient);
    }

    public Client save(Client client) {
        boolean isOldEnough = isOldEnough(client.getbirthDate());

        if (isOldEnough) {
            return metodosCrudClient.save(client);
        }
        return new Client();

    }

    public Client update(Client client) {
        if (client.getIdClient() != null) {
            Optional<Client> evt = metodosCrudClient.getClient(client.getIdClient());
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

    public boolean deleteClient(int IdClient) {
        return getClient(IdClient).map(client -> {
            metodosCrudClient.delete(client);
            return true;
        }).orElse(false);
    }

    public Optional<Client> login(Client clientToLogin) {

        if(!(clientToLogin.getbirthDate() == null)) {
            if (!isOldEnough(clientToLogin.getbirthDate())) {
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

    public boolean isOldEnough(Date birthDate) {
        Date actualDate = new Date();

        int miliSegundosDia = 24 * 60 * 60 * 1000;

        float miliSegundosTranscurridos = Math.abs(birthDate.getTime() - actualDate.getTime());

        int diasTranscurridos = Math.round(miliSegundosTranscurridos / miliSegundosDia);

        return diasTranscurridos >= 6575;

    }

}
