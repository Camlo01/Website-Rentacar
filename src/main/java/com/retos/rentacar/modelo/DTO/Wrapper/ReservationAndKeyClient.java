package com.retos.rentacar.modelo.DTO.Wrapper;

import com.retos.rentacar.modelo.DTO.ReservationDTO;
import com.retos.rentacar.modelo.Entity.Client.KeyClient;

public class ReservationAndKeyClient {

    private ReservationDTO reservation;
    private KeyClient key;

    public ReservationAndKeyClient(ReservationDTO reservation, KeyClient keyClient) {
        this.reservation = reservation;
        this.key = keyClient;
    }

    public ReservationDTO getReservation() {
        return reservation;
    }

    public void setReservation(ReservationDTO reservation) {
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
                "reservation=" + reservation.getClient_id() +
                ", keyClient=" + key.getKeyClient() +
                '}';
    }
}
