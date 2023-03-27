package com.retos.rentacar.interfaces;

import com.retos.rentacar.modelo.Entity.Car.ImageCar;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Camilo
 */
@Component
public interface ImageCarInterface extends PagingAndSortingRepository<ImageCar, Integer> {

    @Query(value = "SELECT * FROM images_car WHERE car_id = :idCar", nativeQuery = true)
    List<ImageCar> getImagesOfCar(@Param("idCar") int idCar);

}
