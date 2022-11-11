package com.retos.rentacar.modelo.DTO.DAO;

import com.retos.rentacar.modelo.Entity.Car.CarStatus;

public class CarDTO {

    private Integer id;
    private String name;
    private String brand;
    private Integer year;
    private String description;
    private CarStatus carStatus;

    public CarDTO() {
    }

    public CarDTO(Integer id, String name, String brand, Integer year, String description, CarStatus carStatus) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.year = year;
        this.description = description;
        this.carStatus = carStatus;
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
}
