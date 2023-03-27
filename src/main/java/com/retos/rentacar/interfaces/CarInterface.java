package com.retos.rentacar.interfaces;

import com.retos.rentacar.modelo.Entity.Car.Car;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author Camilo
 */
@Component
public interface CarInterface extends PagingAndSortingRepository<Car, Integer> {

    @Query(value = "SELECT * FROM car WHERE car_status = 'BOOKABLE' ORDER BY id_car DESC LIMIT 1",
            nativeQuery = true)
    Optional<Car> getLastCarAddedBookable();

    @Query(value = "SELECT * FROM car ORDER BY id_car DESC LIMIT 1",
            nativeQuery = true)
    Optional<Car> getLastCarAdded();

    @Query(value = "SELECT * FROM car WHERE car_status = 'BOOKABLE' LIMIT :size OFFSET :page",
            nativeQuery = true)
    Iterable<Car> getCarsWithStatusBookable(@Param("size") int size, @Param("page") int page);

}


