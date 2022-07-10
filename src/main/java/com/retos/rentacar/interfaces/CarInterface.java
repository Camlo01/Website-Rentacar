/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.retos.rentacar.interfaces;

import com.retos.rentacar.modelo.Car;
import com.retos.rentacar.modelo.Client;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *
 * @author USUARIO
 */
public interface CarInterface extends PagingAndSortingRepository<Car, Integer> {

    @Query(value = "SELECT * FROM car ORDER BY id_car DESC LIMIT 1",
    nativeQuery= true)
    Optional<Car> getLastCarAdded();


}
