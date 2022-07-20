package com.retos.rentacar.repositorio;

import com.retos.rentacar.interfaces.ClientInterface;
import com.retos.rentacar.modelo.Entity.Client.Client;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ClientRepository {
    @Autowired
    private ClientInterface queryPetitions;

    public List<Client> getAll() {
        return (List<Client>) queryPetitions.findAll();
    }

    public Optional<Client> getClient(int id) {
        return queryPetitions.findById(id);
    }

    public Client save(Client client) {
        return queryPetitions.save(client);
    }

    public void delete(Client client) {
        queryPetitions.delete(client);
    }

    public Optional<Client> getClientByEmail(String email) {
        return queryPetitions.findClientByEmail(email);
    }
}
