package com.retos.rentacar.modelo.DTO.Wrapper;

import com.retos.rentacar.modelo.Entity.Client.Client;
import com.retos.rentacar.modelo.Entity.Client.KeyClient;

public class ClientAndKeyClient {

    private Client client;
    private KeyClient keyClient;

    public ClientAndKeyClient(Client client, KeyClient key) {
        this.client = client;
        this.keyClient = key;
    }


    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public KeyClient getKeyClient() {
        return keyClient;
    }

    public void setKeyClient(KeyClient key) {
        this.keyClient = key;
    }

    @Override
    public String toString() {
        return "ClientAndKeyClient{" +
                "client=" + client +
                ", keyClient=" + keyClient +
                '}';
    }
}
