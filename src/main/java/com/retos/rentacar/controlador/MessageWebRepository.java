
package com.retos.rentacar.controlador;

import com.retos.rentacar.modelo.DTO.DAO.CarDTO;
import com.retos.rentacar.modelo.DTO.DAO.MessageDTO;
import com.retos.rentacar.modelo.DTO.Wrapper.MessageAndKeyClient;
import com.retos.rentacar.modelo.Entity.Client.KeyClient;
import com.retos.rentacar.modelo.Entity.Message.Message;
import com.retos.rentacar.servicios.MessageServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/message")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
        RequestMethod.DELETE})
public class MessageWebRepository {

    @Autowired
    private MessageServices services;

    @GetMapping("/messages-of-car")
    public ResponseEntity<?> getMessagesOfCar(@RequestBody CarDTO car) {
        List<Message> messages = services.getMessagesOfCar(car.getId());
        if (messages.size() > 0) {
            return new ResponseEntity<>(messages, HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //a client create a message to a car
    @PostMapping("/create-message")
    public ResponseEntity<?> saveMessage(@RequestBody MessageAndKeyClient body) {

        boolean hasPermission = hasPermissions(body.getKey()) || isMessageOwner(body.getMessage(), body.getKey());

        if (hasPermission) {
            Message result = services.saveMessage(body.getMessage());
            if (result.getId() == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } else return new ResponseEntity<>(result, HttpStatus.CREATED);
        } else return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("/edit-message")
    public ResponseEntity<?> editMessage(@RequestBody MessageAndKeyClient body) {
        boolean hasPermission = hasPermissions(body.getKey()) || isMessageOwner(body.getMessage(), body.getKey());

        if (hasPermission) {
            Message result = services.updateMessage(body.getMessage());
            if (result.getId() == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } else return new ResponseEntity<>(result, HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("/delete-message")
    public ResponseEntity<?> deleteMessage(@RequestBody MessageAndKeyClient body) {
        boolean hasPermission = (isMessageOwner(body.getMessage(), body.getKey()) || hasPermissions(body.getKey()));

        if (hasPermission) {
            boolean wasDeleted = services.deleteMessage(body.getMessage());
            return (wasDeleted) ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    }

    //    UTILS ---------------------

    @GetMapping("/{id}")
    public Optional<Message> getMessage(@PathVariable("id") int idMessage) {
        return services.getMessageById(idMessage);
    }

    /**
     * Method in charge of verify if a keyClient from request is the owner of the message
     *
     * @param msg to evaluate the owner
     * @param key of the possible owner
     * @return boolean value
     */
    private boolean isMessageOwner(MessageDTO msg, KeyClient key) {
        return services.isMessageOwner(msg, key);
    }

    /**
     * Method in charge of validate the permissions from the key of who made the request
     *
     * @param key of client to evaluate
     * @return boolean value
     */
    private boolean hasPermissions(KeyClient key) {
        return services.hasPermissions(key);
    }

}
