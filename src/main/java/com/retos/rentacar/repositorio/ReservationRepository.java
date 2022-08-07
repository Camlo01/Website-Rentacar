package com.retos.rentacar.repositorio;

import com.retos.rentacar.interfaces.ClientInterface;
import com.retos.rentacar.interfaces.ReservationInterface;
import com.retos.rentacar.modelo.DTO.ReservationDTO;
import com.retos.rentacar.modelo.Entity.Client.Client;
import com.retos.rentacar.modelo.Entity.Client.KeyClient;
import com.retos.rentacar.modelo.Entity.Reservation.Reservation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.retos.rentacar.modelo.Entity.Reservation.ReservationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class ReservationRepository {
    @Autowired
    private ReservationInterface crudReservation;
    @Autowired
    private ClientInterface clientInterface;

    public Optional<Reservation> getReservationById(int id) {
        return crudReservation.findReservationByidReservation(id);
    }

    public List<Reservation> getAll() {
        return (List<Reservation>) crudReservation.findAll();
    }

    public List<Reservation> getMyReservationHistory(String key) {
        return crudReservation.getAllReservationsByClientKey(key);
    }

    public List<Reservation> getAllReservationsOfClientByEmail(String email) {
        return crudReservation.getAllReservationsByClientEmail(email);
    }

    public List<Reservation> getActiveClientReservation(KeyClient key) {
        return crudReservation.getActiveClientReservation(key.getKeyClient());
    }


    public Optional<Reservation> getReservationByCode(String code) {
        return crudReservation.findReservationByCode(code);
    }

    public Optional<Reservation> getReservation(int id) {
        return crudReservation.findById(id);
    }

    public void createReservation(ReservationDTO reservationDTO) {
        crudReservation.createReservation(reservationDTO.getStartDate(), reservationDTO.getDevolutionDate(), reservationDTO.getCode(), reservationDTO.getCar_id(), reservationDTO.getClient_id());
    }

    public Reservation save(Reservation reservation) {
        return crudReservation.save(reservation);
    }

    public void delete(Reservation reservation) {
        crudReservation.delete(reservation);
    }

    /**************************************************/
    public List<Reservation> ReservacionStatusRepositorio(ReservationStatus status) {
        return crudReservation.findAllByReservationStatus(status);
    }

    public List<Reservation> ReservacionTiempoRepositorio(Date a, Date b) {
        return crudReservation.findAllByStartDateAfterAndStartDateBefore(a, b);

    }

    public List<CountClients> getClientesRepositorio() {
        List<CountClients> res = new ArrayList<>();
        List<Object[]> report = crudReservation.countTotalReservationsByClient();
        for (int i = 0; i < report.size(); i++) {
            res.add(new CountClients((Long) report.get(i)[1], (Client) report.get(i)[0]));
        }
        return res;
    }
}
