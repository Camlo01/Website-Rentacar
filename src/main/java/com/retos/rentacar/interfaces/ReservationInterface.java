package com.retos.rentacar.interfaces;

import com.retos.rentacar.modelo.Entity.Reservation.Reservation;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Camilo
 */
@Transactional
public interface ReservationInterface extends PagingAndSortingRepository<Reservation, Integer> {

    /**
     * Method in charge of return a reservation by id
     *
     * @param id of reservation to find
     * @return Optional of Reservation
     */
    Optional<Reservation> findReservationById(int id);

    /**
     * Method in charge of return a reservation by its code
     *
     * @param code of reservation to find
     * @return Optional of Reservation
     */
    Optional<Reservation> findReservationByCode(String code);

    /**
     * Create a reservation
     *
     * @param startDate      date the reservation begins
     * @param devolutionDate date a reservation is delivered
     * @param code           code generated automatically to identify the reservation
     * @param car_id         id of car to reserve
     * @param client_id      id of client who reserve
     */
    @Modifying
    @Query(value = "INSERT INTO RESERVATION (START_DATE, DEVOLUTION_DATE,CODE, CAR_ID, CLIENT_ID, reservation_status) " +
            "VALUES (?1, ?2, ?3, ?4, ?5, 'ACTIVE')",
            nativeQuery = true)
    void createReservation(String startDate, String devolutionDate, String code, int car_id, int client_id);

    /**
     * Method in charge of change the status of a reservation found by its code
     *
     * @param status to set
     * @param code   of the reservation o update
     */
    @Modifying
    @Query(value = "UPDATE reservation SET reservation_status = :newStatus WHERE code LIKE :codeReservation", nativeQuery = true)
    void changeStatusOfReservation(@Param("newStatus") String status, @Param("codeReservation") String code);

    /**
     * Method to check availability of a vehicle between two dates
     *
     * @param idCar if of car wanted to reserve
     * @param start start Date of the reservation
     * @param end   end Date of the reservation
     * @return if exist any reservation, return a List for validate if it's null
     */
    @Query(value = "SELECT * FROM reservation " +
            "       WHERE car_id = :carToReserve AND DEVOLUTION_DATE >= :startDate AND START_DATE <= :endDate",
            nativeQuery = true)
    List<Reservation> reservationsOfACarBetweenDates(@Param("carToReserve") int idCar,
                                                     @Param("startDate") Date start,
                                                     @Param("endDate") Date end);

    /**
     * get all the reservations that belongs to a client that is selected by its email
     *
     * @param email of a client
     * @return List of reservations
     */
    @Query(value = " SELECT * FROM reservation " +
            "INNER JOIN client ON client.id_client = reservation.client_id " +
            "WHERE client.email LIKE :email ", nativeQuery = true)
    List<Reservation> getAllReservationsByClientEmail(@Param("email") String email);

    /**
     * get reservations by status between dates that belongs a client what is found by its email
     *
     * @param email  of the owner of the reservation
     * @param status of the reservations
     * @return List of reservation
     */
    @Query(value = "SELECT * FROM reservation " +
            "INNER JOIN client ON client.id_client = reservation.client_id " +
            "WHERE client.email LIKE :email AND reservation_status LIKE :status ", nativeQuery = true)
    List<Reservation> getAllReservationsOfClientByEmailAndStatus(@Param("email") String email,
                                                                 @Param("status") String status);

    /**
     * get reservations between dates that belongs to a client what is found by its email
     *
     * @param email     of the client
     * @param startDate minimum date of start the reservation
     * @param endDate   maximum date of devolution in a reservation
     * @return List of reservations
     */
    @Query(value = "SELECT * FROM reservation INNER JOIN client ON client.id_client = reservation.client_id " +
            "WHERE client.email LIKE :email " +
            "AND start_date >= :startDate AND devolution_date <= :endDate ", nativeQuery = true)
    List<Reservation> getReservationOfClientByEmailBetweenDates(@Param("email") String email,
                                                                @Param("startDate") Date startDate,
                                                                @Param("endDate") Date endDate);

    /**
     * Reservations made for between these two dates
     *
     * @param minDate minimum date of start the reservation
     * @param maxDate maximum date of devolution in a reservation
     * @return List of reservations
     */
    @Query(value = "SELECT * FROM reservation " +
            "WHERE start_date >= :minDate AND devolution_date <= :maxDate",
            nativeQuery = true)
    List<Reservation> getReservationsBetweenDates(@Param("minDate") Date minDate,
                                                  @Param("maxDate") Date maxDate);

    /**
     * Query of reservations made for between two dates with a status
     *
     * @param minDate minimum date from the creation of the reservation
     * @param maxDate maximum date from the creation of the reservation
     * @param status  status of the reservations
     * @return List of reservations
     */
    @Query(value = "SELECT * FROM reservation " +
            "WHERE start_date >= :minDate AND devolution_date <= :maxDate " +
            "AND reservation_status LIKE :status",
            nativeQuery = true)
    List<Reservation> getReservationsBetweenDatesAndStatus(@Param("minDate") Date minDate,
                                                           @Param("maxDate") Date maxDate,
                                                           @Param("status") String status);

    //UNUSED METHODS

    /**
     * Reservations actives for the date {reservationsIn}
     *
     * @param reservationsIn any Date between any reservation
     * @return List of active reservations
     */
    @Query(value = "SELECT * FROM reservation " +
            "WHERE start_date <= :reservationsIn AND devolution_date >= :reservationsIn AND reservation_status = 'ACTIVE'",
            nativeQuery = true)
    List<Reservation> getReservationsActiveIn(@Param("reservationsIn") Date reservationsIn);

    //------------------------------ QUERIES UNUSED

    @Query("SELECT c.client, COUNT(c.client) from Reservation AS c group by c.client order by COUNT(c.client)DESC")
    List<Object[]> countTotalReservationsByClient();

}

