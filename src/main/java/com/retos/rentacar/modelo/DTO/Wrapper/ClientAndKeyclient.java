package com.retos.rentacar.modelo.DTO.Wrapper;

import com.retos.rentacar.modelo.Entity.Client.Client;
import com.retos.rentacar.modelo.Entity.Client.KeyClient;

public class ClientAndKeyclient {

    private Client client;
    private KeyClient keyClient;

    public ClientAndKeyclient(Client client, KeyClient keyClient) {
        this.client = client;
        this.keyClient = keyClient;
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

    public void setKeyClient(KeyClient keyClient) {
        this.keyClient = keyClient;
    }

    @Override
    public String toString() {
        return "ClientAndKeyclient{" +
                "client=" + client +
                ", keyClient=" + keyClient +
                '}';
    }
}
