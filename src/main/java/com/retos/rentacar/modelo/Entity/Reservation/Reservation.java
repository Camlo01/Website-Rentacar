
package com.retos.rentacar.modelo.Entity.Reservation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.retos.rentacar.modelo.Entity.Car.Car;
import com.retos.rentacar.modelo.Entity.Client.Client;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "reservation")
public class Reservation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reservation")
    private Integer idReservation;
    @Column(name = "start_date")
    private Date startDate;
    @Column(name = "devolution_date")
    private Date devolutionDate;
    @Column(name = "code")
    private String code;
    @Column(name = "reservation_status", columnDefinition = "ENUM('REQUESTED','ACTIVE','CANCELLED','POSTPONED','DENIED','COMPLETED')")
    private ReservationStatus reservationStatus;

    @ManyToOne
    @JoinColumn(name = "idCar")
    @JsonIgnoreProperties("reservations")
    private Car car;

    @ManyToOne
    @JoinColumn(name = "idClient")
    @JsonIgnoreProperties({"reservations", "messages"})
    private Client client;


    public Reservation() {
    }

    public Reservation(String code) {
        this.code = code;
    }


    public Reservation(int idReservation) {
        this.idReservation = idReservation;
    }

    public Reservation(String startDate, String devolutionDate) {
        this.startDate = java.sql.Date.valueOf(startDate);
        this.devolutionDate = java.sql.Date.valueOf(devolutionDate);
        this.code = new ReservationCode().generateReservationCode();
    }

    public Reservation(Client client, Car car, Date startDate, Date devolutionDate) {
        this.startDate = startDate;
        this.devolutionDate = devolutionDate;
        this.car = car;
        this.client = client;
        this.reservationStatus = ReservationStatus.REQUESTED;
        this.code = new ReservationCode().generateReservationCode();
    }

    public Reservation(Client client, Car car, Date startDate, Date devolutionDate, ReservationStatus status) {
        this.startDate = startDate;
        this.devolutionDate = devolutionDate;
        this.car = car;
        this.client = client;
        this.reservationStatus = status;
        this.code = new ReservationCode().generateReservationCode();
    }

    public Reservation(Integer idReservation, Date startDate, Date devolutionDate, Car car, Client client) {
        this.idReservation = idReservation;
        this.startDate = startDate;
        this.devolutionDate = devolutionDate;
        this.car = car;
        this.client = client;
        this.reservationStatus = ReservationStatus.REQUESTED;
        this.code = new ReservationCode().generateReservationCode();
    }


    // Getters and setters


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getIdReservation() {
        return idReservation;
    }

    public void setIdReservation(Integer idReservation) {
        this.idReservation = idReservation;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getDevolutionDate() {
        return devolutionDate;
    }

    public void setDevolutionDate(Date devolutionDate) {
        this.devolutionDate = devolutionDate;
    }

    public ReservationStatus getReservationStatus() {
        return reservationStatus;
    }

    public void setReservationStatus(ReservationStatus reservationStatus) {
        this.reservationStatus = reservationStatus;
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

    @Override
    public String toString() {
        return "Reservation{" +
                "client=" + client.getKeyClient() +
                '}';
    }
}
