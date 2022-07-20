
package com.retos.rentacar.servicios;

import com.retos.rentacar.interfaces.ClientInterface;
import com.retos.rentacar.modelo.Entity.Car.Car;
import com.retos.rentacar.modelo.Entity.Client.Client;
import com.retos.rentacar.modelo.Entity.Client.ClientType;
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
    private ClientInterface clientInterface;

    // GET Methods Without Authorization

    public List<Car> getAll() {
        return metodosCrudCar.getAll();
    }

    public Optional<Car> getLastCarAdded() {
        return metodosCrudCar.getLastCarAdded();
    }

    public Optional<Car> getCar(int idCar) {
        return metodosCrudCar.getCar(idCar);
    }

    // POST Method With Autorization

    public Car saveVehicle(Car car, KeyClient keyClient) {
        if (hasPermissions(keyClient.getKeyClient())) {
            save(car);
        }
        return car;
    }

    // PUT Method With Autorization

    public Car updateVehicle(Car car, KeyClient key) {
        if (hasPermissions(key.getKeyClient())) {
            return update(car);
        }
        return new Car("No se pudo actualizar el carro");
    }

    // DELETE Method With Autorization

    public Boolean deleteVehicle(int idCar, KeyClient key) {
        if (hasPermissions(key.getKeyClient())) {
            return delete(idCar);
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

    /**
     * Method that find user by the Client´s Key to validate if the Type of Client
     * has permissions of ADMIN or DEVELOPER
     * 
     * @param key
     * @return true if type client is ADMIN or DEVELOPER
     */
    private Boolean hasPermissions(String key) {
        Optional<Client> clientToEvaluate = clientInterface.getClientByKeyClient(key);
        if (clientToEvaluate.isPresent()) {
            Client client = clientToEvaluate.get();
            return (client.getType() == ClientType.ADMIN) ||
                    (client.getType() == ClientType.DEVELOPER);
        }
        return false;
    }

}
