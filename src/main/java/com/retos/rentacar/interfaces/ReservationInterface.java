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
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

/**
 * @author Camilo
 */
@Transactional
public interface ReservationInterface extends PagingAndSortingRepository<Reservation, Integer> {

    Optional<Reservation> findReservationByidReservation(int idReservation);

    List<Reservation> findAllByReservationStatus(ReservationStatus status);

    List<Reservation> findAllByStartDateAfterAndStartDateBefore(Date dateOne, Date dateTwo);

    Optional<Reservation> findReservationByCode(String code);


    @Modifying
    @Query(value = "INSERT INTO RESERVATION (START_DATE, DEVOLUTION_DATE,CODE, CAR_ID, CLIENT_ID) VALUES (?1, ?2, ?3, ?4, ?5 )", nativeQuery = true)
    void createReservation(String startDate, String devolutionDate, String code, int car_id, int client_id);


    @Query(value = "SELECT * FROM reservation WHERE start_date <= :reservationsIn AND devolution_date >= :reservationsIn AND reservation_status = 'ACTIVE'", nativeQuery = true)
    List<Reservation> getReservationsActiveIn(@Param("reservationsIn") Date reservationsIn);


    @Query(value = "SELECT * FROM reservation WHERE start_date >= :minDate AND devolution_date <= :maxDate", nativeQuery = true)
    List<Reservation> getReservationBetweenDates(@Param("minDate") Date dateMin,
                                                 @Param("maxDate") Date maxDate);

    @Query("SELECT c.client, COUNT(c.client) from Reservation AS c group by c.client order by COUNT(c.client)DESC")
    List<Object[]> countTotalReservationsByClient();

    @Query(value = "SELECT * FROM CLIENT INNER JOIN RESERVATION ON CLIENT.ID_CLIENT=RESERVATION.ID_CLIENT WHERE CLIENT.KEY_CLIENT LIKE :key", nativeQuery = true)
    List<Reservation> getAllReservationsByClientKey(String key);

    @Query(value = "SELECT * FROM CLIENT INNER JOIN RESERVATION ON CLIENT.ID_CLIENT=RESERVATION.ID_CLIENT WHERE CLIENT.EMAIL  = ?1", nativeQuery = true)
    List<Reservation> getAllReservationsByClientEmail(String email);

    @Query(value = "SELECT * FROM CLIENT INNER JOIN RESERVATION ON CLIENT.ID_CLIENT=RESERVATION.ID_CLIENT WHERE CLIENT.KEY_CLIENT LIKE :key AND RESERVATION_STATUS  = 1", nativeQuery = true)
    List<Reservation> getActiveClientReservation(String key);


}

