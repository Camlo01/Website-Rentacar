
package com.retos.rentacar.modelo.Entity.Message;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.retos.rentacar.modelo.Entity.Car.Car;
import com.retos.rentacar.modelo.Entity.Client.Client;

import java.io.Serializable;
import java.util.Optional;

import javax.persistence.*;

@Entity
@Table(name = "message")
public class Message implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_message",nullable = false)
    private Integer idMessage;
    @Column(name = "message_text")
    private String messageText;


    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "car_id")
    private Car car;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;

    public Message() {
    }


    public Message(Integer idMessage, String messageText) {
        this.idMessage = idMessage;
        this.messageText = messageText;
    }

    public Message(String messageText, Car car, Client client) {
        this.messageText = messageText;
        this.car = car;
        this.client = client;
    }

    public Message(Integer idMessage, String messageText, Car car, Client client) {
        this.idMessage = idMessage;
        this.messageText = messageText;
        this.car = car;
        this.client = client;
    }

    public Integer getIdMessage() {
        return idMessage;
    }

    public void setIdMessage(Integer idMessage) {
        this.idMessage = idMessage;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Optional<Message> map(Object object) {
        return null;
    }
}
