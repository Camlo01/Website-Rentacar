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

    /**
     * Create a reservation
     *
     * @param startDate      start of reservation
     * @param devolutionDate end of reservation
     * @param code           code generated automatically
     * @param car_id         id of car to reserve
     * @param client_id      id of client who reserve
     */
    @Modifying
    @Query(value = "INSERT INTO RESERVATION (START_DATE, DEVOLUTION_DATE,CODE, CAR_ID, CLIENT_ID, reservation_status) VALUES (?1, ?2, ?3, ?4, ?5, 'ACTIVE')", nativeQuery = true)
    void createReservation(String startDate, String devolutionDate, String code, int car_id, int client_id);


    /**
     * Consult
     *
     * @param idCar car want to reserve
     * @param start start Date of the reservation
     * @param end   end Date of the reservation
     * @return if exist any reservation, return a List for validate if it's null
     * @Deprecated
     */
    @Query(value = "SELECT * FROM reservation WHERE car_id = :carToReserve AND DEVOLUTION_DATE >= :startDate AND START_DATE <= :endDate", nativeQuery = true)
    List<Reservation> isAvailableCarIn(@Param("carToReserve") int idCar,
                                       @Param("startDate") Date start,
                                       @Param("endDate") Date end);

    /**
     * Reservations actives for the date {reservationsIn}
     *
     * @param reservationsIn any Date between any reservation
     * @return List of active reservations
     */
    @Query(value = "SELECT * FROM reservation WHERE start_date <= :reservationsIn AND devolution_date >= :reservationsIn AND reservation_status = 'ACTIVE'", nativeQuery = true)
    List<Reservation> getReservationsActiveIn(@Param("reservationsIn") Date reservationsIn);


    /**
     * Reservations made for between these two dates
     *
     * @param dateMin minimum date of start the reservation
     * @param maxDate maximum date of devolution in a reservation
     * @return List of reservations
     */
    @Query(value = "SELECT * FROM reservation WHERE start_date >= :minDate AND devolution_date <= :maxDate", nativeQuery = true)
    List<Reservation> getReservationsBetweenDates(@Param("minDate") Date dateMin,
                                                  @Param("maxDate") Date maxDate);

    /**
     * Find the client for its key, then and inner join of reservation table and get all reservations
     *
     * @param key of client
     * @return List of reservations
     */
    @Query(value = "SELECT * FROM CLIENT INNER JOIN RESERVATION ON CLIENT.ID_CLIENT=RESERVATION.ID_CLIENT WHERE CLIENT.KEY_CLIENT LIKE :key", nativeQuery = true)
    List<Reservation> getAllReservationsByClientKey(String key);

    /**
     * Find the client for its email, then and inner join of reservation table and get all reservations
     *
     * @param email of a client
     * @return List of reservations
     */
    @Query(value = "SELECT * FROM CLIENT INNER JOIN RESERVATION ON CLIENT.ID_CLIENT=RESERVATION.ID_CLIENT WHERE CLIENT.EMAIL  = ?1", nativeQuery = true)
    List<Reservation> getAllReservationsByClientEmail(String email);

    /**
     * Find the client for its key, then and inner join of reservation table and get all ACTIVE reservations
     *
     * @param key of a client
     * @return List of active reservations
     */
    @Query(value = "SELECT * FROM CLIENT INNER JOIN RESERVATION ON CLIENT.ID_CLIENT=RESERVATION.ID_CLIENT WHERE CLIENT.KEY_CLIENT LIKE :key AND RESERVATION_STATUS  = 1", nativeQuery = true)
    List<Reservation> getActiveClientReservation(String key);


    //------------------------------ QUERIES UNUSED

    @Query("SELECT c.client, COUNT(c.client) from Reservation AS c group by c.client order by COUNT(c.client)DESC")
    List<Object[]> countTotalReservationsByClient();

}

