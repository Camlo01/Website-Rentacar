
package com.retos.rentacar.controlador;

import com.retos.rentacar.interfaces.CarInterface;
import com.retos.rentacar.interfaces.ClientInterface;
import com.retos.rentacar.modelo.Car;
import com.retos.rentacar.modelo.CarStatus;
import com.retos.rentacar.modelo.Client;
import com.retos.rentacar.modelo.KeyClient;
import com.retos.rentacar.servicios.CarServices;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/Car")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
        RequestMethod.DELETE})
public class CarWebRepository {

    @Autowired
    private CarServices carServices;
    @Autowired
    private CarInterface carInterface;
    @Autowired
    private ClientInterface awd;

    //--- Peticiones HTTP Fijas

    //- GET
    @GetMapping("/home_cars/size={size}page={page}")
    List<Car> getListCarsToHomePage(@PathVariable("size") int size, @PathVariable("page") int page) {
        return (List<Car>) carInterface.getCarsWithStatusBookable(size, page);
    }

    @GetMapping("/lastCarAdded")
    public Optional<Car> getLastCarAdded() {
        return carServices.getLastCarAdded();
    }

    //- POST

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public Car saveVehicleWithAutoritation(@RequestBody Car car,
                                           @RequestBody KeyClient keyClient) {
        return carServices.saveVehicle(car, keyClient);
    }


    //--- Peticiones HTTP de recurso

    @GetMapping("/all")
    public List<Car> getCar() {
        return carServices.getAll();
    }

    @GetMapping("/{id}")
    public Optional<Car> getCar(@PathVariable("id") int id) {
        return carServices.getCar(id);
    }

    @GetMapping("/pageable")
    List<Car> getCarPageable(@RequestParam int page, @RequestParam int size) {
        return carInterface.findAll(PageRequest.of(page, size)).getContent();
    }

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public Car save(@RequestBody Car car) {
        return carServices.save(car);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.CREATED)
    public Car update(@RequestBody Car car) {
        return carServices.update(car);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        carServices.deleteCar(id);
    }

}
