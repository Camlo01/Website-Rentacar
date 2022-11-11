/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.retos.rentacar.interfaces;

import com.retos.rentacar.modelo.Entity.Message.Message;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author USUARIO
 */
public interface MessageInterface extends CrudRepository<Message,Integer> {

    @Query(value = "SELECT * FROM message WHERE car_id = :idCar",nativeQuery = true)
    Iterable<Message> getAllMessagesOfCar(@Param("idCar") int idOfCar);

}
