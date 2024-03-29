package com.retos.rentacar.modelo.DTO.DAO;

import com.retos.rentacar.modelo.Entity.Car.Car;
import com.retos.rentacar.modelo.Entity.Car.CarStatus;
import com.retos.rentacar.modelo.Entity.Gama.Gama;

import java.util.List;

public class CarDTO {

    private Integer id;
    private String name;
    private String brand;
    private Integer year;
    private String description;
    private CarStatus carStatus;
    private GamaDTO gama;

    public CarDTO() {
    }

    public CarDTO(Car car) {
        this.id = car.getId();
        this.name = car.getName();
        this.brand = car.getBrand();
        this.year = car.getYear();
        this.description = car.getDescription();
        this.carStatus = car.getCarStatus();
    }



    public CarDTO(Integer id, String name, String brand, Integer year, String description, CarStatus carStatus) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.year = year;
        this.description = description;
        this.carStatus = carStatus;
    }

    public CarDTO(String name, String brand, Integer year, String description) {
        this.name = name;
        this.brand = brand;
        this.year = year;
        this.description = description;
    }

    public CarDTO(Integer id, String name, String brand, Integer year, String description, CarStatus carStatus, GamaDTO gama) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.year = year;
        this.description = description;
        this.carStatus = carStatus;
        this.gama = gama;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public CarStatus getCarStatus() {
        return carStatus;
    }

    public void setCarStatus(CarStatus carStatus) {
        this.carStatus = carStatus;
    }

    public GamaDTO getGama() {
        return gama;
    }

    public void setGama(GamaDTO gama) {
        this.gama = gama;
    }
}
