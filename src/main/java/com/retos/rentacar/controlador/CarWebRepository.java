
package com.retos.rentacar.controlador;

import com.retos.rentacar.interfaces.CarInterface;
import com.retos.rentacar.modelo.DTO.Wrapper.CarAndKeyClient;
import com.retos.rentacar.modelo.Entity.Car.Car;
import com.retos.rentacar.modelo.Entity.Client.KeyClient;
import com.retos.rentacar.servicios.CarServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/car")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
        RequestMethod.DELETE})
public class CarWebRepository {

    @Autowired
    private CarServices service;
    @Autowired
    private CarInterface carInterface;

    // - GET

    @GetMapping("/home_cars/size={size}page={page}")
    List<Car> getListCarsToHomePage(@PathVariable("size") int size, @PathVariable("page") int page) {
        return (List<Car>) carInterface.getCarsWithStatusBookable(size, page);
    }

    @GetMapping("/lastCarAddedBookable")
    public Optional<Car> getLastCarAddedBookable() {
        return service.getLastCarAddedBookable();
    }

    @GetMapping("/lastCarAdded")
    public Optional<Car> getLastCarAdded() {
        return service.getLastCarAdded();
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveVehicleWithAuthorization(@RequestBody CarAndKeyClient body) {

        if (hasPermission(body.getKey())) {
            Car carToSave = new Car(body.getCar());
            Car carSaved = service.saveVehicle(carToSave);
            if (carSaved.getId() == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } else return new ResponseEntity<>(carSaved, HttpStatus.CREATED);
        } else return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateVehicle(@RequestBody CarAndKeyClient body) {
        if (hasPermission(body.getKey())) {
            Car car = new Car(body.getCar());
            Car carUpdated = service.updateVehicle(car);
            return new ResponseEntity<>(carUpdated, HttpStatus.CREATED);
        } else return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> deleteVehicle(@RequestBody CarAndKeyClient body) {
        if (hasPermission(body.getKey())) {
            boolean wasDeleted = service.deleteVehicle(body.getCar());
            if (wasDeleted) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/all")
    public List<Car> getCar() {
        return service.getAll();
    }

    @GetMapping("/pageable")
    List<Car> getCarPageable(@RequestParam int page, @RequestParam int size) {
        return carInterface.findAll(PageRequest.of(page, size)).getContent();
    }

    /**
     * Method in charge to evaluate the permissions a client by its keyClient
     *
     * @param key to evaluate
     * @return boolean value
     */
    private boolean hasPermission(KeyClient key) {
        return service.hasPermissions(key);
    }

}
