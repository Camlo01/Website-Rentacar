package com.retos.rentacar.repositorio;

import com.retos.rentacar.interfaces.MessageInterface;
import com.retos.rentacar.modelo.Entity.Message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MessageRepository {
    @Autowired
    private MessageInterface msgInterface;

    public List<Message> getAllMessagesOfCar(int id) {
        return (List<Message>) msgInterface.getAllMessagesOfCar(id);
    }

    public Optional<Message> getMessageById(Integer id) {
        return msgInterface.findById(id);
    }

    public Message save(Message message) {
        return msgInterface.save(message);
    }

    public void delete(Message message) {
        msgInterface.delete(message);
    }
}
