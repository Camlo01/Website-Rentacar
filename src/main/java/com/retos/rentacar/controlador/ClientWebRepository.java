
package com.retos.rentacar.controlador;

import com.retos.rentacar.modelo.DTO.ClientAndKeyclient;
import com.retos.rentacar.modelo.Entity.Client.Client;
import com.retos.rentacar.modelo.Entity.Client.KeyClient;
import com.retos.rentacar.servicios.ClientServices;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/Client")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
        RequestMethod.DELETE})
public class ClientWebRepository {

    @Autowired
    private ClientServices servicios;

    // --- Peticiones HTTP Fijas

    // - GET

    @GetMapping("/all")
    public List<Client> getAllClients(@RequestBody KeyClient key) {
        return servicios.getAllClients(key);
    }

    @GetMapping("/{id}")
    public Optional<Client> getClientById(@PathVariable("id") int idClient, @RequestBody KeyClient keyClient) {
        return servicios.getClientByid(idClient, keyClient);
    }

    // - POST

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public Client saveClient(@RequestBody ClientAndKeyclient clientAndKey) {
        return servicios.saveClient(clientAndKey.getClient(), clientAndKey.getKeyClient());
    }

    // - PUT

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.CREATED)
    public Client update(@RequestBody ClientAndKeyclient body) {
        return servicios.updateClient(body.getClient(), body.getKeyClient());
    }

    // - DELETE

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public boolean delete(@RequestBody ClientAndKeyclient body) {
        return servicios.deleteClient(body.getClient().getIdClient(), body.getKeyClient());
    }

    // Login

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Client login(@RequestBody Client clientRecived) {
        return servicios.login(clientRecived).get();
    }


    //Resources

}
