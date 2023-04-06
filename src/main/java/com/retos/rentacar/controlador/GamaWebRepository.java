
package com.retos.rentacar.controlador;

import com.retos.rentacar.modelo.DTO.Wrapper.GamaAndKeyClient;
import com.retos.rentacar.modelo.Entity.Client.KeyClient;
import com.retos.rentacar.modelo.Entity.Gama.Gama;
import com.retos.rentacar.servicios.GamaServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/gama")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
        RequestMethod.DELETE})
public class GamaWebRepository {

    @Autowired
    private GamaServices service;

    @GetMapping("/all")
    public Iterable<Gama> getGama() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Optional<Gama> getGama(@PathVariable("id") int idGama) {
        return service.getGamaById(idGama);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody GamaAndKeyClient body) {
        if (hasPermissions(body.getKey())) {
            Gama newGama = service.saveGama(body.getGama());
            return new ResponseEntity<>(newGama, HttpStatus.CREATED);
        } else return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody GamaAndKeyClient body) {
        if (hasPermissions(body.getKey())) {
            Gama gamaUpdated = service.updateGama(body.getGama());
            return new ResponseEntity<>(gamaUpdated, HttpStatus.CREATED);
        } else return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    //    cant delete a gama if is associate with any car
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@RequestBody GamaAndKeyClient body) {
        if (hasPermissions(body.getKey())) {
            boolean wasDeleted = service.deleteGama(body.getGama());
            if (wasDeleted) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    /**
     * Method in charge of validate the permissions of a client by its keyClient
     *
     * @param key to validate
     * @return boolean value
     */
    private boolean hasPermissions(KeyClient key) {
        return service.hasPermissions(key);
    }

}
