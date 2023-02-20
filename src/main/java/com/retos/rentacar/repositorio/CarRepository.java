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
    private CarInterface carInterface;

    /**
     * Method in charge of return a car by its Id
     *
     * @return Optional of car
     */
    public Optional<Car> getCarById(int id) {
        return carInterface.findById(id);
    }

    /**
     * Method that return all the cars that are in the database
     *
     * @return a List of car
     */
    public List<Car> getAll() {
        return (List<Car>) carInterface.findAll();
    }

    /**
     * Method to get the last car added that is bookable
     *
     * @return Optional of car
     */
    public Optional<Car> getLastCarAddedBookable() {
        return carInterface.getLastCarAddedBookable();
    }

    /**
     * Method to get the last car added no matter its status
     *
     * @return Optional of car
     */
    public Optional<Car> getLastCarAdded() {
        return carInterface.getLastCarAdded();
    }


    /**
     * Method in charge of save a car in the database
     *
     * @param car to save already processed
     * @return the car if was successfully saved
     */
    public Car save(Car car) {
        return carInterface.save(car);
    }

    /**
     * Method in charge of delete a car
     *
     * @param car to delete
     */
    public void delete(Car car) {
        carInterface.delete(car);
    }

    /**
     * Method in charge to delete a car by its Id
     *
     * @param id of car to delete
     */
    public void deleteCarById(int id) {
        carInterface.deleteById(id);
    }
}
