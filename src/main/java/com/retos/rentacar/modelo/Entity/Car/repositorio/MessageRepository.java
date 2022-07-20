package com.retos.rentacar.repositorio;

import com.retos.rentacar.interfaces.MessageInterface;
import com.retos.rentacar.modelo.Entity.Message.Message;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MessageRepository {
    @Autowired
    private MessageInterface crudMessage;

    public List<Message> getAll() {
        return (List<Message>) crudMessage.findAll();
    }

    public Optional<Message> getMessage(int id) {
        return crudMessage.findById(id);
    }

    public Message save(Message message) {
        return crudMessage.save(message);
    }

    public void delete(Message message) {
        crudMessage.delete(message);
    }
}
