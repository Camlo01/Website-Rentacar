
package com.retos.rentacar.servicios;

import com.jayway.jsonpath.Option;
import com.retos.rentacar.modelo.Client;
import com.retos.rentacar.modelo.Gama;
import com.retos.rentacar.repositorio.ClientRepository;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties.ClientType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

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
        if (client.getIdClient() == null) {
            return metodosCrudClient.save(client);
        } else {
            Optional<Client> evt = metodosCrudClient.getClient(client.getIdClient());
            if (evt.isEmpty()) {
                return metodosCrudClient.save(client);
            } else {
                return client;
            }

        }

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
                if (client.getEmail() != null) {
                    evt.get().setAge(client.getAge());
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

}
