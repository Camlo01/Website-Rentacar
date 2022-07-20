
package com.retos.rentacar.modelo.Entity.Client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.retos.rentacar.modelo.Entity.Message.Message;
import com.retos.rentacar.modelo.Entity.Reservation.Reservation;

import java.io.Serializable;
import java.sql.Date;

import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "client")
public class Client implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idClient;
    @Column(unique = true)
    private String email;
    private String password;
    private String name;
    private ClientType type;
    private String keyClient;
    private Date birthDate;

    @OneToMany(cascade = {CascadeType.PERSIST}, mappedBy = "client")
    @JsonIgnoreProperties("client")
    private List<Message> messages;

    @OneToMany(cascade = {CascadeType.PERSIST}, mappedBy = "client")
    @JsonIgnoreProperties("client")
    private List<Reservation> reservations;

    public Client() {
    }

    public Client(String email, String password) {
        this.email = email;
        this.password = password;
        this.type = ClientType.CLIENT;
        this.keyClient = new KeyClient().getKeyClient();
        this.birthDate = Date.valueOf("2004-07-01");
    }

    public Client(int idClient) {
        this.idClient = idClient;
    }
    public Client(String email) {
        this.email = email;
    }



    public Client(String name, String email, String password, Date birthDate) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.birthDate = birthDate;
        this.type = ClientType.CLIENT;
        this.keyClient = new KeyClient().getKeyClient();
    }

    public Client(String name, String email, String password, Date birthDate, ClientType clientType) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.birthDate = birthDate;
        this.type = clientType;
        this.keyClient = new KeyClient().getKeyClient();
    }

    
    public Client(String name, String email, String password, Date birthDate, ClientType clientType, String keyClient) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.birthDate = birthDate;
        this.type = clientType;
        this.keyClient = keyClient;
    }

    public Client(Integer idClient, String email, String password, String name, ClientType type, String keyClient, Date birthDate, List<Message> messages, List<Reservation> reservations) {
        this.idClient = idClient;
        this.email = email;
        this.password = password;
        this.name = name;
        this.type = type;
        this.keyClient = keyClient;
        this.birthDate = birthDate;
        this.messages = messages;
        this.reservations = reservations;
    }

    public Integer getIdClient() {
        return idClient;
    }

    public void setIdClient(Integer idClient) {
        this.idClient = idClient;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ClientType getType() {
        return type;
    }

    public void setType(ClientType type) {
        this.type = type;
    }

    public String getKeyClient() {
        return keyClient;
    }

    public Date setbirthDate(Date date) {
        return birthDate;
    }

    public Date getbirthDate() {
        return birthDate;
    }


    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    @Override
    public String toString() {
        return "Client{" +
                "idClient=" + idClient +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", keyClient='" + keyClient + '\'' +
                ", birthDate=" + birthDate +
                ", messages=" + messages +
                ", reservations=" + reservations +
                '}';
    }
}
