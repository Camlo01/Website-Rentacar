package com.retos.rentacar.interfaces;

import com.retos.rentacar.modelo.Entity.Message.Message;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * @author Camilo
 */
public interface MessageInterface extends CrudRepository<Message, Integer> {

    @Query(value = "SELECT * FROM message WHERE car_id = :idCar", nativeQuery = true)
    Iterable<Message> getAllMessagesOfCar(@Param("idCar") int idOfCar);

}
