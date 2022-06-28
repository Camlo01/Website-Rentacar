
package com.retos.rentacar.servicios;

import com.retos.rentacar.modelo.Car;
import com.retos.rentacar.repositorio.CarRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarServices {
    @Autowired
    private CarRepository metodosCrudCar;

    public List<Car> getAll() {
        return metodosCrudCar.getAll();
    }

    public Optional<Car> getCar(int idCar) {
        return metodosCrudCar.getCar(idCar);
    }

    public Car save(Car car) {
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

    public Car update(Car car) {
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

    public boolean deleteCar(int idCar) {
        Boolean aBoolean = getCar(idCar).map(car -> {
            metodosCrudCar.delete(car);
            return true;
        }).orElse(false);
        return aBoolean;
    }

}
