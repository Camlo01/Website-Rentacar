
package com.retos.rentacar.controlador;

import com.retos.rentacar.modelo.DTO.Wrapper.ClientAndKeyClient;
import com.retos.rentacar.modelo.Entity.Client.Client;
import com.retos.rentacar.modelo.Entity.Client.KeyClient;
import com.retos.rentacar.servicios.ClientServices;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
        RequestMethod.DELETE})
public class ClientWebRepository {

    @Autowired
    private ClientServices services;

    // --- Peticiones HTTP Fijas

    // - GET

    @GetMapping("/all")
    public List<Client> getAllClients(@RequestBody KeyClient key) {
        return services.getAllClientsWithAuthorization(key);
    }

    @GetMapping("/{id}")
    public Optional<Client> getClientById(@PathVariable("id") int idClient, @RequestBody KeyClient keyClient) {
        return services.getClientByIdWithAuthorization(idClient, keyClient);
    }

    // - POST

    // If I want to create my own account
    @PostMapping("/create-account")
    @ResponseStatus(HttpStatus.CREATED)
    public Client createClient(@RequestBody Client client) {
        return services.createAccount(client);
    }


    // create account as admin
    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public Client saveClient(@RequestBody ClientAndKeyClient clientAndKey) {
        return services.saveClient(clientAndKey.getClient(), clientAndKey.getKeyClient());
    }

    // - PUT

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.CREATED)
    public Client update(@RequestBody ClientAndKeyClient body) {
        return services.updateClient(body.getClient(), body.getKeyClient());
    }

    // - DELETE

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public boolean delete(@RequestBody ClientAndKeyClient body) {
        return services.deleteClient(body.getClient().getId(), body.getKeyClient());
    }

    // Login

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Client login(@RequestBody Client clientReceived) {
        return services.login(clientReceived).get();
    }


    //Resources

}
