
package com.retos.rentacar.controlador;

import com.retos.rentacar.interfaces.CarInterface;
import com.retos.rentacar.modelo.Entity.Car.Car;
import com.retos.rentacar.modelo.DTO.Wrapper.CarAndKeyclient;
import com.retos.rentacar.servicios.CarServices;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/car")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
        RequestMethod.DELETE})
public class CarWebRepository {

    @Autowired
    private CarServices carServices;
    @Autowired
    private CarInterface carInterface;

    // --- Peticiones HTTP Fijas

    // - GET

    @GetMapping("/home_cars/size={size}page={page}")
    List<Car> getListCarsToHomePage(@PathVariable("size") int size, @PathVariable("page") int page) {
        return (List<Car>) carInterface.getCarsWithStatusBookable(size, page);
    }

    @GetMapping("/lastCarAddedBookable")
    public Optional<Car> getLastCarAddedBookable() {
        return carServices.getLastCarAddedBookable();
    }

    @GetMapping("/lastCarAdded")
    public Optional<Car> getLastCarAdded() {
        return carServices.getLastCarAdded();
    }

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public Car saveVehicleWithAuthorization(@RequestBody CarAndKeyclient carAndKeyclient) {
        Car carToSave = new Car(carAndKeyclient.getCar());
        return carServices.saveVehicle(carToSave, carAndKeyclient.getKeyClient());
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.CREATED)
    public Car updateVehicle(@RequestBody CarAndKeyclient body) {
        Car car = new Car(body.getCar());
        return carServices.updateVehicle(car, body.getKeyClient());
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteVehicle(@RequestBody CarAndKeyclient body) {
        carServices.deleteVehicle(body.getCar(), body.getKeyClient());
    }

    @GetMapping("/all")
    public List<Car> getCar() {
        return carServices.getAll();
    }

    @GetMapping("/pageable")
    List<Car> getCarPageable(@RequestParam int page, @RequestParam int size) {
        return carInterface.findAll(PageRequest.of(page, size)).getContent();
    }

}
