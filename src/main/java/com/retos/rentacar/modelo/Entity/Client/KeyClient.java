package com.retos.rentacar.servicios.modelo.Entity.Client;

import com.retos.rentacar.interfaces.ClientInterface;

import javax.persistence.Transient;
import java.util.Random;

public class KeyClient {

    private String keyClient;

    public KeyClient(String keyClient) {
        this.keyClient = keyClient;
    }

    public KeyClient() {
        this.keyClient = generateKeyClient();
    }

    public String getKeyClient() {
        return keyClient;
    }

    public void setKeyClient(String keyClient) {
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

    @Override
    public String toString() {
        return "KeyClient{" +
                "keyClient='" + keyClient + '\'' +
                '}';
    }
}



