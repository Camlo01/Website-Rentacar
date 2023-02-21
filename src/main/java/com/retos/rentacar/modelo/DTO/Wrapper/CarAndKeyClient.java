package com.retos.rentacar.modelo.DTO.Wrapper;

import com.retos.rentacar.modelo.DTO.DAO.CarDTO;
import com.retos.rentacar.modelo.Entity.Car.Car;
import com.retos.rentacar.modelo.Entity.Client.KeyClient;

public class CarAndKeyclient {

    private CarDTO car;
    private KeyClient keyClient;


    public CarAndKeyclient() {
    }

    public CarAndKeyclient(CarDTO car, KeyClient keyClient) {
        this.car = car;
        this.keyClient = keyClient;
    }

    public CarDTO getCar() {
        return car;
    }

    public void setCar(CarDTO car) {
        this.car = car;
    }

    public KeyClient getKeyClient() {
        return keyClient;
    }

    public void setKeyClient(KeyClient keyClient) {
        this.keyClient = keyClient;
    }
}
