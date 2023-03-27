package com.retos.rentacar.repositorio;

import com.retos.rentacar.interfaces.ClientInterface;
import com.retos.rentacar.modelo.Entity.Client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ClientRepository {
    @Autowired
    private ClientInterface clientInterface;

    public List<Client> getAll() {
        return (List<Client>) clientInterface.findAll();
    }

    public Optional<Client> getClientByKey(String key) {
        return clientInterface.findClientByKeyClient(key);
    }

    public Optional<Client> getClientById(int id) {
        return clientInterface.findById(id);
    }

    public Client save(Client client) {
        return clientInterface.save(client);
    }

    public void delete(Client client) {
        clientInterface.delete(client);
    }

    public Optional<Client> getClientByEmail(String email) {
        return clientInterface.findClientByEmail(email);
    }
}
