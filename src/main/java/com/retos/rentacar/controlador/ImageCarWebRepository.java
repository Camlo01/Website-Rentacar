package com.retos.rentacar.controlador;

import com.retos.rentacar.modelo.DTO.DAO.ImageCarDTO;
import com.retos.rentacar.modelo.DTO.Wrapper.ImageCarAndKeyClient;
import com.retos.rentacar.modelo.Entity.Car.ImageCar;
import com.retos.rentacar.servicios.ImageCarServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    @ResponseStatus(HttpStatus.CREATED)
    ImageCar addNewImageCar(@RequestBody ImageCarAndKeyClient body) {
        return service.saveImageCar(body.getImage(), body.getKey());
    }

    @DeleteMapping("/delete-image")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteImageCar(@RequestBody ImageCarAndKeyClient body) {
        service.deleteImageCar(body.getImage(), body.getKey());
    }


}
