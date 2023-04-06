package com.retos.rentacar.servicios;

import com.retos.rentacar.modelo.Entity.Client.Client;
import com.retos.rentacar.modelo.Entity.Client.ClientType;
import com.retos.rentacar.modelo.Entity.Client.KeyClient;
import com.retos.rentacar.repositorio.ClientRepository;
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
public class ClientServicesTest {

    @Mock
    private ClientRepository repository;

    @InjectMocks
    private ClientServices service;

    private Client clientClient, clientSupport, clientAdmin, clientDeveloper, noneClient;

    private ArrayList<Client> arrayOfClients = new ArrayList<>();

    @BeforeEach
    void setUp() {
        clientClient = new Client(1, "CL13NT3", "client", "cliente@mail.com", "12345*", Date.valueOf("2000-01-01"), ClientType.CLIENT);
        clientSupport = new Client(2, "SUPP0RT", "support", "support@mail.com", "12345*", Date.valueOf("2000-01-01"), ClientType.SUPPORT);
        clientAdmin = new Client(3, "4DM1N", "admin", "admin@mail.com", "12345*", Date.valueOf("2000-01-01"), ClientType.ADMIN);
        clientDeveloper = new Client(4, "D3V3L0P3R", "developer", "developer@mail.com", "12345*", Date.valueOf("2000-01-01"), ClientType.DEVELOPER);
        noneClient = new Client(5);

        arrayOfClients.add(clientClient);
        arrayOfClients.add(clientSupport);
        arrayOfClients.add(clientAdmin);
        arrayOfClients.add(clientDeveloper);
        arrayOfClients.add(noneClient);
    }


    @DisplayName("getClientById() should return the client if exist")
    @Test
    public void getClientById_clientThatExist() {
        int idOfClientToFind = 1;

        Mockito.when(repository.getClientById(idOfClientToFind))
                .thenReturn(Optional.of(clientClient));
        Assertions.assertEquals(clientClient, service.getClientById(idOfClientToFind).get());
    }

    @DisplayName("getClientById() should return null if the client does not exist")
    @Test
    public void getClientById_clientDoesNotExist() {
        int idOfClientToFind = 99;

        Mockito.when(repository.getClientById(idOfClientToFind))
                .thenReturn(Optional.empty());
        Assertions.assertEquals(Optional.empty(), service.getClientById(idOfClientToFind));
    }

    @DisplayName("getClientByKey() should return the client if exist")
    @Test
    public void getClientByKey_clientThatExist() {
        KeyClient keyOfClientToFind = new KeyClient(clientClient.getKeyClient());

        Mockito.when(repository.getClientByKey(keyOfClientToFind.getKeyClient()))
                .thenReturn(Optional.of(clientClient));
        Assertions.assertEquals(clientClient, service.getClientByKey(keyOfClientToFind.getKeyClient()).get());
    }

    @DisplayName("getClientByKey() should return null if the client does not exits")
    @Test
    public void getClientByKey_clientThatDoesNotExist() {
        KeyClient keyOfClientToFind = new KeyClient("QWERTY");

        Mockito.when(repository.getClientByKey(keyOfClientToFind.getKeyClient()))
                .thenReturn(Optional.empty());
        Assertions.assertEquals(Optional.empty(), service.getClientByKey(keyOfClientToFind.getKeyClient()));
    }

    @DisplayName("createAccount() with a client correctly created")
    @Test
    public void createAccount_correctlyCreated() {
        Client accountCreated = new Client("Matt Murdock", "mattrianho@gmail.com", "uP710&cLNj7$a", Date.valueOf("2001-01-01"));

        Mockito.when(repository.save(accountCreated)).thenReturn(accountCreated);
        Assertions.assertEquals(accountCreated, service.createAccount(accountCreated));
    }

    @DisplayName("createAccount() should create the client as CLIENT type")
    @Test
    public void createAccount_accountShouldBeClientType() {
        Client accountCreated = new Client("Werner Gutierrez", "wernerjr@gmail.com", "8PxI5381#hJd%S", Date.valueOf("2000-01-01"));
        accountCreated.setType(ClientType.ADMIN);

        Mockito.when(repository.save(accountCreated)).thenReturn(accountCreated);
        Assertions.assertEquals(ClientType.CLIENT, service.createAccount(accountCreated).getType());
    }

    @DisplayName("createAccount() not allow create account being a minor")
    @Test
    public void createAccount_tryWithInvalidAge() {
        Client accountCreated = new Client("Valeria Sanchez", "valeri123@gmail.com", "Fki111$UB1Y#", Date.valueOf("2023-01-01"));

        Assertions.assertNull(service.createAccount(accountCreated));
    }

    @DisplayName("createAccount() not allow something other than an email")
    @Test
    public void createAccount_tryWithInvalidEmail() {
        Client accountCreated = new Client("Felipe Rojas", "felipemail", "e7V$949u6%jz", Date.valueOf("2003-01-01"));

        Assertions.assertNull(service.createAccount(accountCreated));
    }

