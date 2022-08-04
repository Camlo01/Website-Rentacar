package com.retos.rentacar.modelo.DTO.Wrapper;

import com.retos.rentacar.modelo.Entity.Car.Car;
import com.retos.rentacar.modelo.Entity.Client.KeyClient;

public class CarAndKeyclient {

    private Car car;
    private KeyClient keyClient;

    public CarAndKeyclient(Car car, KeyClient keyClient) {
        this.car = car;
        this.keyClient = keyClient;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public KeyClient getKeyClient() {
        return keyClient;
    }

    public void setKeyClient(KeyClient keyClient) {
        this.keyClient = keyClient;
    }
}
