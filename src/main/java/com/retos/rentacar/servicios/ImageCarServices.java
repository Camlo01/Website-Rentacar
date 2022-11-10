package com.retos.rentacar.servicios;

import com.retos.rentacar.interfaces.CarInterface;
import com.retos.rentacar.modelo.DTO.DAO.ImageCarDTO;
import com.retos.rentacar.modelo.Entity.Car.ImageCar;
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

    public Optional<ImageCar> getImageCarById(int id) {
        return repository.getImageCar(id);
    }

    public List<ImageCar> getImagesOfCar(int idCar) {
        return repository.getImagesOfCar(idCar);
    }

    public ImageCar saveImageCar(ImageCarDTO img) {
        ImageCar newImage = new ImageCar();

        newImage.setUrl(img.getUrl());
        newImage.setCar(carInterface.findById(img.getIdCar()).get());

        return repository.save(newImage);
    }

    public void deleteImageCar(ImageCarDTO img) {
        repository.deletze(img.getId());
    }


}
