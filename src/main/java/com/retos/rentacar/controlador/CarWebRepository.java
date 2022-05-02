
package com.retos.rentacar.controlador;

import com.retos.rentacar.modelo.Car;
import com.retos.rentacar.servicios.CarServices;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/Car")
@CrossOrigin(origins = "*", methods={RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class CarWebRepository {
    @GetMapping("/holaMundoCar")
    public String saludad(){
    return "Hola Mundo Car";
    }
    
    @Autowired
    private CarServices serviciosCar;
    @GetMapping("/all")
    public List <Car> getCar(){
        return serviciosCar.getAll();
    }
    @GetMapping("/{id}")
    public Optional<Car> getCar(@PathVariable("id") int id) {
        return serviciosCar.getCar(id);
    }

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public Car save(@RequestBody Car car) {
        return serviciosCar.save(car);
    }
     
    @PutMapping("/update")
    @ResponseStatus(HttpStatus.CREATED)
    public Car update(@RequestBody Car car) {
        return serviciosCar.update(car);
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public boolean delete(@PathVariable ("id") int id) {
        return serviciosCar.deleteCar(id);
    }
    
}
