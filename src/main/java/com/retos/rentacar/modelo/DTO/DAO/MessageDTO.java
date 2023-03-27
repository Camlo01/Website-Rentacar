package com.retos.rentacar.modelo.DTO.DAO;

import com.retos.rentacar.modelo.Entity.Message.Message;

public class MessageDTO {

    private Integer id;
    private String text;
    private Integer idCar;
    private Integer idClient;

    public MessageDTO() {
    }

    public MessageDTO(Message msg) {
        this.id = msg.getId();
        this.text = msg.getMessageText();
        this.idCar = msg.getCar().getId();
        this.idClient = msg.getClient().getId();
    }

    public MessageDTO(Integer id, String text, Integer idCar, Integer idClient) {
        this.id = id;
        this.text = text;
        this.idCar = idCar;
        this.idClient = idClient;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getIdCar() {
        return idCar;
    }

    public void setIdCar(Integer idCar) {
        this.idCar = idCar;
    }

    public Integer getIdClient() {
        return idClient;
    }

    public void setIdClient(Integer idClient) {
        this.idClient = idClient;
    }
}
