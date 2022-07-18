package com.retos.rentacar.modelo;

public class CarAndKeyclient {

    private Car car;
    private KeyClient keyClient;


    public Car getCar() {
        return car;
    }

    public CarAndKeyclient(Car car, KeyClient keyClient) {
        this.car = car;
        this.keyClient = keyClient;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public KeyClient getKeyClient() {
        return keyClient;
    }


}
