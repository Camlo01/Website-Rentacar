
package com.retos.rentacar.servicios;

import com.retos.rentacar.modelo.Entity.Car.Car;
import com.retos.rentacar.modelo.Entity.Client.KeyClient;
import com.retos.rentacar.repositorio.CarRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarServices {
    @Autowired
    private CarRepository metodosCrudCar;
    @Autowired
    private ClientServices clientServices;

    // GET Methods Without Authorization

    public List<Car> getAll() {
        return metodosCrudCar.getAll();
    }

    public Optional<Car> getLastCarAddedBookable() {
        return metodosCrudCar.getLastCarAddedBookable();
    }


    public Optional<Car> getLastCarAdded() {
        return metodosCrudCar.getLastCarAdded();
    }

    public Optional<Car> getCar(int idCar) {
        return metodosCrudCar.getCar(idCar);
    }

    // POST Method With Authorization

    public Car saveVehicle(Car car, KeyClient keyClient) {
        if (clientServices.hasPermissions(keyClient, false)) {
            save(car);
        }
        return car;
    }

    // PUT Method With Authorization

    public Car updateVehicle(Car car, KeyClient key) {
        if (clientServices.hasPermissions(key, true)) {
            return update(car);
        }
        return new Car("No se pudo actualizar el carro");
    }

    // DELETE Method With Authorization

    public Boolean deleteVehicle(Car car, KeyClient key) {
        if (clientServices.hasPermissions(key,false)) {
            return delete(car.getIdCar());
        }
        return false;
    }

    // UTILS METHODS

    private boolean delete(int idCar) {
        Boolean aBoolean = getCar(idCar).map(car -> {
            metodosCrudCar.delete(car);
            return true;
        }).orElse(false);
        return aBoolean;
    }

    private Car update(Car car) {
        if (car.getIdCar() != null) {
            Optional<Car> evt = metodosCrudCar.getCar(car.getIdCar());
            if (!evt.isEmpty()) {
                if (car.getName() != null) {
                    evt.get().setName(car.getName());
                }
                if (car.getBrand() != null) {
                    evt.get().setBrand(car.getBrand());
                }
                if (car.getYear() != null) {
                    evt.get().setYear(car.getYear());
                }
                if (car.getDescription() != null) {
                    evt.get().setDescription(car.getDescription());
                }
                metodosCrudCar.save(evt.get());
                return car;
            } else {
                return car;
            }
        } else {
            return car;
        }
    }

    private Car save(Car car) {
        if (car.getIdCar() == null) {
            return metodosCrudCar.save(car);
        } else {
            Optional<Car> evt = metodosCrudCar.getCar(car.getIdCar());
            if (evt.isEmpty()) {
                return metodosCrudCar.save(car);
            } else {
                return car;
            }

        }

    }


}
