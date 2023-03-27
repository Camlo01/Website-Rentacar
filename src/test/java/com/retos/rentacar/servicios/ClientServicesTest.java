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


}