package com.retos.rentacar.repositorio;

import com.retos.rentacar.interfaces.ClientInterface;
import com.retos.rentacar.modelo.Client;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ClientRepository {
    @Autowired
    private ClientInterface crudClient;

    public List<Client> getAll() {
        return (List<Client>) crudClient.findAll();
    }

    public Optional<Client> getClient(int id) {
        return crudClient.findById(id);
    }

    public Client save(Client client) {
        return crudClient.save(client);
    }

    public void delete(Client client) {
        crudClient.delete(client);
    }

    public Optional<Client> getElementForLoginValidation(String email) {
        return crudClient.findClientByEmail(email);
    }
}
