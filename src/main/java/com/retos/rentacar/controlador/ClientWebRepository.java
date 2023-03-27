
package com.retos.rentacar.controlador;

import com.retos.rentacar.modelo.DTO.Wrapper.ClientAndKeyClient;
import com.retos.rentacar.modelo.Entity.Client.Client;
import com.retos.rentacar.modelo.Entity.Client.KeyClient;
import com.retos.rentacar.servicios.ClientServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/client")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
        RequestMethod.DELETE})
public class ClientWebRepository {

    @Autowired
    private ClientServices services;

    // - GET

    @GetMapping("/all")
    public List<Client> getAllClients(@RequestBody KeyClient key) {
        return services.getAllClientsWithAuthorization(key);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getClientById(@PathVariable("id") int idClient, @RequestBody KeyClient keyClient) {
        if (hasPermissions(keyClient)) {
            Optional<Client> clientOptional = services.getClientByIdWithAuthorization(idClient);
            if (clientOptional.isPresent()) {
                return new ResponseEntity<>(clientOptional.get(), HttpStatus.OK);
            } else return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    // - POST

    // If I want to create my own account
    @PostMapping("/create-account")
    public ResponseEntity<?> createClient(@RequestBody Client client) {
        Client clientCreated = services.createAccount(client);
        if (client.getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else return new ResponseEntity<>(clientCreated, HttpStatus.CREATED);
    }

    // create account as admin
    @PostMapping("/save")
    public ResponseEntity<?> saveClient(@RequestBody ClientAndKeyClient body) {
        if (hasPermissions(body.getKeyClient())) {
            Client client = services.saveClient(body.getClient());
            return new ResponseEntity<>(client, HttpStatus.CREATED);
        } else return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    // - PUT

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody ClientAndKeyClient body) {
        KeyClient whoRequest = new KeyClient(body.getKeyClient().getKeyClient());
        boolean hasPermission = (hasPermissions(whoRequest) || isAccountOwner(body));
        if (hasPermission) {
            Client clientUpdated = services.updateClient(body.getClient());
            return new ResponseEntity<>(clientUpdated, HttpStatus.CREATED);
        } else return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    // Login

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> login(@RequestBody Client clientReceived) {
        Optional<Client> client = services.login(clientReceived);
        if (client.isPresent()) {
            return new ResponseEntity<>(client.get(), HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    // - DELETE

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestBody ClientAndKeyClient body) {
        KeyClient whoRequest = body.getKeyClient();
        boolean hasPermission = (hasPermissions(whoRequest) || isAccountOwner(body));

        if (hasPermission) {
            boolean wasDeleted = services.deleteClient(body.getClient().getId());
            if (wasDeleted) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    //Resources

    /**
     * Method in charge of validate the permissions of a keyClient
     *
     * @param key to evaluate
     * @return boolean value
     */
    private boolean hasPermissions(KeyClient key) {
        return services.hasPermissions(key, false);
    }

    /**
     * Method in charge of evaluate if a keyClient belong to the client sent
     *
     * @param body object with Client to compare and KeyClient
     * @return boolean value
     */
    private boolean isAccountOwner(ClientAndKeyClient body) {
        return services.isAccountOwner(body.getClient(), body.getKeyClient());
    }

}
