package com.retos.rentacar.repositorio;

import com.retos.rentacar.interfaces.ImageCarInterface;
import com.retos.rentacar.modelo.Entity.Car.ImageCar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImageCarRepository {

    @Autowired
    private ImageCarInterface imageInterface;

    public Optional<ImageCar> getImageCarById(int id) {
        return imageInterface.findById(id);
    }

    public List<ImageCar> getImagesOfCar(int id) {
        return imageInterface.getImagesOfCar(id);
    }

    public ImageCar save(ImageCar img) {
        return imageInterface.save(img);
    }

    public void deleteById(int id) {
        imageInterface.deleteById(id);
    }

    public void delete(int idImg) {
        Boolean aBoolean = getImageCarById(idImg).map(img -> {
            imageInterface.delete(img);
            return true;
        }).orElse(false);
    }

}
