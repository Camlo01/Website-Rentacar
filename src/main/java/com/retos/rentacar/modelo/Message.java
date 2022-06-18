
package com.retos.rentacar.modelo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "message")
public class Message implements Serializable {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer idMessage;
    private String messageText;

    
    @ManyToOne
    @JoinColumn(name ="idCar")
    @JsonIgnoreProperties({"messages", "client", "reservations"})
    private Car car;
    
    @ManyToOne
    @JoinColumn(name ="idClient")
    @JsonIgnoreProperties({"messages", "reservations", "client"})
    private Client client;

    public Message() {
    }

    public Message(String messageText) {
        this.messageText = messageText;
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
}
