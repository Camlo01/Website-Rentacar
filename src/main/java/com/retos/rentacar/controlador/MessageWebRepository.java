
package com.retos.rentacar.controlador;

import com.retos.rentacar.modelo.DTO.DAO.CarDTO;
import com.retos.rentacar.modelo.DTO.DAO.MessageDTO;
import com.retos.rentacar.modelo.DTO.Wrapper.MessageAndKeyClient;
import com.retos.rentacar.modelo.Entity.Message.Message;
import com.retos.rentacar.servicios.CarServices;
import com.retos.rentacar.servicios.MessageServices;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
        RequestMethod.DELETE})
public class MessageWebRepository {

    @Autowired
    private MessageServices services;

    @GetMapping("/messages-of-car")
    public List<Message> getMessagesOfCar(@RequestBody CarDTO car) {
        return services.getMessageOfCar(car.getId());
    }

    //un cliente crea un mensaje a un carro
    @PostMapping("/create-message")
    @ResponseStatus(HttpStatus.CREATED)
    public Message saveMessage(@RequestBody MessageDTO msg) {
        return services.save(msg);
    }

    @PutMapping("/edit-message")
    @ResponseStatus(HttpStatus.CREATED)
    public Message editMessage(@RequestBody MessageAndKeyClient body) {
        return services.updateMessage(body.getMessage(),body.getKey());
    }

    @DeleteMapping("/delete-message")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteMessage(@RequestBody MessageAndKeyClient body) {
        services.deleteMessage(body.getMessage(), body.getKey());
    }

    //    UTILS ---------------------

    @GetMapping("/{id}")
    public Optional<Message> getMessage(@PathVariable("id") int idMessage) {
        return services.getMessage(idMessage);
    }

}
