/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.retos.rentacar.modelo.Entity.Car;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.retos.rentacar.modelo.Entity.Gama.Gama;
import com.retos.rentacar.modelo.Entity.Message.Message;
import com.retos.rentacar.modelo.Entity.Reservation.Reservation;
import com.retos.rentacar.servicios.GamaServices;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author cjop1
 */
/*
 * Entidad clase vehículo (tabla Car)
 */
@Entity
@Table(name = "car")
public class Car implements Serializable {

    @Transient // This column is ignored
    GamaServices gamaServices;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_car")
    private Integer idCar;
    @Column(name = "name")
    private String name;
    @Column(name = "brand")
    private String brand;
    @Column(name = "year")
    private Integer year;
    @Column(name = "description")
    private String description;
    @Column(name = "car_status")
    private CarStatus carStatus;

    @ManyToOne
    @JoinColumn(name = "id_gama")
    @JsonIgnoreProperties("cars")
    private Gama gama;

    @OneToMany(cascade = { CascadeType.PERSIST }, mappedBy = "car")
    @JsonIgnoreProperties({ "car", "client" })
    private List<Message> messages;

    @OneToMany(cascade = { CascadeType.PERSIST }, mappedBy = "car")
    @JsonIgnoreProperties({ "car", "client" })
    private List<Reservation> reservations;

    public Car(int idCar) {
        this.idCar = idCar;
    }

    public Car(String mensaje) {
        this.name = mensaje;
    }

    public Car() {
        this.name = null;
        this.brand = null;
        this.year = null;
        this.description = null;
    }

    public Car(String name, String brand, Integer year, String description) {
        this.name = name;
        this.brand = brand;
        this.year = year;
        this.description = description;
        this.carStatus = CarStatus.NOT_AVAILABLE;
    }

    public Car(String name, String brand, Integer year, String description, int idGama) {
        this.name = name;
        this.brand = brand;
        this.year = year;
        this.description = description;
        this.gama = gamaServices.getGamaToBuild(idGama);
        this.carStatus = CarStatus.NOT_AVAILABLE;

    }

    public Car(String name, String brand, Integer year, String description, Gama gama) {
        this.name = name;
        this.brand = brand;
        this.year = year;
        this.description = description;
        this.gama = gama;
        this.carStatus = CarStatus.NOT_AVAILABLE;
    }

    public Car(String name, String brand, Integer year, String description, Gama gama, CarStatus status) {
        this.name = name;
        this.brand = brand;
        this.year = year;
        this.description = description;
        this.gama = gama;
        this.carStatus = status;
    }

    public Car(Integer idCar, String name, String brand, Integer year, String description, Gama gama,
            List<Message> messages, List<Reservation> reservations) {
        this.idCar = idCar;
        this.name = name;
        this.brand = brand;
        this.year = year;
        this.description = description;
        this.gama = gama;
        this.messages = messages;
        this.reservations = reservations;
        this.carStatus = CarStatus.NOT_AVAILABLE;
    }

    public Integer getIdCar() {
        return idCar;
    }

    public void setIdCar(Integer idCar) {
        this.idCar = idCar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Gama getGama() {
        return gama;
    }

    public void setGama(Gama gama) {
        this.gama = gama;
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

    public CarStatus getCarStatus() {
        return carStatus;
    }

    public void setCarStatus(CarStatus carStatus) {
        this.carStatus = carStatus;
    }
}
