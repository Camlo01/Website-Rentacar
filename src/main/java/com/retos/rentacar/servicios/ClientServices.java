
package com.retos.rentacar.servicios;

import com.retos.rentacar.modelo.Client;
import com.retos.rentacar.repositorio.ClientRepository;
import java.util.List;
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
            if (!evt.isEmpty()) {
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
                return client;
            } else {
                return client;
            }
        } else {
            return client;
        }
    }

    public boolean deleteClient(int IdClient) {
        Boolean aBoolean = getClient(IdClient).map(client -> {
            metodosCrudClient.delete(client);
            return true;
        }).orElse(false);
        return aBoolean;
    }
}
