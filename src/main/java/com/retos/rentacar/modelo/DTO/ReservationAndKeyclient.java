package com.retos.rentacar.modelo.DTO;

import com.retos.rentacar.modelo.Entity.Client.KeyClient;
import com.retos.rentacar.modelo.Entity.Reservation.Reservation;

public class ReservationAndKeyclient {

    private Reservation reservation;
    private KeyClient keyClient;


    public ReservationAndKeyclient(Reservation reservation, KeyClient keyClient) {
        this.reservation = reservation;
        this.keyClient = keyClient;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public KeyClient getKeyClient() {
        return keyClient;
    }

    public void setKeyClient(KeyClient keyClient) {
        this.keyClient = keyClient;
    }
}
