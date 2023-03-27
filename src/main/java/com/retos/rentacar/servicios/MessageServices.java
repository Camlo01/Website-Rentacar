
package com.retos.rentacar.servicios;

import com.retos.rentacar.modelo.DTO.DAO.MessageDTO;
import com.retos.rentacar.modelo.Entity.Car.Car;
import com.retos.rentacar.modelo.Entity.Client.Client;
import com.retos.rentacar.modelo.Entity.Client.KeyClient;
import com.retos.rentacar.modelo.Entity.Message.Message;
import com.retos.rentacar.repositorio.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class MessageServices {

    private final int LENGTH_LIMIT = 800;

    @Autowired
    private MessageRepository repository;
    @Autowired
    private CarServices carServices;
    @Autowired
    private ClientServices clientServices;

    /**
     * Method in charge of return all messages of a car by its id
     *
     * @param id of car from which obtain messages
     * @return List of messages
     */
    public List<Message> getMessagesOfCar(int id) {
        return repository.getAllMessagesOfCar(id);
    }

    /**
     * Method in charge of return a Message by its id
     *
     * @param id of message to get
     * @return Optional of Message
     */
    public Optional<Message> getMessageById(Integer id) {
        return repository.getMessageById(id);
    }

    /**
     * Method in charge to verify if a message is valid, in that case, create it
     *
     * @param msg to save
     * @return null if something went wrong
     */
    public Message saveMessage(MessageDTO msg) {
        String text = msg.getText();

        // validate the text doest exceed 800 characters
        if (text.length() > 0 && text.length() <= LENGTH_LIMIT) {
            String[] words = text.split(" ");

            //Validate doesn't include an email or phone number for every word
            for (String word : words) {
                if (isAnEmail(word) || isAnPhoneNumber(word)) {
                    return null;
                }
            }
            return save(msg);
        }
        return null;
    }

    /**
     * Method in charge to validate if who try to update a message has permissions or is the owner
     * in one of both cases, update it
     *
     * @param msg updated
     * @return the message uf was successfully updated
     */
    public Message updateMessage(MessageDTO msg) {
        int textLength = msg.getText().length();

        if (textLength > 0 && textLength <= LENGTH_LIMIT) {
            String[] words = msg.getText().split(" ");

            //Validate doesn't include an email or phone number for every word
            for (String word : words) {
                if (isAnEmail(word) || isAnPhoneNumber(word)) {
                    return null;
                }
            }
            return update(msg);
        }
        return null;
    }

    /**
     * Method in charge to validate if who try to delete the message has permissions or is the owner
     * in one of both cases, delete it
     *
     * @param msg to delete
     * @return true if was successfully deleted
     */
    public boolean deleteMessage(MessageDTO msg) {
        Optional<Message> messageToDelete = getMessageById(msg.getId());
        if (messageToDelete.isPresent()) {
            Message message = messageToDelete.get();
            repository.delete(message);
            return true;
        }
        return false;
    }

    //  UTILS

    /**
     * Method in charge of saving a message assigning the car and the client
     *
     * @param msg DTO to convert a Message with Car and Client
     * @return the object if was successfully saved
     */
    private Message save(MessageDTO msg) {

        if (msg.getIdCar() != null && msg.getIdClient() != null) {
            Optional<Car> car = carServices.getCarById(msg.getIdCar());
            Optional<Client> client = clientServices.getClientById(msg.getIdClient());

            if (car.isPresent() && client.isPresent()) {
                Message newMessage = new Message();
                newMessage.setMessageText(msg.getText());
                newMessage.setCar(car.get());
                newMessage.setClient(client.get());
                return repository.save(newMessage);
            }
        }
        return null;
    }

    /**
     * Method in charge to update a message
     *
     * @param msg updated
     * @return null if something went wrong
     */
    private Message update(MessageDTO msg) {
        Optional<Message> messageInDB = getMessageById(msg.getId());
        if (messageInDB.isPresent()) {
            Message messageDB = messageInDB.get();
            messageDB.setMessageText(msg.getText());
            return repository.save(messageDB);
        }
        return null;
    }

//    Resources

    /**
     * Method in charge to validate if the owner of the message correspond to the passed keyClient
     *
     * @param msg with client assign
     * @param key to validate
     * @return true if the key correspond to the owner of the message
     */
    public boolean isMessageOwner(MessageDTO msg, KeyClient key) {
        Optional<Client> clientOfMessage = clientServices.getClientById(msg.getIdClient());
        Optional<Client> clientOfKey = clientServices.getClientByKey(key.getKeyClient());
        if (clientOfMessage.isPresent() && clientOfKey.isPresent()) {
            return Objects.equals(clientOfMessage.get(), clientOfKey.get());
        }
        return false;
    }

    /**
     * Method in charge of validate the permissions of the keyClient owner
     *
     * @param key to evaluate
     * @return boolean value
     */
    public boolean hasPermissions(KeyClient key) {
        return clientServices.hasPermissions(key, false);
    }

    /**
     * Method in charge to validate if a String is an email
     *
     * @param possibleEmail String to validate
     * @return true in that case
     */
    private boolean isAnEmail(String possibleEmail) {
        Pattern EMAIL_PATTERN = Pattern.compile("^(.+)@(.+)$");
        Matcher matcher = EMAIL_PATTERN.matcher(possibleEmail);
        return matcher.find();
    }

    /**
     * Method in charge to validate if a String is a phone number
     *
     * @param possibleNumber String to validate
     * @return true if correspond
     */
    private boolean isAnPhoneNumber(String possibleNumber) {
        Pattern patron = Pattern.compile("^\\+?[0-9]{10,15}$");
        Matcher matcher = patron.matcher(possibleNumber);
        return matcher.matches();
    }

}
