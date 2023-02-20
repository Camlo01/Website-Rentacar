
package com.retos.rentacar.servicios;

import com.retos.rentacar.modelo.DTO.DAO.CarDTO;
import com.retos.rentacar.modelo.Entity.Car.Car;
import com.retos.rentacar.modelo.Entity.Client.KeyClient;
import com.retos.rentacar.modelo.Entity.Gama.Gama;
import com.retos.rentacar.repositorio.CarRepository;

import java.util.List;
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

    /**
     * Method in charge of return a car by its Id
     *
     * @return Optional of car
     */
    public Optional<Car> getCarById(int idCar) {
        return carRepository.getCarById(idCar);
    }

    /**
     * Method that return all the cars that are in the database
     *
     * @return a List of car
     */
    public List<Car> getAll() {
        return carRepository.getAll();
    }

    /**
     * Method to get the last car added that is bookable
     *
     * @return Optional of car
     */
    public Optional<Car> getLastCarAddedBookable() {
        return carRepository.getLastCarAddedBookable();
    }

    /**
     * Method to get the last car added no matter its status
     *
     * @return Optional of car
     */
    public Optional<Car> getLastCarAdded() {
        return carRepository.getLastCarAdded();
    }

    /**
     * Method in charge to save a vehicle
     *
     * @param car to save
     * @param key of who request
     * @return null if the car could not be saved
     */
    public Car saveVehicle(Car car, KeyClient key) {
        if (clientServices.hasPermissions(key, false)) {
            return carRepository.save(car);
        }
        return null;
    }

    /**
     * Method in charge to update a vehicle
     *
     * @param car updated
     * @param key of who request
     * @return null if could not be updated
     */
    public Car updateVehicle(Car car, KeyClient key) {
        if (clientServices.hasPermissions(key, true)) {
            return update(car);
        }
        return null;
    }

    /**
     * Method in charge to delete a vehicle after verify who request has permissions
     *
     * @param car to delete
     * @param key of who request
     * @return true if was successfully deleted
     */
    public boolean deleteVehicle(CarDTO car, KeyClient key) {
        Optional<Car> vehicleToDeleteObtained = carRepository.getCarById(car.getId());
        if (vehicleToDeleteObtained.isPresent()) {
            if (clientServices.hasPermissions(key, false)) {
                Car vehicleToDelete = vehicleToDeleteObtained.get();
                carRepository.delete(vehicleToDelete);
                return true;
            }
        }
        return false;
    }

    // UTILS METHODS

    /**
     * Method in charge to update a car
     *
     * @param car updated
     * @return null if the car could not be updated
     */
    private Car update(Car car) {

        int idOfCarToUpdate = car.getId();

        Optional<Car> carObtained = carRepository.getCarById(idOfCarToUpdate);

        if (carObtained.isPresent()) {
            Car carInDB = carObtained.get();

            carInDB.setId(car.getId());
            carInDB.setName(car.getName());
            carInDB.setBrand(car.getBrand());
            carInDB.setYear(car.getYear());
            carInDB.setDescription(car.getDescription());
            carInDB.setCarStatus(car.getCarStatus());

            try {
//                Try to set a new Gama
                int newGamaId = car.getGama().getId();
                Gama newGama = gamaRepository.getGamaById(newGamaId).get();
                carInDB.setGama(newGama);

            } catch (NullPointerException ignored) {
            }

            return carRepository.save(carInDB);
        }
        return null;
    }


}
