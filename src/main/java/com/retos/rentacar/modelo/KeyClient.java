package com.retos.rentacar.modelo;

import com.retos.rentacar.interfaces.ClientInterface;
import com.retos.rentacar.servicios.GamaServices;

import javax.persistence.Transient;
import java.util.Optional;
import java.util.Random;

public class KeyClient {

    @Transient //This column is ignored
    ClientInterface clientInterface;


    private String keyClient;

    public KeyClient() {
        this.keyClient = generateKeyClient();
    }

    public String getKeyClient() {
        return keyClient;
    }

    public KeyClient(String keyClient) {
        this.keyClient = keyClient;
    }

    public String generateKeyClient() {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 12; i++) {
            int index = random.nextInt(alphabet.length());
            char randomChar = alphabet.charAt(index);
            sb.append(randomChar);
        }
        return sb.toString();
    }

    public Boolean hasPermissions(String key) {
        Optional<Client> clientToEvaluate = clientInterface.getClientByKeyClient(key);
        if (clientToEvaluate.isPresent()) {
            Client client = clientToEvaluate.get();
            return (client.getType() == ClientType.ADMIN) ||
                    (client.getType() == ClientType.DEVELOPER);
        }
        return false;
    }
}



