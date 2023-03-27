package com.retos.rentacar.servicios;

import com.retos.rentacar.modelo.DTO.DAO.MessageDTO;
import com.retos.rentacar.modelo.Entity.Car.Car;
import com.retos.rentacar.modelo.Entity.Client.Client;
import com.retos.rentacar.modelo.Entity.Client.ClientType;
import com.retos.rentacar.modelo.Entity.Client.KeyClient;
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

import java.sql.Date;
import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

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

    @DisplayName("getMessagesOfCar() Should return a list")
    @Test
    public void testGetMessagesOfCar() {
        int idOfCar = 1;

        Mockito.when(repository.getAllMessagesOfCar(idOfCar))
                .thenReturn(new ArrayList<Message>());

        Assertions.assertNotNull(services.getMessagesOfCar(idOfCar));
    }

    @DisplayName("getMessageById() Optional should be present")
    @Test
    public void testGetMessageById() {
        Message msg = new Message();
        msg.setId(1);

        int idOfMessage = 1;

        Mockito.when(repository.getMessageById(idOfMessage))
                .thenReturn(Optional.of(msg));

        Assertions.assertNotNull(services.getMessageById(idOfMessage));
    }

    @DisplayName("saveMessage() message include a email")
    @Test
    public void testSaveMessage_IncludeEmail() {
        message.setMessageText("text of message with email test@mail.com");

        Assertions.assertNull(services.saveMessage(new MessageDTO(message)));
    }

    @DisplayName("saveMessage() message include a phone number with +")
    @Test
    public void testSaveMessage_IncludePhoneNumber() {
        message.setMessageText("text of message with a phone number +3229374263");

        Assertions.assertNull(services.saveMessage(new MessageDTO(message)));
    }

    @DisplayName("saveMessage() message include a phone number without +")
    @Test
    public void testSaveMessage_IncludePhoneNumberWithoutPlus() {
        message.setMessageText("text of message with a phone number 3229374263");

        Assertions.assertNull(services.saveMessage(new MessageDTO(message)));
    }

    @DisplayName("saveMessage() text exceeds 800 characters")
    @Test
    public void testSaveMessage_LargeMessage() {
        message.setMessageText(TEXT_LARGE);

        Assertions.assertNull(services.saveMessage(new MessageDTO(message)));
    }

    @DisplayName("saveMessage() with empty text")
    @Test
    public void testSaveMessage_EmptyMessage() {
        message.setMessageText("");
        Assertions.assertNull(services.saveMessage(new MessageDTO(message)));
    }

    @DisplayName("saveMessage() without a client")
    @Test
    public void testSaveMessage_WithoutClient() {
        client.setId(null);
        message.setClient(client);

        Assertions.assertNull(services.saveMessage(new MessageDTO(message)));
    }

    @DisplayName("saveMessage() without a car")
    @Test
    public void testSaveMessage_WithoutCar() {
        message.setCar(new Car());
        Assertions.assertNull(services.saveMessage(new MessageDTO(message)));
    }

    @DisplayName("saveMessage() correctly created")
    @Test
    public void testSaveMessage_CorrectlyCreated() {
        message.setMessageText("a message about a car");

        Mockito.when(carServices.getCarById(message.getCar().getId()))
                .thenReturn(Optional.of(car));

        Mockito.when(clientServices.getClientById(message.getClient().getId()))
                .thenReturn(Optional.of(client));

        Mockito.when(repository.save(any(Message.class))).thenReturn(new Message());


        Assertions.assertNotNull(services.saveMessage(new MessageDTO(message)));
    }

    @DisplayName("updateMessage() empty text")
    @Test
    public void testUpdateMessage_EmptyText() {
        message.setMessageText("");

        Assertions.assertNull(services.updateMessage(new MessageDTO(message), new KeyClient("OWNER")));
    }

    @DisplayName("updateMessage() text exceeds 800 characters")
    @Test
    public void testUpdateMessage_LargeText() {
        message.setMessageText(TEXT_LARGE);

        Assertions.assertNull(services.updateMessage(new MessageDTO(message), new KeyClient("OWNER")));
    }

    @DisplayName("updateMessage() message include an email")
    @Test
    public void testUpdateMessage_IncludeEmail() {
        message.setMessageText("Message that include an email test@mail.com");

        Assertions.assertNull(services.updateMessage(new MessageDTO(message), new KeyClient("OWNER")));
    }

    @DisplayName("updateMessage() message include a phone number")
    @Test
    public void testUpdateMessage_IncludePhoneNumber() {
        message.setMessageText("Message that include a phone number +118472974823 awd adw adw ");

        Assertions.assertNull(services.updateMessage(new MessageDTO(message), new KeyClient("OWNER")));
    }

    @DisplayName("updateMessage() correct text and author is who try to update it")
    @Test
    public void testUpdateMessage_correctTextAndBeingTheAuthor() {
        message.setMessageText("Message correctly created");
        KeyClient whoRequest = new KeyClient("OWNER");
        Client author = new Client("Matt", "Matt@mail.com", "V01s*49cIqDN", Date.valueOf("2000-01-01"));
        author.setId(99);
        author.setKeyClient(whoRequest.getKeyClient());
        message.setClient(author);

        MessageDTO msg = new MessageDTO(message);

        //validation of permissions
        Mockito.when(clientServices.hasPermissions(whoRequest, false))
                .thenReturn(false);

        Mockito.when(clientServices.getClientById(msg.getIdClient()))
                .thenReturn(Optional.of(author));

        Mockito.when(clientServices.getClientByKey(whoRequest.getKeyClient()))
                .thenReturn(Optional.of(author));

        //Message in DB
        Mockito.when(repository.getMessageById(msg.getId())).thenReturn(Optional.of(new Message()));

        //Finally return the message
        Mockito.when(repository.save(any(Message.class))).thenReturn(message);

        Assertions.assertNotNull(services.updateMessage(msg, whoRequest));
    }

    @DisplayName("updateMessage() correct text and someone other than the owner is who try to update it")
    @Test
    public void testUpdateMessage_correctTextAndDifferentPerson() {
        message.setMessageText("Message with text updated");

        KeyClient whoRequest = new KeyClient("SOM3BODY_ELSE");
        Client clientWhoRequest = new Client("Roger", "Roeger14@mail.com", "yH211@rW&eBQ", Date.valueOf("2000-01-01"));
        clientWhoRequest.setId(99);
        clientWhoRequest.setKeyClient(whoRequest.getKeyClient());

        MessageDTO msg = new MessageDTO(message);

        //validation of permissions
        Mockito.when(clientServices.hasPermissions(whoRequest, false))
                .thenReturn(false);

        Mockito.when(clientServices.getClientById(msg.getIdClient()))
                .thenReturn(Optional.of(client));

        Mockito.when(clientServices.getClientByKey(whoRequest.getKeyClient()))
                .thenReturn(Optional.of(clientWhoRequest));

        Assertions.assertNull(services.updateMessage(msg, whoRequest));
    }

    @DisplayName("updateMessage() correct text and Admin is who update it")
    @Test
    public void testUpdateMessage_correctTextAndAdmin() {
        message.setMessageText("Message correctly created");
        MessageDTO msg = new MessageDTO(message);

        Client whoUpdatedIt = new Client("Louis", "louiusilli@mail.com", "t4I46$!XWX87O", Date.valueOf("2000-01-01"));
        whoUpdatedIt.setId(7);
        whoUpdatedIt.setType(ClientType.ADMIN);
        KeyClient whoRequest = new KeyClient(whoUpdatedIt.getKeyClient());

        //validation of permissions
        Mockito.when(clientServices.hasPermissions(whoRequest, false))
                .thenReturn(true);

        //Message in DB
        Mockito.when(repository.getMessageById(msg.getId())).thenReturn(Optional.of(new Message()));

        //Finally return the message
        Mockito.when(repository.save(any(Message.class))).thenReturn(message);

        Assertions.assertNotNull(services.updateMessage(msg, whoRequest));
    }

    @DisplayName("updateMessage() correct text and Support is who update it")
    @Test
    public void testUpdateMessage_correctTextAndSupport() {
        message.setMessageText("Message correctly created");
        MessageDTO msg = new MessageDTO(message);
        KeyClient keySupport = new KeyClient("SUPPORT");
        client.setKeyClient(keySupport.getKeyClient());

        //validation of permissions
        Mockito.when(clientServices.hasPermissions(keySupport, false))
                .thenReturn(false);

        Mockito.when(clientServices.getClientById(msg.getIdClient()))
                .thenReturn(Optional.of(client));

        //to validate if is the owner
        Mockito.when(clientServices.getClientByKey(keySupport.getKeyClient()))
                .thenReturn(Optional.of(client));

        Assertions.assertNull(services.updateMessage(msg, keySupport));
    }

    @DisplayName("updateMessage() try to update a message that doesn't exist")
    @Test
    public void testUpdateMessage_textDoesNotExist() {
        message.setMessageText("Message correctly created");
        KeyClient key = new KeyClient(client.getKeyClient());
        MessageDTO msg = new MessageDTO(message);

        //validation of permissions
        Mockito.when(clientServices.hasPermissions(key, false))
                .thenReturn(false);

        Mockito.when(clientServices.getClientById(msg.getIdClient()))
                .thenReturn(Optional.of(client));

        //to validate if is the owner
        Mockito.when(clientServices.getClientByKey(key.getKeyClient()))
                .thenReturn(Optional.of(client));

        //Message in DB
        Mockito.when(repository.getMessageById(msg.getId())).thenReturn(Optional.empty());

        Assertions.assertNull(services.updateMessage(msg, key));
    }

    @DisplayName("deleteMessage() delete a message that doesn't exist")
    @Test
    public void testDeleteMessage_messageThatDoesNotExist() {
        message.setMessageText("Message");
        MessageDTO msg = new MessageDTO(message);
        KeyClient keyOwner = new KeyClient(client.getKeyClient());

        Mockito.when(repository.getMessageById(msg.getId()))
                .thenReturn(Optional.empty());

        Assertions.assertFalse(services.deleteMessage(msg, keyOwner));
    }

    @DisplayName("deleteMessage() delete a message as owner")
    @Test
    public void testDeleteMessage_deletingAsOwner() {
        message.setMessageText("Message");
        MessageDTO msg = new MessageDTO(message);

        KeyClient keyOwner = new KeyClient(client.getKeyClient());

        //Find message in DB
        Mockito.when(repository.getMessageById(msg.getId()))
                .thenReturn(Optional.of(message));

        //validation of permissions
        Mockito.when(clientServices.hasPermissions(keyOwner, false))
                .thenReturn(false);

        Mockito.when(clientServices.getClientById(msg.getIdClient()))
                .thenReturn(Optional.of(client));

        //to validate if is the owner
        Mockito.when(clientServices.getClientByKey(keyOwner.getKeyClient()))
                .thenReturn(Optional.of(client));

        Assertions.assertTrue(services.deleteMessage(msg, keyOwner));
    }

    @DisplayName("deleteMessage() delete a message as Admin")
    @Test
    public void testDeleteMessage_deletingAsAdmin() {
        message.setMessageText("Message");
        MessageDTO msg = new MessageDTO(message);
        KeyClient keyAdmin = new KeyClient(client.getKeyClient());

        //Find message in DB
        Mockito.when(repository.getMessageById(msg.getId()))
                .thenReturn(Optional.of(message));

        //validation of permissions
        Mockito.when(clientServices.hasPermissions(keyAdmin, false))
                .thenReturn(true);

        Assertions.assertTrue(services.deleteMessage(msg, keyAdmin));
    }

    @DisplayName("deleteMessage() delete a message as Support")
    @Test
    public void testDeleteMessage_deletingAsSupport() {
        message.setMessageText("Message");
        MessageDTO msg = new MessageDTO(message);
        KeyClient keySupport = new KeyClient("SUPP0RT");

        Client clientSupport = new Client("Matt", "Matt@mail.com", "V01s*49cIqDN", Date.valueOf("2000-01-01"));
        clientSupport.setType(ClientType.SUPPORT);

        //Find message in DB
        Mockito.when(repository.getMessageById(msg.getId())).thenReturn(Optional.of(message));

        //Validation of permissions
        Mockito.when(clientServices.hasPermissions(keySupport, false))
                .thenReturn(false);

        Mockito.when(clientServices.getClientById(msg.getIdClient()))
                .thenReturn(Optional.of(client));

        Mockito.when(clientServices.getClientByKey(keySupport.getKeyClient()))
                .thenReturn(Optional.of(clientSupport));

        Assertions.assertFalse(services.deleteMessage(msg, keySupport));
    }

    @DisplayName("deleteMessage() delete a message as someone other than the autor")
    @Test
    public void testDeleteMessage_deletingAsSomeoneElse() {
        message.setMessageText("Message");
        MessageDTO msg = new MessageDTO(message);
        KeyClient key = new KeyClient(client.getKeyClient());
        Client someOther = new Client("Unknown", "Unknown@mail.com", "p#08xY7AGyopW", Date.valueOf("2000-01-01"));

        //Find message in DB
        Mockito.when(repository.getMessageById(msg.getId())).thenReturn(Optional.of(message));

        //validation of permissions
        Mockito.when(clientServices.hasPermissions(key, false))
                .thenReturn(false);

        //To validate if is the owner
        Mockito.when(clientServices.getClientById(msg.getIdClient()))
                .thenReturn(Optional.of(client));

        Mockito.when(clientServices.getClientByKey(key.getKeyClient()))
                .thenReturn(Optional.of(someOther));

        Assertions.assertFalse(services.deleteMessage(msg, key));
    }
}