    @DisplayName("createAccount() not allow insecure password")
    @Test
    public void createAccount_tryWithInvalidPassword() {
        Client accountCreated = new Client("Daniel Cortes", "dani3cortes@gmail.com", "12345678", Date.valueOf("2001-01-01"));

        Assertions.assertNull(service.createAccount(accountCreated));
    }

    @DisplayName("createAccount() should assign a keyClient")
    @Test
    public void createAccount_assignAKeyClient() {
        Client accountCreated = new Client("David Lopez", "davolopez@gmail.com", "833BCz!%QjY2S", Date.valueOf("2003-01-01"));

        Mockito.when(repository.save(accountCreated)).thenReturn(accountCreated);
        Assertions.assertNotNull(service.createAccount(accountCreated).getKeyClient());
    }

    @DisplayName("createAccount() should assign a keyClient")
    @Test
    public void createAccount_assignAnUniqueKeyClient() {
        Client accountCreated = new Client("Jorge Pintor", "yoryipinto@gmail.com", "p89X9iVZA8W9&", Date.valueOf("2003-01-01"));

        Mockito.when(repository.save(accountCreated)).thenReturn(accountCreated);
        Assertions.assertNotNull(service.createAccount(accountCreated).getKeyClient());
    }

    @DisplayName("saveClient() not allow create account for a minor")
    @Test
    public void saveClient_tryWithInvalidAge() {
        Client accountCreated = new Client("Sebastian Blanco", "sebastian123@gmail.com", "5y0#P1C1m%e", Date.valueOf("2023-01-01"));

        Assertions.assertNull(service.saveClient(accountCreated));
    }

    @DisplayName("saveClient() not allow something other than an email")
    @Test
    public void saveClient_tryWithInvalidEmail() {
        Client accountCreated = new Client("Catalina Martinez", "catlalina@abc", "h859A^^U1uE", Date.valueOf("2000-01-01"));

        Assertions.assertNull(service.saveClient(accountCreated));
    }

    @DisplayName("saveClient() not allow insecure password")
    @Test
    public void saveClient_tryWithInvalidPassword() {
        Client accountCreated = new Client("Daniela Riveros", "danirveros@gmail.com", "12345678", Date.valueOf("2000-01-01"));

        Assertions.assertNull(service.saveClient(accountCreated));
    }

    @DisplayName("saveClient() allows to create a client no matter the type")
    @Test
    public void saveClient_permitCreateWithDifferentType() {
        Client accountCreated = new Client("Johann Duran", "johannes@gmail.com", "Y7iu!08HbaTQ^ZaVHK7X", Date.valueOf("2003-01-01"));
        accountCreated.setType(ClientType.ADMIN);

        Mockito.when(repository.save(accountCreated)).thenReturn(accountCreated);

        Assertions.assertEquals(ClientType.ADMIN, service.saveClient(accountCreated).getType());
    }

    @DisplayName("updateClient() not allow config an age of a minor")
    @Test
    public void updateClient_tryWithInvalidAge() {
        Client accountUpdated = new Client(2, "SUPP0RT", "support", "support@mail.com", "fS1&1*87!9WL%Y7!W$wj", Date.valueOf("2023-01-01"), ClientType.ADMIN);

        Assertions.assertNull(service.updateClient(accountUpdated));
    }

    @DisplayName("updateClient() not allow something other than an email")
    @Test
    public void updateClient_tryWithInvalidEmail() {
        Client accountUpdated = new Client(3, "4DM1N", "admin", "oiufnhaosuhe", "L029t*53VlCZduTSTZvm", Date.valueOf("2000-01-01"), ClientType.ADMIN);

        Assertions.assertNull(service.updateClient(accountUpdated));
    }

    @DisplayName("updateClient() not allow insecure password")
    @Test
    public void updateClient_tryWithInvalidPassword() {
        Client accountUpdated = new Client(3, "4DM1N", "admin", "admin@mail.com", "5yS93M*4p3WKN", Date.valueOf("2000-01-01"), ClientType.ADMIN);

        Assertions.assertNull(service.updateClient(accountUpdated));
    }

    @DisplayName("updateClient() not allow a client type change its type itself")
    @Test
    public void updateClient_clientTypeCanNotUpdateTheType() {
        Client accountInDB = new Client(4, "P3RS0N4", "perosna", "persona123@mail.com", "4PqJ%qP71g5%k", Date.valueOf("2000-01-01"), ClientType.CLIENT);
        Client accountUpdated = new Client(4, "P3RS0N4", "perosna", "persona123@mail.com", "4PqJ%qP71g5%k", Date.valueOf("2000-01-01"), ClientType.ADMIN);

        Mockito.when(repository.getClientById(accountUpdated.getId()))
                .thenReturn(Optional.of(accountInDB));

        Mockito.when(repository.save(any(Client.class)))
                .thenReturn(accountInDB);

        Assertions.assertEquals(ClientType.CLIENT, service.updateClient(accountUpdated).getType());
    }

