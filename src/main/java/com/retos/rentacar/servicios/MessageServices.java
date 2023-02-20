
package com.retos.rentacar.servicios;

import com.retos.rentacar.modelo.DTO.DAO.MessageDTO;
import com.retos.rentacar.modelo.Entity.Client.KeyClient;
import com.retos.rentacar.modelo.Entity.Message.Message;
import com.retos.rentacar.repositorio.MessageRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServices {
    @Autowired
    private MessageRepository metodosCrudMessage;
    @Autowired
    private CarServices carServices;
    @Autowired
    private ClientServices clientServices;


    public List<Message> getMessageOfCar(int id) {
        return metodosCrudMessage.getAllMessagesOfCar(id);
    }


    public Optional<Message> getMessage(int idMessage) {
        return metodosCrudMessage.getMessage(idMessage);
    }

    public Message save(MessageDTO msg) {
        Message newMessage = new Message();
        newMessage.setMessageText(msg.getText());
        newMessage.setCar(carServices.getCarById(msg.getIdCar()).get());
        newMessage.setClient(clientServices.getClientById(msg.getIdClient()).get());
        return metodosCrudMessage.save(newMessage);
    }


    public Message updateMessage(MessageDTO msg, KeyClient key) {
        if (clientServices.hasPermissions(key, false)) {
            return update(msg);
        }
        return null;
    }


    public void deleteMessage(MessageDTO msg, KeyClient key) {
        if (clientServices.hasPermissions(key, false)) {
            delete(msg.getId());
        }
    }

    //  UTILS
    //You just can edit the text of the message
    public Message update(MessageDTO msg) {
        Message evt = getMessage(msg.getId()).get();
        if (!Objects.equals(evt.getMessageText(), msg.getText())) {
            evt.setMessageText(msg.getText());
            return metodosCrudMessage.save(evt);
        }
        return null;
    }

    private boolean delete(int idMessage) {
        Boolean aBoolean = getMessage(idMessage).map(msg -> {
            metodosCrudMessage.delete(msg);
            return true;
        }).orElse(false);

        return aBoolean;
    }

}
