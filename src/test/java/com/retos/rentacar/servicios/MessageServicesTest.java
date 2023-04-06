package com.retos.rentacar.servicios;

import com.retos.rentacar.modelo.DTO.DAO.MessageDTO;
import com.retos.rentacar.modelo.Entity.Car.Car;
import com.retos.rentacar.modelo.Entity.Client.Client;
import com.retos.rentacar.modelo.Entity.Message.Message;
import com.retos.rentacar.repositorio.MessageRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class MessageServicesTest {

    private Message message;
    private Car car;
    private Client client;
    private final String LARGE_TEXT = "Cars have become an essential part of modern society. They provide a means of transportation, allowing people to easily travel from one place to another. Cars come in many different shapes, sizes, and colors, and are available with a variety of features and options. One of the most important aspects of a car is its engine. The engine is responsible for powering the car, and there are many different types of engines available. Some engines are designed for speed, while others are designed for fuel efficiency. The size of the engine also plays a role in its performance, with larger engines typically providing more power. Another important aspect of a car is its design. Cars come in many different styles, from sleek and sporty to rugged and utilitarian. The design of a car can affect its performance";

    private List<Message> listOfMessages;

    @Mock
    MessageRepository repository;

    @InjectMocks
    MessageServices service;

    @BeforeEach
    public void setUp() {
        message = new Message();
        message.setId(1);
        message.setMessageText("correct text");

        car = new Car();
        car.setId(1);
        client = new Client();
        client.setId(1);

        message.setCar(car);
        message.setClient(client);

        listOfMessages = new ArrayList<>();
        listOfMessages.add(message);
    }

    @DisplayName("getMessagesOfCar() should return empty if the car does not exist")
    @Test
    public void getMessagesOfCar_thatDoesNotExist() {
        int idOfCarFromWhichGetMessages = 99;

        Mockito.when(repository.getAllMessagesOfCar(idOfCarFromWhichGetMessages)).thenReturn(new ArrayList<>());
        Assertions.assertEquals(0, service.getMessagesOfCar(idOfCarFromWhichGetMessages).size());
    }

    @DisplayName("getMessagesOfCar() should not return empty if the car exist")
    @Test
    public void getMessagesOfCar_thatExist() {
        int idOfCarFromWhichGetMessages = 1;

        Mockito.when(repository.getAllMessagesOfCar(idOfCarFromWhichGetMessages)).thenReturn(listOfMessages);
        Assertions.assertEquals(1, service.getMessagesOfCar(idOfCarFromWhichGetMessages).size());
    }

    @DisplayName("getMessageById() should return empty if the message does not exist")
    @Test
    public void getMessageById_ShouldNotReturnNullIfExist() {
        int idOfMessageToFind = 99;

        Mockito.when(repository.getMessageById(idOfMessageToFind)).thenReturn(Optional.empty());
        Assertions.assertEquals(Optional.empty(), service.getMessageById(idOfMessageToFind));
    }

    @DisplayName("getMessageById() should return the message if exist")
    @Test
    public void getMessageById_ShouldReturnNullIWithNoneExistMessage() {
        int idOfMessageToFind = 1;

        Mockito.when(repository.getMessageById(idOfMessageToFind)).thenReturn(Optional.of(message));
        Assertions.assertNotEquals(Optional.empty(), service.getMessageById(idOfMessageToFind));
    }

    @DisplayName("saveMessage() if the message field is empty")
    @Test
    public void saveMessage_emptyMessage() {
        MessageDTO messageDTO = new MessageDTO(1, "", 1, 1);
        Assertions.assertNull(service.saveMessage(messageDTO));
    }

    @DisplayName("saveMessage() if it includes an email")
    @Test
    public void saveMessage_IncludeEmail() {
        MessageDTO messageDTO = new MessageDTO(1, "This is a message that include an email validmail@gmail.com", 1, 1);
        Assertions.assertNull(service.saveMessage(messageDTO));
    }

    @DisplayName("saveMessage() if it includes a phone number")
    @Test
    public void saveMessage_includePhoneNumber() {
        MessageDTO messageDTO = new MessageDTO(1, "This is a message that include an phone number +573221834715", 1, 1);
        Assertions.assertNull(service.saveMessage(messageDTO));
    }

    @DisplayName("saveMessage() if its longer that 800 characters")
    @Test
    public void saveMessage_longerThanEightHundredCharacters() {
        MessageDTO messageDTO = new MessageDTO(1, LARGE_TEXT, 1, 1);
        Assertions.assertNull(service.saveMessage(messageDTO));
    }

    @DisplayName("saveMessage() save if it created successfully")
    @Test
    public void saveMessage_successfullyCreated() {
        MessageDTO messageDTO = new MessageDTO(1, LARGE_TEXT, 1, 1);
        Assertions.assertEquals(1, service.saveMessage(messageDTO).getId());
    }

    @DisplayName("updateMessage() if the message field is empty")
    @Test
    public void updateMessage_emptyMessage() {
        MessageDTO messageDTO = new MessageDTO(1, "", 1, 1);
        Assertions.assertNull(service.updateMessage(messageDTO));
    }

    @DisplayName("updateMessage() it if the message includes an email")
    @Test
    public void updateMessage_includeEmail() {
        MessageDTO messageDTO = new MessageDTO(1, "This is a message that include an email validmail@gmail.com", 1, 1);
        Assertions.assertNull(service.updateMessage(messageDTO));
    }

    @DisplayName("updateMessage() if it includes a phone number")
    @Test
    public void updateMessage_includePhoneNumber() {
        MessageDTO messageDTO = new MessageDTO(1, "This is a message that include an phone number +573221834715", 1, 1);
        Assertions.assertNull(service.updateMessage(messageDTO));
    }

    @DisplayName("updateMessage()if its longer than 800 characters")
    @Test
    public void updateMessage_longerThanEightHundredCharacters() {
        MessageDTO messageDTO = new MessageDTO(1, LARGE_TEXT, 1, 1);
        Assertions.assertNull(service.updateMessage(messageDTO));
    }

    @DisplayName("updateMessage() if it created successfully")
    @Test
    public void updateMessage_successfullyUpdated() {
        MessageDTO messageDTO = new MessageDTO(1, "Message created successfully", 1, 1);
        Assertions.assertEquals(1, service.updateMessage(messageDTO).getId());
    }

    @DisplayName("deleteMessage() if the message does not exist")
    @Test
    public void deleteMessage_thatDoesNotExist() {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setId(1);
        Mockito.when(repository.getMessageById(messageDTO.getId())).thenReturn(Optional.empty());

        Assertions.assertFalse(service.deleteMessage(messageDTO));
    }

    @DisplayName("deleteMessage() if exist")
    @Test
    public void deleteMessage_thatExist() {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setId(1);
        Mockito.when(repository.getMessageById(messageDTO.getId()))
                .thenReturn(Optional.of(new Message()));

        Assertions.assertTrue(service.deleteMessage(messageDTO));
    }

}