    @DisplayName("updateClient() cannot update a client that does not exist")
    @Test
    public void updateClient_tryUpdateNonExistentClient() {
        Client accountUpdated = new Client(4, "T0UPD4T3", "Jorge", "Jorge123@mail.com", "kQ@0z149E7&Mo", Date.valueOf("2000-01-01"), ClientType.ADMIN);

        Mockito.when(repository.getClientById(accountUpdated.getId())).thenReturn(Optional.empty());
        Assertions.assertNull(service.updateClient(accountUpdated));
    }

    @DisplayName("updateClient() should return null if there was no one change")
    @Test
    public void updateClient_thereWasNoOneChange() {
        Client accountInDB = new Client(1, "CL13NT3", "client", "cliente@mail.com", "x9VW8$Zkkb*85", Date.valueOf("2000-01-01"), ClientType.CLIENT);
        Client accountUpdatedClient = new Client(1, "CL13NT3", "client", "cliente@mail.com", "x9VW8$Zkkb*85", Date.valueOf("2000-01-01"), ClientType.CLIENT);

        Mockito.when(repository.getClientById(accountUpdatedClient.getId())).thenReturn(Optional.of(accountInDB));
        Assertions.assertNull(service.updateClient(accountUpdatedClient));
    }

    @DisplayName("updateClient() correctly updated")
    @Test
    public void updateClient_correctlyUpdated() {
        Client accountInDB = new Client(1, "CL13NT3", "client", "cliente@mail.com", "x9VW8$Zkkb*85", Date.valueOf("2000-01-01"), ClientType.CLIENT);
        Client accountUpdatedClient = new Client(1, "CL13NT3", "client", "cliente@mail.com", "La78^kw5ve^Wljn", Date.valueOf("2000-01-01"), ClientType.CLIENT);

        Mockito.when(repository.getClientById(accountUpdatedClient.getId())).thenReturn(Optional.of(accountInDB));

        Mockito.when(repository.save(any(Client.class))).thenReturn(accountUpdatedClient);

        Assertions.assertEquals(accountUpdatedClient, service.updateClient(accountUpdatedClient));
    }

    @DisplayName("deleteClient() if the client does not exist")
    @Test
    public void deleteClient_nonexistentClient() {
        int idOfClientToDelete = 1;

        Mockito.when(repository.getClientById(idOfClientToDelete)).thenReturn(Optional.empty());
        Assertions.assertFalse(service.deleteClient(idOfClientToDelete));
    }

    @DisplayName("deleteClient() correctly deleted")
    @Test
    public void deleteClient_correctlyDeleted() {
        int idOfClientToDelete = 2;

        Mockito.when(repository.getClientById(idOfClientToDelete)).thenReturn(Optional.of(new Client()));
        Assertions.assertTrue(service.deleteClient(idOfClientToDelete));
    }

    @DisplayName("login() to a account that does not exist")
    @Test
    public void login_nonexistentClient() {
        Client accountToLogin = new Client();
        accountToLogin.setEmail("camilo123@gmail.com");
        accountToLogin.setPassword("l2A2*0PyyVaa5PD");

        Mockito.when(repository.getClientByEmail(accountToLogin.getEmail())).thenReturn(Optional.of(accountToLogin));

        Assertions.assertNotEquals(Optional.empty(), service.login(accountToLogin));
    }

    @DisplayName("login() password that does not match to the account")
    @Test
    public void login_passwordDoesNotMatch() {
        Client accountToLogin = new Client();
        accountToLogin.setEmail("mattrianho@gmail.com");
        accountToLogin.setPassword("Qf1t1*A57#*&72G");

        Client accountInDB = new Client();
        accountInDB.setEmail("mattrianho@gmail.com");
        accountInDB.setPassword("2Yg86J3#0V8Tf5O");

        Mockito.when(repository.getClientByEmail(accountToLogin.getEmail())).thenReturn(Optional.of(accountInDB));

        Assertions.assertEquals(Optional.empty(), service.login(accountToLogin));
    }

    @DisplayName("login() with invalid email")
    @Test
    public void login_tryWithInvalidEmail() {
        Client accountToLogin = new Client();
        accountToLogin.setEmail("QWEUGQWIDU");

        Assertions.assertEquals(Optional.empty(), service.login(accountToLogin));
    }

    @DisplayName("login() with the correct credential")
    @Test
    public void login_loginSuccessfully() {
        Client accountToLogin = new Client();
        accountToLogin.setEmail("mattrianho@gmail.com");
        accountToLogin.setPassword("Qf1t1*A57#*&72G");

        Client accountInDB = new Client();
        accountInDB.setEmail("mattrianho@gmail.com");
        accountInDB.setPassword("Qf1t1*A57#*&72G");

        Mockito.when(repository.getClientByEmail(accountInDB.getEmail())).thenReturn(Optional.of(accountInDB));

        Assertions.assertNotEquals(Optional.empty(), service.login(accountToLogin));
    }

}