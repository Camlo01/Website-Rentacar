
package com.retos.rentacar.servicios;

import com.retos.rentacar.modelo.DTO.ReservationDTO;
import com.retos.rentacar.modelo.Entity.Client.Client;
import com.retos.rentacar.modelo.Entity.Client.KeyClient;
import com.retos.rentacar.modelo.Entity.Reservation.Reservation;
import com.retos.rentacar.modelo.Entity.Reservation.ReservationCode;
import com.retos.rentacar.modelo.Entity.Reservation.ReservationStatus;
import com.retos.rentacar.repositorio.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ReservationServices {

    @Autowired
    private ReservationRepository repository;
    @Autowired
    private ClientServices clientServices;

    /**
     * Method in charge of get Reservation by id
     *
     * @param id of reservation to find
     * @return reservation
     */
    public Reservation getReservationById(int id) {
        Optional<Reservation> reservationOptional = repository.getReservationById(id);
        return reservationOptional.orElse(null);
    }

    /**
     * Method in charge of return a Reservation by its code
     *
     * @param code of reservation to find
     * @return Reservation
     */
    public Reservation getReservationByCode(String code) {
        Optional<Reservation> reservationOptional = repository.getReservationByCode(code);
        return reservationOptional.orElse(null);
    }

    /**
     * Method in charge of return reservations of a client
     *
     * @param email of the client of who find the reservations
     * @return List of Reservations
     */
    public List<Reservation> getReservationsOfClient(String email) {
        return repository.getReservationsOfClientByEmail(email);
    }

    /**
     * Method in charge of return reservations by status of a client
     *
     * @param email  owner of the reservations
     * @param status status of the reservations to find
     * @return List of reservations
     */
    public List<Reservation> getReservationsOfClientByStatus(String email, String status) {
        return repository.getReservationsOfClientByEmailAndStatus(email, status);
    }

    /**
     * Method in charge of return reservations of a client Between dates
     *
     * @param email of the reservations owner
     * @param start from when select the reservations
     * @param end   maximum date of reservation was made
     * @return List of Reservation
     */
    public List<Reservation> getReservationsOfClientBetweenDates(String email, String start, String end) {
        Date startDate = convertToDate(start);
        Date endDate = convertToDate(end);

        return repository.getReservationsOfClientByEmailBetweenDates(email, startDate, endDate);
    }

    /**
     * Method in charge of create a reservation
     *
     * @param reservation to create
     * @return Reservation if was successfully created
     */
    public boolean createReservation(ReservationDTO reservation) {
        int idCarToReserve = reservation.getCar_id();
        String startDate = reservation.getStartDate();
        String endDate = reservation.getDevolutionDate();
        if (isAvailableIn(idCarToReserve, startDate, endDate)) {
            String code;
            do {
                code = new ReservationCode().generateReservationCode();
            } while (!isAvailableCode(code));
            reservation.setCode(code);
            repository.createReservation(reservation);
            return true;

        }
        return false;
    }

    /**
     * Method in charge to cancel a reservation
     *
     * @param code of reservation to cancel
     * @return boolean value
     */
    public boolean cancelReservation(String code) {

        repository.changeStatusReservation("CANCELLED", code);

        Reservation reservationToValidateStatus = getReservationByCode(code);
        return reservationToValidateStatus.getReservationStatus().equals(ReservationStatus.CANCELLED);
    }

    /**
     * Method in charge of return reservations between dates
     *
     * @param start minimum reservation start date
     * @param end   maximum dates for creating a reservation
     * @return List of reservations
     */
    public List<Reservation> getReservationsBetweenDates(String start, String end) {
        Date startDate = convertToDate(start);
        Date endDate = convertToDate(end);
        return repository.getReservationsBetweenDates(startDate, endDate);
    }

    /**
     * Method in charge of return reservations between dates and
     *
     * @param status of the reservations
     * @param start  minimium reservation start date
     * @param end    maximum dates for creating a reservation
     * @return List of reservations
     */
    public List<Reservation> getReservationByStatusBetweenDates(String status, String start, String end) {
        Date startDate = convertToDate(start);
        Date endDate = convertToDate(end);

        return repository.getReservationByStatusBetweenDates(startDate, endDate, status);
    }

    //resources

    /**
     * Method in charge of evaluate the permissions of a client by its keyClient
     *
     * @param key to evaluate
     * @return boolean value
     */
    public boolean hasPermissions(KeyClient key) {
        return clientServices.hasPermissions(key, false);
    }

    /**
     * Method in charge of evaluate the permissions of a client by its keyClient including support
     *
     * @param key to evaluate
     * @return boolean value
     */
    public boolean hasPermissionsIncludeSupport(KeyClient key) {
        return clientServices.hasPermissions(key, true);
    }

    /**
     * Method in charge of validate if the keyClient is the same of who made the reservation
     *
     * @param reservation to validate the owner
     * @param key         to validate
     * @return true if is the owner of the reservation passed
     */
    public boolean isReservationOwner(ReservationDTO reservation, KeyClient key) {
        Optional<Client> possibleClient = clientServices.getClientById(reservation.getClient_id());
        if (possibleClient.isPresent()) {
            String reservationOwnerKey = possibleClient.get().getKeyClient();
            String whoRequest = key.getKeyClient();
            return Objects.equals(reservationOwnerKey, whoRequest);
        }
        return false;
    }

    /**
     * Method in charge of validate if the keyClient is the owner of a reservation by its code
     *
     * @param code of the reservation to validate
     * @param key  of who validate if is the owner
     * @return boolean value
     */
    public boolean isReservationOwner(String code, KeyClient key) {
        Reservation reservationOptional = getReservationByCode(code);
        KeyClient reservationOwner = new KeyClient(reservationOptional.getClient().getKeyClient());
        return Objects.equals(key, reservationOwner);
    }

    /**
     * Method in charge of validate if a car is available to reserve between two dates
     *
     * @param idCar of car to validate
     * @param start date you want to book
     * @param end   date you want to book
     * @return boolean value
     */
    private boolean isAvailableIn(int idCar, String start, String end) {
        Date startDate = java.sql.Date.valueOf(start);
        Date endDate = java.sql.Date.valueOf(end);
        return repository.isAvailableToReserve(idCar, startDate, endDate);
    }

    /**
     * Method in charge to convert String of date to Date object
     *
     * @param stringDate date in format yyyy-MM-dd
     * @return Date object
     */
    private Date convertToDate(String stringDate) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(stringDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method in charge to validate if a code is available for assign to a reservation
     *
     * @param code to set
     * @return boolean value
     */
    private boolean isAvailableCode(String code) {
        Optional<Reservation> reservation = repository.getReservationByCode(code);
        return reservation.isEmpty();
    }

}
