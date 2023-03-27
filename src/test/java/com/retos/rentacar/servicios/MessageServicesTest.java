package com.retos.rentacar.servicios;

import com.retos.rentacar.modelo.Entity.Car.Car;
import com.retos.rentacar.modelo.Entity.Client.Client;
import com.retos.rentacar.modelo.Entity.Message.Message;
import com.retos.rentacar.repositorio.MessageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MessageServicesTest {

    private Message message;
    private Car car;
    private Client client;
    private final String TEXT_LARGE = "Cars have become an essential part of modern society. They provide a means of transportation, allowing people to easily travel from one place to another. Cars come in many different shapes, sizes, and colors, and are available with a variety of features and options. One of the most important aspects of a car is its engine. The engine is responsible for powering the car, and there are many different types of engines available. Some engines are designed for speed, while others are designed for fuel efficiency. The size of the engine also plays a role in its performance, with larger engines typically providing more power. Another important aspect of a car is its design. Cars come in many different styles, from sleek and sporty to rugged and utilitarian. The design of a car can affect its performance";

    @Mock
    MessageRepository repository;
    @Mock
    CarServices carServices;
    @Mock
    ClientServices clientServices;

    @InjectMocks
    MessageServices services;

    @BeforeEach
    public void setUp() {
        message = new Message();
        message.setMessageText("correct text");

        car = new Car();
        car.setId(1);
        client = new Client();
        client.setId(1);

        message.setCar(car);
        message.setClient(client);
    }


}