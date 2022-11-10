package com.retos.rentacar.repositorio;

import com.retos.rentacar.interfaces.ImageCarInterface;
import com.retos.rentacar.modelo.DTO.DAO.ImageCarDTO;
import com.retos.rentacar.modelo.Entity.Car.Car;
import com.retos.rentacar.modelo.Entity.Car.ImageCar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImageCarRepository {

    @Autowired
    private ImageCarInterface crudImageCar;

    public Optional<ImageCar> getImageCar(int id) {
        return crudImageCar.findById(id);
    }

    public List<ImageCar> getImagesOfCar(int id) {
        return crudImageCar.getImagesOfCar(id);
    }

    public ImageCar save(ImageCar img) {
        return crudImageCar.save(img);
    }

    public void delete(int idImg) {

        Boolean aBoolean = getImageCar(idImg).map(img -> {
            crudImageCar.delete(img);
            return true;
        }).orElse(false);
    }

}
