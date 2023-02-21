package com.retos.rentacar.modelo.DTO.Wrapper;

import com.retos.rentacar.modelo.Entity.Client.KeyClient;
import com.retos.rentacar.modelo.Entity.Reservation.Reservation;

public class ReservationAndKeyClient {

    private Reservation reservation;
    private KeyClient key;


    public ReservationAndKeyClient(Reservation reservation, KeyClient keyClient) {
        this.reservation = reservation;
        this.key = keyClient;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public KeyClient getKey() {
        return key;
    }

    public void setKey(KeyClient key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "ReservationAndKeyclient{" +
                "reservation=" + reservation.getClient() +
                ", keyClient=" + key.getKeyClient() +
                '}';
    }
}
