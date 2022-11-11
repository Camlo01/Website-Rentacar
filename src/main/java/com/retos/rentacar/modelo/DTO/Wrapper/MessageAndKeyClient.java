package com.retos.rentacar.modelo.DTO.Wrapper;

import com.retos.rentacar.modelo.DTO.DAO.MessageDTO;
import com.retos.rentacar.modelo.Entity.Client.KeyClient;

public class MessageAndKeyClient {

    private MessageDTO message;
    private KeyClient key;

    public MessageAndKeyClient() {
    }

    public MessageAndKeyClient(MessageDTO message, KeyClient key) {
        this.message = message;
        this.key = key;
    }

    public MessageDTO getMessage() {
        return message;
    }

    public void setMessage(MessageDTO message) {
        this.message = message;
    }

    public KeyClient getKey() {
        return key;
    }

    public void setKey(KeyClient key) {
        this.key = key;
    }
}
