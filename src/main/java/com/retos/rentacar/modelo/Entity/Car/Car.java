/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.retos.rentacar.modelo.Entity.Car;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.retos.rentacar.modelo.Entity.Gama.Gama;
import com.retos.rentacar.modelo.Entity.Message.Message;
import com.retos.rentacar.modelo.Entity.Reservation.Reservation;
import com.retos.rentacar.servicios.GamaServices;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

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
    @Enumerated(EnumType.STRING)
    @Column(name = "car_status", columnDefinition = "ENUM('BOOKABLE','NOT_AVAILABLE','OCCUPIED','RESTRICTION','REPAIR')")
    private CarStatus carStatus;


    //    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "gama_id")
    @JsonIgnoreProperties("cars")
//    @JsonIgnoreProperties({"idGama", "cars"})
    private Gama gama;

//    @JsonIgnore
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "car")
    private List<ImageCar> images;


//    @JsonIgnore
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "car")
    private List<Message> messages;


    @JsonIgnore
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "car")
    private List<Reservation> reservations;

    public Car(int idCar) {
        this.idCar = idCar;
    }

    public Car(String message) {
        this.name = message;
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


    public Car(Integer idCar, String name, String brand, String description, CarStatus carStatus, Gama gama) {
        this.idCar = idCar;
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.carStatus = carStatus;
        this.gama = gama;
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

    public Car(Integer idCar, String name, String brand, Integer year, String description, CarStatus carStatus, Gama gama, List<Message> messages, List<ImageCar> images, List<Reservation> reservations) {
        this.idCar = idCar;
        this.name = name;
        this.brand = brand;
        this.year = year;
        this.description = description;
        this.carStatus = carStatus;
        this.gama = gama;
        this.messages = messages;
        this.images = images;
        this.reservations = reservations;
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

    public List<ImageCar> getImages() {
        return images;
    }

    public void setImages(List<ImageCar> images) {
        this.images = images;
    }
}
