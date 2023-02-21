
package com.retos.rentacar.servicios;

import com.retos.rentacar.modelo.DTO.ReservationDTO;
import com.retos.rentacar.modelo.DTO.Wrapper.ReservationAndKeyClient;
import com.retos.rentacar.modelo.Entity.Client.Client;
import com.retos.rentacar.modelo.Entity.Client.KeyClient;
import com.retos.rentacar.modelo.Entity.Reservation.Reservation;
import com.retos.rentacar.modelo.Entity.Reservation.ReservationCode;
import com.retos.rentacar.modelo.Entity.Reservation.ReservationStatus;
import com.retos.rentacar.repositorio.CountClients;
import com.retos.rentacar.repositorio.ReservationRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationServices {

    @Autowired
    private ReservationRepository repository;
    @Autowired
    private ClientServices clientServices;

    /**
     * Metodos crud
     * <p>
     * - Traer todas las reservas
     * - Guardar reservation
     * - actualizar la reservacion
     * - eliminar reservación
     * <p>
     * **********************
     * Queries
     * <p>
     * - traer todas las reservaciones
     * - traer todas las reservaciones por status
     * - tarer reservaciones entre fechas
     * - contar cantidad de reservaciones entre fechas
     */


    // GET
    public List<Reservation> getActiveReservationsOfAClient(KeyClient key) {
        return repository.getActiveClientReservation(key);
    }

    public List<Reservation> getMyReservationHistory(KeyClient key) {
        return repository.getMyReservationHistory(key.getKeyClient());
    }


    public Optional<Reservation> getReservationById(ReservationAndKeyClient body) {
        int id = body.getReservation().getId();
        KeyClient key = body.getKey();

        if (clientServices.hasPermissions(key, false)) {
            return repository.getReservationById(id);
        }

        return Optional.empty();
    }


    public List<Reservation> getAllReservations(KeyClient key) {
        if (clientServices.hasPermissions(key, false)) {
            return repository.getAll();
        }
        return null;
    }


    public List<Reservation> getReservationsOfAClient(Client client, KeyClient key) {
        if (clientServices.hasPermissions(key, true)) {
            return repository.getAllReservationsOfClientByEmail(client.getEmail());
        }
        return null;
    }

    public Optional<Reservation> getReservationByCode(Reservation reservation, KeyClient key) {
        return repository.getReservationByCode(reservation.getCode());
    }

    public List<Reservation> reservationsActiveIn(String toConsult, KeyClient keyClient) {
        if (clientServices.hasPermissions(keyClient, false)) {
            return repository.getReservationsActiveIn(java.sql.Date.valueOf(toConsult));
        }
        return null;
    }

    public List<Reservation> reservationsBetweenDates(String minDate, String maxDate, KeyClient key) {
        if (clientServices.hasPermissions(key, false)) {
            return repository.getReservationsBetweenDates(java.sql.Date.valueOf(minDate), java.sql.Date.valueOf(maxDate));
        }
        return null;
    }

    // POST

    public boolean createReservation(ReservationDTO reservationDTO) {
        int idCar = reservationDTO.getCar_id();
        String startDate = reservationDTO.getStartDate();
        String endDate = reservationDTO.getDevolutionDate();
        if (isAvailableToReserve(idCar, startDate, endDate)) {
            reservationDTO.setCode(new ReservationCode().generateReservationCode());
            repository.createReservation(reservationDTO);
            return true;
        }
        return false;

    }


    // PUT

    public Boolean cancelReservation(Reservation reservation, KeyClient key) {

        Reservation reservationToCancel = (repository.getReservationById(reservation.getId())).get();

        if (clientServices.hasPermissions(key, false) || sameKeyOfWhoBooked(reservationToCancel, key)) {

            reservationToCancel.setReservationStatus(ReservationStatus.CANCELLED);
            repository.save(reservationToCancel);
            return true;
        }
        return false;

    }

    public Reservation editReservation(Reservation reservation, KeyClient key) {

        if (clientServices.hasPermissions(key, true)) {
            return update(reservation);
        }
        return null;
    }

    public List<Reservation> datesBetweenYourReservation(ReservationDTO reservationDTO) {
        List<Reservation> reservations = new ArrayList<>();

        Reservation previous = repository.previousReservation(reservationDTO.getCar_id(), reservationDTO.getDevolutionDate()).get();
        Reservation next = repository.nextReservation(reservationDTO.getCar_id(), reservationDTO.getDevolutionDate()).get();

        reservations.add(previous);
        reservations.add(next);
        return reservations;
    }

    // DELETE


    //resources


    private Boolean sameKeyOfWhoBooked(Reservation reservationMade, KeyClient keyOfWhoBooked) {
        String toCompare = reservationMade.getClient().getKeyClient();
        String key = keyOfWhoBooked.getKeyClient();
        return key.equals(toCompare);
    }

    private Boolean isAvailableToReserve(int idCar, String start, String end) {
        Date startDate = java.sql.Date.valueOf(start);
        Date endDate = java.sql.Date.valueOf(end);
        return repository.isCarAvailableToReserve(idCar, startDate, endDate);
    }

    ;

    //-------
    public List<Reservation> getAll() {
        return repository.getAll();
    }

    public Optional<Reservation> getReservation(int idReservation) {
        return repository.getReservation(idReservation);
    }


    public Reservation save(Reservation reservation) {
        if (reservation.getId() == null) {
            return repository.save(reservation);
        } else {
            Optional<Reservation> evt = repository.getReservation(reservation.getId());
            if (evt.isEmpty()) {
                return repository.save(reservation);
            } else {
                return reservation;
            }


        }

    }

    public Reservation update(Reservation reservation) {


        if (reservation.getId() != null) {
            Optional<Reservation> evt = repository.getReservation(reservation.getId());
            if (!evt.isEmpty()) {
                if (reservation.getStartDate() != null) {
                    evt.get().setStartDate(reservation.getStartDate());
                }
                if (reservation.getDevolutionDate() != null) {
                    evt.get().setDevolutionDate(reservation.getDevolutionDate());
                }
                repository.save(evt.get());
                return reservation;
            } else {
                return reservation;
            }
        } else {
            return reservation;
        }
    }

    //Unused queries

    public boolean deleteReservation(int IdReservation) {
        Boolean aBoolean = getReservation(IdReservation).map(reservation -> {
            repository.delete(reservation);
            return true;
        }).orElse(false);
        return aBoolean;
    }

    public List<Reservation> findAllByStatus(String status) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Reservation> findAllByStartDateAfterAndStartDateBefore(Date a, Date b) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Object[]> countTotalReservationsByClient() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    public List<Reservation> reporteTiempoServicio(String datoA, String datoB) {
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");

        Date datoUno = new Date();
        Date datoDos = new Date();

        try {
            datoUno = parser.parse(datoA);
            datoDos = parser.parse(datoB);
        } catch (ParseException evt) {
            evt.printStackTrace();
        }
        if (datoUno.before(datoDos)) {
            return repository.ReservacionTiempoRepositorio(datoUno, datoDos);
        } else {
            return new ArrayList<>();

        }
    }

    public List<CountClients> reporteClientesServicio() {
        return repository.getClientesRepositorio();
    }

}
