
package com.retos.rentacar.servicios;

import com.retos.rentacar.modelo.DTO.DAO.CarDTO;
import com.retos.rentacar.modelo.DTO.DAO.GamaDTO;
import com.retos.rentacar.modelo.Entity.Car.Car;
import com.retos.rentacar.modelo.Entity.Car.CarStatus;
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
    private CarRepository carRepository;
    @Autowired
    private ClientServices clientServices;
    @Autowired
    private GamaRepository gamaRepository;

    // GET Methods Without Authorization

    public List<Car> getAll() {
        return carRepository.getAll();
    }

    public Optional<Car> getLastCarAddedBookable() {
        return carRepository.getLastCarAddedBookable();
    }


    public Optional<Car> getLastCarAdded() {
        return carRepository.getLastCarAdded();
    }

    public Optional<Car> getCar(int idCar) {
        return carRepository.getCar(idCar);
    }

    // POST Method With Authorization

    public Car saveVehicle(CarDTO car, KeyClient keyClient) {
        if (clientServices.hasPermissions(keyClient, false)) {
            if(car.getCarStatus() == null){
                car.setCarStatus(CarStatus.OCCUPIED);
            }
            if(car.getGama() == null){
                Gama emptyGama = gamaRepository.getGama(11).get();
                car.setGama( new GamaDTO(emptyGama));
            }

            return carRepository.save(new Car(car));
        }
        return null;
    }

    // PUT Method With Authorization

    public Car updateVehicle(CarDTO car, KeyClient key) {
        if (clientServices.hasPermissions(key, true)) {
            return update(new Car(car));
        }
        return null;
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
            carRepository.delete(car);
            return true;
        }).orElse(false);
        return aBoolean;
    }

    private Car update(Car car) {
        Car evt = carRepository.getCar(car.getId()).get();
        if (Objects.equals(car.getId(), evt.getId())) {

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
            if (!Objects.equals(evt.getGama().getId(), car.getGama().getId())){

                // Consulting new gama to set
                Gama gamaToReplace = gamaRepository.getGama(car.getGama().getId()).get();
                System.out.println(gamaToReplace.toString());
                evt.setGama(gamaToReplace);

            }

            carRepository.save(evt);
        }
        return evt;
    }


}
