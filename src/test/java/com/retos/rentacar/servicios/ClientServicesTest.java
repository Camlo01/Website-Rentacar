package com.retos.rentacar.servicios;

import com.retos.rentacar.interfaces.ClientInterface;
import com.retos.rentacar.modelo.Entity.Client.Client;
import com.retos.rentacar.modelo.Entity.Client.ClientType;
import com.retos.rentacar.modelo.Entity.Client.KeyClient;
import com.retos.rentacar.repositorio.ClientRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ClientServicesTest {

    @Mock
    private ClientRepository clientRepository;
    @Mock
    private ClientInterface clientInterface;

    @InjectMocks
    private ClientServices clientService;

    private Client clientClient, clientSupport, clientAdmin, clientDeveloper, noneClient;

    private ArrayList<Client> arrayOfClients = new ArrayList<>();

    @BeforeEach
    void setUp() {
        clientClient = new Client(1, "CL13NT3", "cliente", "cliente@mail.com", "12345*", Date.valueOf("2000-01-01"), ClientType.CLIENT);
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

    @DisplayName("getAllClients() as Developer should return not null")
    @Test
    public void testGetAllClients_ShouldNotReturnNull() {
        KeyClient keyOfDeveloper = new KeyClient(clientDeveloper.getKeyClient());

        Mockito.when(clientInterface.findClientByKeyClient(keyOfDeveloper.getKeyClient()))
                .thenReturn(Optional.of(clientDeveloper));

        Mockito.when(clientRepository.getAll())
                .thenReturn(arrayOfClients);

        Assertions.assertNotNull(clientService.getAllClientsWithAuthorization(keyOfDeveloper));

    }


    @DisplayName("getAllClients() as Support should return null")
    @Test
    public void testGetAllClients_AsSupport() {
        KeyClient keyOfSupport = new KeyClient(clientSupport.getKeyClient());

        Mockito.when(clientInterface.findClientByKeyClient(keyOfSupport.getKeyClient())).thenReturn(Optional.of(clientSupport));

        Assertions.assertNull(clientService.getAllClientsWithAuthorization(keyOfSupport));
    }

    @DisplayName("getAllClients() as Client should return null")
    @Test
    public void testGetAllClients_AsClient() {
        KeyClient keyOfClient = new KeyClient(clientClient.getKeyClient());

        Mockito.when(clientInterface.findClientByKeyClient(keyOfClient.getKeyClient())).thenReturn(Optional.of(clientClient));

        Assertions.assertNull(clientService.getAllClientsWithAuthorization(keyOfClient));
    }

    @DisplayName("getClientById() should return a Client")
    @Test
    public void testGetClientById() {
        int clientToFind = 1;
        Client clientReturned = arrayOfClients.get(clientToFind);

        Mockito.when(clientRepository.getClientById(clientToFind))
                .thenReturn(Optional.of(clientReturned));

        Assertions.assertNotNull(clientService.getClientById(clientToFind));
    }

    @DisplayName("getClientByIdWithAuthorization() as Client Should return Empty Optional")
    @Test
    public void testGetClientByIdWithAuthorization_AsClient() {
        String keyOfClient = clientClient.getKeyClient();

        Mockito.when(clientInterface.findClientByKeyClient(keyOfClient))
                .thenReturn(Optional.of(clientClient));

        Assertions.assertEquals(Optional.empty(), clientService.getClientByIdWithAuthorization(2, new KeyClient(keyOfClient)));
    }

    @DisplayName("getClientByIdWithAuthorization() as Admin should return client")
    @Test
    public void testGetClientByIdWithAuthorization_AsAdmin() {
        int idOfClient = 1;
        KeyClient keyAdmin = new KeyClient(clientAdmin.getKeyClient());

        Mockito.when(clientInterface.findClientByKeyClient(keyAdmin.getKeyClient()))
                .thenReturn(Optional.of(clientAdmin));

        Mockito.when(clientRepository.getClientById(idOfClient))
                .thenReturn(Optional.of(clientClient));

        int idOfClientObtained =
                clientService.getClientByIdWithAuthorization(1, keyAdmin).get().getId();
        Assertions.assertEquals(idOfClient, idOfClientObtained);


    }

    @DisplayName("getClientByIdWithAuthorization() como Developer should return client")
    @Test
    public void testGetClientByIdWithAuthorization_AsDeveloper() {
        int idOfClient = 5;

        KeyClient keyDeveloper = new KeyClient(clientDeveloper.getKeyClient());

        Mockito.when(clientInterface.findClientByKeyClient(keyDeveloper.getKeyClient()))
                .thenReturn(Optional.of(clientDeveloper));

        Mockito.when(clientRepository.getClientById(idOfClient))
                .thenReturn(Optional.of(noneClient));

        int idOfClientObtained =
                clientService.getClientByIdWithAuthorization(idOfClient, keyDeveloper).get().getId();

        Assertions.assertEquals(idOfClient, idOfClientObtained);

    }

    @DisplayName("getClientByIdWithAuthorization() as Support should return empty object")
    @Test
    public void testGetClientByIdWithAuthorization_AsSupport() {
        int idOfClient = 1;

        KeyClient keySupport = new KeyClient(clientSupport.getKeyClient());

        Mockito.when(clientInterface.findClientByKeyClient(keySupport.getKeyClient()))
                .thenReturn(Optional.of(clientSupport));

        Assertions.assertEquals(Optional.empty(), clientService.getClientByIdWithAuthorization(idOfClient, keySupport));

    }

    @DisplayName("createAccount() minor creating an account")
    @Test
    public void testCreateAccount_MinorCreateAccount() {
        Client minorAccountToCreate = new Client("Luis",
                "Luisillo1@gmail.com",
                "t8dG8$$7iN*uAZGP",
                Date.valueOf("2013-12-12")
        );

        Assertions.assertNull(clientService.createAccount(minorAccountToCreate));
    }

    @DisplayName("createAccount() insecure password")
    @Test
    public void testCreateAccount_InsecurePassword() {
        Client accountToCreate = new Client("Hugo",
                "Huguillo123@gmail.com",
                "hugo",
                Date.valueOf("1998-02-10")
        );
        Assertions.assertNull(clientService.createAccount(accountToCreate));
    }

    @DisplayName("createAccount() invalid email")
    @Test
    public void testCreateAccount_InvalidEmail() {
        Client invalidMailAccount = new Client("Hugo",
                "NoValidEmail",
                "T70b*8i^M6%XMA",
                Date.valueOf("1998-02-10"));
        Assertions.assertNull(clientService.createAccount(invalidMailAccount));
    }

    @DisplayName("createAccount() valid account, email, password and age")
    @Test
    public void testCreateAccount() {

        Client clientToCreate =
                new Client("Pepe",
                        "Pepe123@gmail.com",
                        "B!o510%i1FvpBx",
                        Date.valueOf("2000-01-01"));
        Mockito.when(clientRepository.save(clientToCreate))
                .thenReturn(clientToCreate);

        Assertions.assertNotNull(clientService.createAccount(clientToCreate));

    }

    @DisplayName("saveClient() as Client")
    @Test
    public void testSaveClient_AsClient() {

        Client clientToSave =
                new Client("name", "validEmail@gmail.com", "1e65TN^979n%B#UH", Date.valueOf("2000-01-01"));

        KeyClient keyOfClient = new KeyClient(clientClient.getKeyClient());

        Mockito.when(clientInterface.findClientByKeyClient(keyOfClient.getKeyClient()))
                .thenReturn(Optional.of(clientClient));


        Assertions.assertNull(clientService.saveClient(clientToSave, keyOfClient));

    }

    @DisplayName("saveClient() as Admin")
    @Test
    public void testSaveClient_AsAdmin() {
        Client clientToSave =
                new Client("name", "validEmail@gmail.com", "La66&47MRtKmY&", Date.valueOf("2000-01-01"));

        KeyClient keyAdmin = new KeyClient(clientAdmin.getKeyClient());

        Mockito.when(clientInterface.findClientByKeyClient(keyAdmin.getKeyClient()))
                .thenReturn(Optional.of(clientAdmin));

        Mockito.when(clientRepository.save(clientToSave))
                .thenReturn(clientToSave);

        Assertions.assertNotNull(clientService.saveClient(clientToSave, keyAdmin));

    }

    @DisplayName("saveClient() as Developer")
    @Test
    public void testSaveClient_AsDeveloper() {
        Client clientToSave =
                new Client("name", "validEmail@gmail.com", "1e65TN^979n%B#UH", Date.valueOf("2000-01-01"));

        KeyClient keyDeveloper = new KeyClient(clientDeveloper.getKeyClient());

        Mockito.when(clientInterface.findClientByKeyClient(keyDeveloper.getKeyClient()))
                .thenReturn(Optional.of(clientDeveloper));


        Mockito.when(clientRepository.save(clientToSave))
                .thenReturn(clientToSave);


        Assertions.assertNotNull(clientService.saveClient(clientToSave, keyDeveloper));

    }

    @DisplayName("saveClient() as Support")
    @Test
    public void testSaveClient_AsSupport() {
        Client clientToSave =
                new Client("name", "validEmail@gmail.com", "1e65TN^979n%B#UH", Date.valueOf("2000-01-01"));

        KeyClient keySupport = new KeyClient(clientSupport.getKeyClient());

        Mockito.when(clientInterface.findClientByKeyClient(keySupport.getKeyClient()))
                .thenReturn(Optional.of(clientSupport));

        Assertions.assertNull(clientService.saveClient(clientToSave, keySupport));

    }

    @DisplayName("hasPermissions() true if is Admin")
    @Test
    public void testHasPermissions_IsAdmin() {
        KeyClient keyAdmin = new KeyClient(clientAdmin.getKeyClient());

        Mockito.when(clientInterface.findClientByKeyClient(keyAdmin.getKeyClient()))
                .thenReturn(Optional.of(clientAdmin));

        Assertions.assertTrue(clientService.hasPermissions(keyAdmin, false));
    }

    @DisplayName("hasPermissions() true if is Developer")
    @Test
    public void testHasPermissions_IsDeveloper() {
        KeyClient keyDeveloper = new KeyClient(clientDeveloper.getKeyClient());

        Mockito.when(clientInterface.findClientByKeyClient(keyDeveloper.getKeyClient()))
                .thenReturn(Optional.of(clientDeveloper));

        Assertions.assertTrue(clientService.hasPermissions(keyDeveloper, false));
    }

    @DisplayName("hasPermissions() negar permiso si es Client")
    @Test
    public void testHasPermissions_IsClient() {
        KeyClient keyClient = new KeyClient(clientClient.getKeyClient());

        Mockito.when(clientInterface.findClientByKeyClient(keyClient.getKeyClient()))
                .thenReturn(Optional.of(clientClient));

        Assertions.assertFalse(clientService.hasPermissions(keyClient, true));
    }

    @DisplayName("hasPermissions() true if Support and passed as true in permission")
    @Test
    public void testHasPermissions_IsSupportWithTrue() {
        KeyClient keySupport = new KeyClient(clientSupport.getKeyClient());

        Mockito.when(clientInterface.findClientByKeyClient(keySupport.getKeyClient()))
                .thenReturn(Optional.of(clientSupport));

        Assertions.assertTrue(clientService.hasPermissions(keySupport, true));
    }

    @DisplayName("hasPermissions() false if Support and passed as false in permission")
    @Test
    public void testHasPermissions_IsSupportWithFalse() {
        KeyClient keySupport = new KeyClient(clientSupport.getKeyClient());

        Mockito.when(clientInterface.findClientByKeyClient(keySupport.getKeyClient()))
                .thenReturn(Optional.of(clientSupport));

        Assertions.assertFalse(clientService.hasPermissions(keySupport, false));
    }


    @DisplayName("updateClient() as Client type not being the owner")
    @Test
    public void testUpdateClient_AsClientAsNotOwner() {

        Client clientDB = new Client(15, "ABCDEFG", "Jorge", "jorgecito123@mail.com", "0dvK3*$77qmq", Date.valueOf("2000-01-01"), ClientType.CLIENT);
        Client clientUpdated = new Client(24, "QWERTY", "Daniel", "daniel123@mail.com", "K5u4T3*@ES4xt!dZ", Date.valueOf("2001-02-02"), ClientType.CLIENT);
        KeyClient key = new KeyClient("DIFFERENT_KEY");

        Mockito.when(clientInterface.findClientByKeyClient(key.getKeyClient()))
                .thenReturn(Optional.of(clientDB));

        Assertions.assertNull(clientService.updateClient(clientUpdated, key));

    }

    @DisplayName("updateClient() as Client type and being the owner")
    @Test
    public void testUpdateClient_AsClientAsOwner() {

        Client clientDB = new Client(15, "ABCDEFG", "Jorge", "jorgecito123@mail.com", "0dvK3*$77qmq", Date.valueOf("2000-01-01"), ClientType.CLIENT);
        Client clientUpdated = new Client(15, "ABCDEFG", "Jorgecito", "jorgecito123@mail.com", "14W76wA*uE1rs^%9", Date.valueOf("2000-01-01"), ClientType.CLIENT);
        KeyClient keyWhoRequest = new KeyClient(clientDB.getKeyClient());

        Mockito.when(clientInterface.findClientByKeyClient(keyWhoRequest.getKeyClient()))
                .thenReturn(Optional.of(clientDB));

        Mockito.when(clientRepository.getClientById(clientUpdated.getId()))
                .thenReturn(Optional.of(clientDB));

        Mockito.when(clientRepository.save(clientDB))
                .thenReturn(clientUpdated);

        Assertions.assertNotNull(clientService.updateClient(clientUpdated, keyWhoRequest));

    }

    @DisplayName("updateClient() as Admin type updating own account ")
    @Test
    public void testUpdateClient_AsAdminAndUpdatingOwnAccount() {
        Client clientDB = new Client(234, "TW3NYT2", "Jose", "Joselito50@gmail.com", "88A@Aq2Nk#QO13hF", Date.valueOf("1985-01-01"), ClientType.ADMIN);

        Client clientUpdated = new Client(234, "TW3NYT2", "Jose", "Joselito50@gmail.com", "53zY3$wpM1Rr^tRtJ", Date.valueOf("1985-01-01"), ClientType.ADMIN);
        KeyClient keyWhoRequest = new KeyClient(clientDB.getKeyClient());

        Mockito.when(clientInterface.findClientByKeyClient(keyWhoRequest.getKeyClient()))
                .thenReturn(Optional.of(clientDB));

        Mockito.when(clientRepository.getClientById(clientUpdated.getId()))
                .thenReturn(Optional.of(clientDB));

        Mockito.when(clientRepository.save(clientDB)).thenReturn(clientUpdated);

        Assertions.assertNotNull(clientService.updateClient(clientUpdated, keyWhoRequest));

    }

    @DisplayName("updateClient() as Admin type updating other account ")
    @Test
    public void testUpdateClient_AsAdminAndUpdatingOtherAccount() {

        Client clientDB = new Client(99, "OLDQWNIDAW", "David", "David123@gmail.com", "Lr141C#p!VgS", Date.valueOf("2004-01-01"), ClientType.CLIENT);
        Client clientUpdated = new Client(99, "OLDQWNIDAW", "David", "David123@gmail.com ", "Lr141C#p!VgS", Date.valueOf("2004-01-01"), ClientType.CLIENT);
        KeyClient keyWhoRequest = new KeyClient(clientAdmin.getKeyClient());

        Mockito.when(clientInterface.findClientByKeyClient(keyWhoRequest.getKeyClient()))
                .thenReturn(Optional.of(clientAdmin));

        Mockito.when(clientRepository.getClientById(clientUpdated.getId()))
                .thenReturn(Optional.of(clientDB));

        Mockito.when(clientRepository.save(clientDB))
                .thenReturn(clientUpdated);

        Assertions.assertNotNull(clientService.updateClient(clientUpdated, keyWhoRequest));
    }

    @DisplayName("updateClient() as Admin type updating other account but with an invalid value ")
    @Test
    public void testUpdateClient_AsAdminAndUpdatingOtherAccountWithInvalidValue() {

        Client clientDB = new Client(99, "OLDQWNIDAW", "David", "David123@gmail.com", "Lr141C#p!VgS", Date.valueOf("2004-01-01"), ClientType.CLIENT);
        Client clientUpdated = new Client(99, "OLDQWNIDAW", "David", "David123@gmail.com ", "hugo", Date.valueOf("2004-01-01"), ClientType.CLIENT);
        KeyClient keyWhoRequest = new KeyClient(clientAdmin.getKeyClient());

        Mockito.when(clientInterface.findClientByKeyClient(keyWhoRequest.getKeyClient()))
                .thenReturn(Optional.of(clientAdmin));

        Mockito.when(clientRepository.getClientById(clientUpdated.getId()))
                .thenReturn(Optional.of(clientDB));

        Mockito.when(clientRepository.save(clientDB))
                .thenReturn(clientUpdated);

        Assertions.assertNotNull(clientService.updateClient(clientUpdated, keyWhoRequest));
    }

    @DisplayName("deleteClient() as Client, delete someone else's account")
    @Test
    public void testDeleteClient_DeletingAccountAsSomeoneElse() {
        Client clientDB = new Client(10, "SOMEBODYELSEAC", "Matt", "Matthew123@gmail.com", "8Fx33$2%@%7j5seT", Date.valueOf("2000-01-01"), ClientType.CLIENT);
        Client client = new Client(40, "CLIENT", "Roger", "Roeger123@mail.com", "31Y8k#o!zQyU%iz$N", Date.valueOf("2000-01-01"), ClientType.CLIENT);

        KeyClient keyWhoRequest = new KeyClient(client.getKeyClient());

        Mockito.when(clientRepository.getClientById(10))
                .thenReturn(Optional.of(clientDB));

        Mockito.when(clientInterface.findClientByKeyClient(client.getKeyClient()))
                .thenReturn(Optional.of(client));

        Assertions.assertFalse(clientService.deleteClient(10, keyWhoRequest));
    }

    @DisplayName("deleteClient() as Client, delete own accounts")
    @Test
    public void testDeleteClient_DeletingOwnAccount() {
        Client clientDB = new Client(10, "SOMEBODYELSEAC", "Matt", "Matthew123@gmail.com", "8Fx33$2%@%7j5seT", Date.valueOf("2000-01-01"), ClientType.CLIENT);
        Client client = new Client(10, "SOMEBODYELSEAC", "Matt", "Matthew123@gmail.com", "8Fx33$2%@%7j5seT", Date.valueOf("2000-01-01"), ClientType.CLIENT);

        KeyClient keyWhoRequest = new KeyClient(client.getKeyClient());

        Mockito.when(clientRepository.getClientById(10))
                .thenReturn(Optional.of(clientDB));

        Mockito.when(clientInterface.findClientByKeyClient(client.getKeyClient()))
                .thenReturn(Optional.of(client));

        Assertions.assertTrue(clientService.deleteClient(10, keyWhoRequest));
    }

    @DisplayName("deleteClient() as Admin, deleting an account")
    @Test
    public void testDeleteClient_AdminDeletingAnAccount() {
        Client clientDB = new Client(10, "SOMEELSE", "Gray", "Gray123@gmail.com", "8Fx33$2%@%7j5seT", Date.valueOf("2000-01-01"), ClientType.CLIENT);
        Client client = new Client(40, "ADMIN", "Josep", "Josep123@mail.com", "31Y8k#o!zQyU%iz$N", Date.valueOf("2000-01-01"), ClientType.ADMIN);

        KeyClient keyWhoRequest = new KeyClient(client.getKeyClient());

        Mockito.when(clientRepository.getClientById(10))
                .thenReturn(Optional.of(clientDB));

        Mockito.when(clientInterface.findClientByKeyClient(client.getKeyClient()))
                .thenReturn(Optional.of(client));

        Assertions.assertTrue(clientService.deleteClient(10, keyWhoRequest));

    }

    @DisplayName("deleteClient() as Admin, deleting own account")
    @Test
    public void testDeleteClient_AdminDeletingOwnAccount() {

        Client clientDB = new Client(40, "ADMIN", "Josep", "Josep123@mail.com", "31Y8k#o!zQyU%iz$N", Date.valueOf("2000-01-01"), ClientType.ADMIN);
        Client client = new Client(40, "ADMIN", "Josep", "Josep123@mail.com", "31Y8k#o!zQyU%iz$N", Date.valueOf("2000-01-01"), ClientType.ADMIN);

        KeyClient keyWhoRequest = new KeyClient(client.getKeyClient());

        Mockito.when(clientRepository.getClientById(40))
                .thenReturn(Optional.of(clientDB));

        Mockito.when(clientInterface.findClientByKeyClient(client.getKeyClient()))
                .thenReturn(Optional.of(client));

        Assertions.assertTrue(clientService.deleteClient(40, keyWhoRequest));

    }

    @DisplayName("deleteClient() as Support, deleting an account")
    @Test
    public void testDeleteClient_AsSupport() {

        Client clientDB = new Client(40, "SOMEBODY", "qwer", "qwerty@mail.com", "31Y8k#o!zQyU%iz$N", Date.valueOf("2000-01-01"), ClientType.ADMIN);
        Client client = new Client(5, "SUPPORT", "Louis", "Louis123@mail.com", "31Y8k#o!zQyU%iz$N", Date.valueOf("2000-01-01"), ClientType.SUPPORT);

        KeyClient keyWhoRequest = new KeyClient(client.getKeyClient());

        Mockito.when(clientRepository.getClientById(40))
                .thenReturn(Optional.of(clientDB));

        Mockito.when(clientInterface.findClientByKeyClient(client.getKeyClient()))
                .thenReturn(Optional.of(client));

        Assertions.assertFalse(clientService.deleteClient(40, keyWhoRequest));


    }

    @DisplayName("login() with a existing account")
    @Test
    public void testLogin_ExistingAccount() {

        Client clientDB = new Client(1, "CL13NT3", "cliente", "usuario@mail.com", "4&6vNE!Sx55k98Rykf", Date.valueOf("2000-01-01"), ClientType.CLIENT);
        Client userToLogin = new Client("usuario@mail.com", "4&6vNE!Sx55k98Rykf");

        Mockito.when(clientRepository.getClientByEmail(userToLogin.getEmail()))
                .thenReturn(Optional.of(clientDB));

        Assertions.assertEquals(clientDB, clientService.login(userToLogin).get());

    }

    @DisplayName("login() with a nonexistent account")
    @Test
    public void testLogin_NonexistentAccount() {

        Client userToLogin = new Client("usuario@mail.com", "4&6vNE!Sx55k98Rykf");

        Mockito.when(clientRepository.getClientByEmail(userToLogin.getEmail()))
                .thenReturn(Optional.empty());

        Assertions.assertEquals(Optional.empty(), clientService.login(userToLogin));

    }

    @DisplayName("login() with empty object")
    @Test
    public void testLogin_emptyObject() {

        Client userToLogin = new Client();

        Mockito.when(clientRepository.getClientByEmail(userToLogin.getEmail()))
                .thenReturn(Optional.empty());

        Assertions.assertEquals(Optional.empty(), clientService.login(userToLogin));

    }

}