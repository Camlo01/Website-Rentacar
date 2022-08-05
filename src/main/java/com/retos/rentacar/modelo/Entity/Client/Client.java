
package com.retos.rentacar.modelo.Entity.Client;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
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
    @Column(name = "id_client")
    private Integer idClient;
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "name")
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(name = "type", columnDefinition = "ENUM('BOOKABLE','NOT_AVAILABLE','RESTRICTION','REPAIR')")
    private ClientType type;
    @Column(name = "key_client", unique = true)
    private String keyClient;
    @Column(name = "birth_date")
    private Date birthDate;


    @JsonIgnore
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "client")
    private List<Message> messages;


    @JsonIgnore
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "client")
    private List<Reservation> reservations;


    // Constructors

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


    public void setKeyClient(String keyClient) {
        this.keyClient = keyClient;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }


    public List<Message> getMessages() {
        return messages;
    }

//    @JsonSetter
    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

//    @JsonSetter
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
