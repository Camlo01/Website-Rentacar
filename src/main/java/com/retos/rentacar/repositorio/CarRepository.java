package com.retos.rentacar.repositorio;

import com.retos.rentacar.interfaces.CarInterface;
import com.retos.rentacar.modelo.Entity.Car.Car;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CarRepository {
    @Autowired
    private CarInterface crudCar;


    public List<Car> getAll() {
        return (List<Car>) crudCar.findAll();
    }

    public Optional<Car> getCar(int id) {
        return crudCar.findById(id);
    }

    public Optional<Car> getLastCarAdded() {
        return crudCar.getLastCarAdded();
    }

    public Optional<Car> getCarByid(int id) { return crudCar.findById(id); }

    public Optional<Car> getLastCarAddedBookable() {
        return crudCar.getLastCarAddedBookable();
    }

    public Car save(Car car) {
        return crudCar.save(car);
    }

    public void delete(Car car) {
        crudCar.delete(car);
    }

}
