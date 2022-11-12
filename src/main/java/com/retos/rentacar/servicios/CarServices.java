
package com.retos.rentacar.servicios;

import com.retos.rentacar.modelo.DTO.DAO.CarDTO;
import com.retos.rentacar.modelo.Entity.Car.Car;
import com.retos.rentacar.modelo.Entity.Car.CarStatus;
import com.retos.rentacar.modelo.Entity.Car.ImageCar;
import com.retos.rentacar.modelo.Entity.Client.KeyClient;
import com.retos.rentacar.modelo.Entity.Gama.Gama;
import com.retos.rentacar.repositorio.CarRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.retos.rentacar.repositorio.GamaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarServices {
    @Autowired
    private CarRepository metodosCrudCar;
    @Autowired
    private ClientServices clientServices;
    @Autowired
    private GamaRepository gamaRepository;

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

    public Car saveVehicle(CarDTO car, KeyClient keyClient) {
        if (clientServices.hasPermissions(keyClient, false)) {
            car.setCarStatus(CarStatus.OCCUPIED);
            return metodosCrudCar.save(new Car(car));
        }
        return null;
    }

    // PUT Method With Authorization

    public Car updateVehicle(CarDTO car, KeyClient key) {
        if (clientServices.hasPermissions(key, true)) {
            return update(new Car(car));
        }
        return new Car("No se pudo actualizar el carro");
    }

    // DELETE Method With Authorization

    // delete vehicle
    public Boolean deleteVehicle(CarDTO car, KeyClient key) {
        if (clientServices.hasPermissions(key, false)) {
            int idOfCarToDelete = car.getId();

            return delete(idOfCarToDelete);
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
        Car evt = metodosCrudCar.getCar(car.getIdCar()).get();
        if (Objects.equals(car.getIdCar(), evt.getIdCar())) {

            if (!Objects.equals(evt.getName(), car.getName())) {
                evt.setName(car.getName());
            }
            if (!Objects.equals(evt.getBrand(), car.getBrand())) {
                evt.setBrand(car.getBrand());
            }
            if (!Objects.equals(evt.getName(), car.getName())) {
                evt.setName(car.getName());
            }
            if (!Objects.equals(evt.getYear(), car.getYear())) {
                evt.setYear(car.getYear());
            }
            if (!Objects.equals(evt.getDescription(), car.getDescription())) {
                evt.setDescription(car.getDescription());
            }
            if (!Objects.equals(evt.getCarStatus(), car.getCarStatus())) {
                evt.setCarStatus(car.getCarStatus());
            }
            if (!Objects.equals(evt.getGama().getIdGama(), car.getGama().getIdGama())){

                // Consulting new gama to set
                Gama gamaToReplace = gamaRepository.getGama(car.getGama().getIdGama()).get();
                System.out.println(gamaToReplace.toString());
                evt.setGama(gamaToReplace);

            }

            metodosCrudCar.save(evt);
        }
        return evt;
    }


}
