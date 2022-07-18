/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.retos.rentacar.interfaces;

import com.retos.rentacar.modelo.Entity.Car.Car;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 * @author Camilo
 */
public interface CarInterface extends PagingAndSortingRepository<Car, Integer> {


    @Query(value = "SELECT * FROM car WHERE car_status = 0 ORDER BY id_car DESC LIMIT 1",
            nativeQuery = true)
    Optional<Car> getLastCarAdded();


    @Query(value = "SELECT * FROM car WHERE car_status = 0 LIMIT :size OFFSET :page",
            nativeQuery = true)
    Iterable<Car> getCarsWithStatusBookable(@Param("size") int size, @Param("page") int page);

}


