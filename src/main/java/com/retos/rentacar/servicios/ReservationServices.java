
package com.retos.rentacar.servicios;

import com.retos.rentacar.modelo.DTO.ReservationAndKeyclient;
import com.retos.rentacar.modelo.Entity.Client.Client;
import com.retos.rentacar.modelo.Entity.Client.KeyClient;
import com.retos.rentacar.modelo.Entity.Reservation.Reservation;
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
    public Optional<Reservation> getReservationById(ReservationAndKeyclient body) {
        int id = body.getReservation().getIdReservation();
        KeyClient key = body.getKeyClient();

        if (clientServices.hasPermissions(key, false)) {
            return repository.getReservationById(id);
        }

        return Optional.empty();
    }

    public List<Reservation> getActiveReservationsOfAClient(KeyClient key) {
        return repository.getActiveClientReservation(key);
    }


    public List<Reservation> getAllReservations(KeyClient key) {
        if (clientServices.hasPermissions(key, false)) {
            return repository.getAll();
        }
        return null;
    }

    public List<Reservation> getMyReservationHistory(KeyClient key) {
        return repository.getMyReservationHistory(key.getKeyClient());
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


    // POST

    public Reservation reservateVehicle(Reservation reservation) {
        return save(reservation);
    }


    // PUT

    public Reservation cancelReservation(Reservation reservation, KeyClient key) {

        Reservation reservationToCancel = (repository.getReservationById(reservation.getIdReservation())).get();
        reservationToCancel.setReservationStatus(ReservationStatus.CANCELLED);

        if (clientServices.hasPermissions(key, false)) {
            return cancelTheReservation(reservationToCancel);
        } else if (sameKeyOfWhoBooked(reservationToCancel, key)) {
            return cancelTheReservation(reservationToCancel);
        }
        return null;

    }

    public Reservation editReservation(Reservation reservation, KeyClient key) {

        if (clientServices.hasPermissions(key, true)) {
            return update(reservation);
        }
        return null;
    }

    // DELETE


    //resources

    private Reservation cancelTheReservation(Reservation reservation) {
        return repository.save(reservation);
    }

    private Boolean sameKeyOfWhoBooked(Reservation reservationMade, KeyClient keyOfWhoBooked) {
        String toCompare = reservationMade.getClient().getKeyClient();
        String key = keyOfWhoBooked.getKeyClient();
        return key.equals(toCompare);
    }

    //-------
    public List<Reservation> getAll() {
        return repository.getAll();
    }

    public Optional<Reservation> getReservation(int idReservation) {
        return repository.getReservation(idReservation);
    }


    public Reservation save(Reservation reservation) {
        if (reservation.getIdReservation() == null) {
            return repository.save(reservation);
        } else {
            Optional<Reservation> evt = repository.getReservation(reservation.getIdReservation());
            if (evt.isEmpty()) {
                return repository.save(reservation);
            } else {
                return reservation;
            }


        }

    }

    public Reservation update(Reservation reservation) {
        if (reservation.getIdReservation() != null) {
            Optional<Reservation> evt = repository.getReservation(reservation.getIdReservation());
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
