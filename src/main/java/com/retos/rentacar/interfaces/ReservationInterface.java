/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.retos.rentacar.interfaces;

import com.retos.rentacar.modelo.Entity.Reservation.Reservation;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.retos.rentacar.modelo.Entity.Reservation.ReservationStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 * @author Camilo
 */
public interface ReservationInterface extends PagingAndSortingRepository<Reservation, Integer> {

    Optional<Reservation> findReservationByidReservation(int idReservation);

    List<Reservation> findAllByReservationStatus(ReservationStatus status);

    List<Reservation> findAllByStartDateAfterAndStartDateBefore(Date dateOne, Date dateTwo);

    Optional<Reservation> findReservationByCode(String code);

//    @Query(value = "INSERT INTO RESERVATION (CODE, START_DATE, DEVOLUTION_DATE, ID_CAR, ID_CLIENT) VALUES (?1, ?2, ?3, ?4, ?5 )", nativeQuery = true)
//    Optional<Reservation> createReservation(String code, Date start_date, Date devolution_date, int idCar, int idClient);

    @Query("SELECT c.client, COUNT(c.client) from Reservation AS c group by c.client order by COUNT(c.client)DESC")
    List<Object[]> countTotalReservationsByClient();

    @Query(value = "SELECT * FROM CLIENT INNER JOIN RESERVATION ON CLIENT.ID_CLIENT=RESERVATION.ID_CLIENT WHERE CLIENT.KEY_CLIENT LIKE :key", nativeQuery = true)
    List<Reservation> getAllReservationsByClientKey(String key);

    @Query(value = "SELECT * FROM CLIENT INNER JOIN RESERVATION ON CLIENT.ID_CLIENT=RESERVATION.ID_CLIENT WHERE CLIENT.EMAIL  = ?1", nativeQuery = true)
    List<Reservation> getAllReservationsByClientEmail(String email);

    @Query(value = "SELECT * FROM CLIENT INNER JOIN RESERVATION ON CLIENT.ID_CLIENT=RESERVATION.ID_CLIENT WHERE CLIENT.KEY_CLIENT LIKE :key AND RESERVATION_STATUS  = 1", nativeQuery = true)
    List<Reservation> getActiveClientReservation(String key);


}

