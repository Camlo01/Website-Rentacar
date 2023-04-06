package com.retos.rentacar.servicios;

import com.retos.rentacar.interfaces.CarInterface;
import com.retos.rentacar.modelo.DTO.DAO.ImageCarDTO;
import com.retos.rentacar.modelo.Entity.Car.Car;
import com.retos.rentacar.modelo.Entity.Car.ImageCar;
import com.retos.rentacar.modelo.Entity.Client.KeyClient;
import com.retos.rentacar.repositorio.ImageCarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImageCarServices {

    @Autowired
    private ImageCarRepository repository;
    @Autowired
    private CarInterface carInterface;
    @Autowired
    private ClientServices clientServices;

    /**
     * Method in charge of get a image by its id
     *
     * @param id of image to find
     * @return Optional of imageCar
     */
    public Optional<ImageCar> getImageCarById(int id) {
        return repository.getImageCarById(id);
    }

    /**
     * Method in charge of return a List
     *
     * @param idCar of car to get images from
     * @return List of ImageCar objects
     */
    public List<ImageCar> getImagesOfCar(int idCar) {
        return repository.getImagesOfCar(idCar);
    }

    /**
     * Method in charge of save a imageCar
     *
     * @param img to save
     * @return ImageCar if was successfully saved
     */
    public ImageCar saveImageCar(ImageCarDTO img) {
        ImageCar newImage = new ImageCar();

        String url = img.getUrl();

//        validate there is not empty fields
        if (url.length() == 0) {
            return null;
        }
//        validate that the car what is being assigned the image exists
        Optional<Car> carToSetImage = getCarById(img.getIdCar());
        if (carToSetImage.isEmpty()) {
            return null;
        }

        newImage.setUrl(img.getUrl());
        newImage.setCar(carToSetImage.get());
        return repository.save(newImage);
    }

    /**
     * Method in charge of delete a imageCar by its id
     *
     * @param img to delete
     */
    public boolean deleteImageCar(ImageCarDTO img) {
        Optional<ImageCar> imgToDelete = getImageCarById(img.getId());

        if (imgToDelete.isPresent()) {
            repository.deleteById(img.getId());
            return true;
        } else return false;
    }

    // Util methods

    private Optional<Car> getCarById(int id) {
        return carInterface.findById(id);
    }

    public boolean hasPermissions(KeyClient key) {
        return clientServices.hasPermissions(key, false);
    }

}
