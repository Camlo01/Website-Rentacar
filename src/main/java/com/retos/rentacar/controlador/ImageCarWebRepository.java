package com.retos.rentacar.controlador;

import com.retos.rentacar.modelo.DTO.Wrapper.ImageCarAndKeyClient;
import com.retos.rentacar.modelo.Entity.Car.ImageCar;
import com.retos.rentacar.modelo.Entity.Client.KeyClient;
import com.retos.rentacar.servicios.ImageCarServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/images-car")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
        RequestMethod.DELETE})
public class ImageCarWebRepository {

    @Autowired
    private ImageCarServices service;


    @GetMapping("/image-to-consult={idImage}")
    Optional<ImageCar> consultImage(@PathVariable("idImage") int idImageCar) {
        return service.getImageCarById(idImageCar);
    }

    @GetMapping("/images-of-car={idCar}")
    List<ImageCar> ListImagesOfCar(@PathVariable("idCar") int idCar) {
        return service.getImagesOfCar(idCar);
    }

    //MISSING VALIDATION WITH KEYCLIENT
    @PostMapping("/new-image-for-car")
    public ResponseEntity<?> addNewImageCar(@RequestBody ImageCarAndKeyClient body) {
        if (hasPermissions(body.getKey())) {
            ImageCar img = service.saveImageCar(body.getImage());
            return new ResponseEntity<>(img, HttpStatus.CREATED);
        } else return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("/delete-image")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> deleteImageCar(@RequestBody ImageCarAndKeyClient body) {
        if (hasPermissions(body.getKey())) {
            service.deleteImageCar(body.getImage());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    }

//    Util methods

    /**
     * Method in charge of validate the permissions of a keyClient owner
     *
     * @param key to validate
     * @return boolean value
     */
    private boolean hasPermissions(KeyClient key) {
        return service.hasPermissions(key);
    }


}
