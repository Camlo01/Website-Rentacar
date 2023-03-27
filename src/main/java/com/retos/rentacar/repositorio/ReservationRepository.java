package com.retos.rentacar.repositorio;

import com.retos.rentacar.interfaces.ReservationInterface;
import com.retos.rentacar.modelo.DTO.ReservationDTO;
import com.retos.rentacar.modelo.Entity.Reservation.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@Repository
public class ReservationRepository {

    @Autowired
    private ReservationInterface reservationInterface;

    /**
     * Method in charge of return a reservation by its id
     *
     * @param id of reservation to return
     * @return Optional of reservation
     */
    public Optional<Reservation> getReservationById(int id) {
        return reservationInterface.findReservationById(id);
    }

    /**
     * Method in charge of return a reservation by its code
     *
     * @param code of reservation to return
     * @return Optional of Reservation
     */
    public Optional<Reservation> getReservationByCode(String code) {
        return reservationInterface.findReservationByCode(code);
    }

    /**
     * Method in charge of return reservations of a client
     *
     * @param email by which to bring the reservations
     * @return List of Reservations
     */
    public List<Reservation> getReservationsOfClientByEmail(String email) {
        return reservationInterface.getAllReservationsByClientEmail(email);
    }

    /**
     * Method in charge of return
     *
     * @param email  of the client for which to bring the reservations
     * @param status of the reservations
     * @return List of reservations
     */
    public List<Reservation> getReservationsOfClientByEmailAndStatus(String email, String status) {
        return reservationInterface.getAllReservationsOfClientByEmailAndStatus(email, status);
    }

    /**
     * Method in charge of get the reservations of a client between dates
     *
     * @param email of the client for which get the reservations
     * @param start date from when get the reservations
     * @param end   date until when get the reservations
     * @return List of reservations
     */
    public List<Reservation> getReservationsOfClientByEmailBetweenDates(String email, Date start, Date end) {
        return reservationInterface.getReservationOfClientByEmailBetweenDates(email, start, end);
    }

    /**
     * Method in charge of create a reservation
     *
     * @param reservation to save
     */
    public void createReservation(ReservationDTO reservation) {
        String startDate = reservation.getStartDate();
        String endDate = reservation.getDevolutionDate();
        String code = reservation.getCode();
        int idCar = reservation.getCar_id();
        int idClient = reservation.getClient_id();
        reservationInterface.createReservation(startDate, endDate, code, idCar, idClient);
    }

    /**
     * Method in charge or change the status of a reservation
     *
     * @param newStatus to set to the reservation
     * @param code      of the reservation to change the status
     */
    public void changeStatusReservation(String newStatus, String code) {
        reservationInterface.changeStatusOfReservation(newStatus, code);
    }

    /**
     * Method to validate the availability of a car between two dates to be reserved
     *
     * @param id    of car
     * @param start from when to bring reservations
     * @param end   until when to bring reservations
     * @return boolean value
     */
    public boolean isAvailableToReserve(int id, Date start, Date end) {
        List<Reservation> carReserved = reservationInterface.reservationsOfACarBetweenDates(id, start, end);

        return carReserved.isEmpty();
    }

    /**
     * Method in charge of return reservations between two dates
     *
     * @param startDate from when to bring reservations
     * @param endDate   until when to bring reservations
     * @return list of reservations
     */
    public List<Reservation> getReservationsBetweenDates(Date startDate, Date endDate) {
        return reservationInterface.getReservationsBetweenDates(startDate, endDate);
    }

    /**
     * Method in charge of return reservations by status between dates
     *
     * @param startDate from when to bring reservations
     * @param endDate   until when to bring reservations
     * @param status    of the reservations
     * @return List of reservations
     */
    public List<Reservation> getReservationByStatusBetweenDates(Date startDate, Date endDate, String status) {
        return reservationInterface.getReservationsBetweenDatesAndStatus(startDate, endDate, status);
    }

}
