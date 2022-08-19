package com.retos.rentacar.controlador;


import com.retos.rentacar.modelo.Entity.Car.Car;
import com.retos.rentacar.modelo.Entity.Client.Client;
import com.retos.rentacar.modelo.Entity.Gama.Gama;
import com.retos.rentacar.modelo.Entity.Message.Message;
import com.retos.rentacar.modelo.Entity.Reservation.Reservation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

@RestController
@RequestMapping("/test")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
        RequestMethod.DELETE})
public class TestWebRepository {

    @GetMapping("/hello")
    String messageText(){
        return "Message received successfully";
    }

    @GetMapping("/null")
    @ResponseStatus(HttpStatus.OK)
    void nullTest(){}

    @GetMapping("/car")
    Car carOfTest(){
        return new Car("name", "brand", 2022, "This is a car of test");
    }

    @GetMapping("/client")
    Client clientOfTest(){
        return new Client("name","email@mail.com", "12345", Date.valueOf("2000-01-01"));
    }

    @GetMapping("/gama")
    Gama gamaOfTest(){
        return new Gama("name", "description of example");
    }

    @GetMapping("/message")
    Message messageOfText(){
        return new Message(null, "message of test");
    }

    @GetMapping("/reservation")
    Reservation reservationOfText(){
        return new Reservation("2000-01-01","2000-01-01");
    }

}

