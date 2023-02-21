package com.retos.rentacar.modelo.DTO.Wrapper;

import com.retos.rentacar.modelo.DTO.DAO.CarDTO;
import com.retos.rentacar.modelo.Entity.Client.KeyClient;

public class CarAndKeyClient {

    private CarDTO car;
    private KeyClient key;

    public CarAndKeyClient(CarDTO car, KeyClient key) {
        this.car = car;
        this.key = key;
    }

    public CarDTO getCar() {
        return car;
    }

    public void setCar(CarDTO car) {
        this.car = car;
    }

    public KeyClient getKey() {
        return key;
    }

    public void setKey(KeyClient key) {
        this.key = key;
    }
}
