package com.retos.rentacar.modelo.Entity.Reservation;


import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum ReservationStatus {

    //reservation status until a response is received to reserve the vehicle
    REQUESTED,

    //The car is owned by the customer
    ACTIVE,
    
    //Canceled by system or administrator
    CANCELLED,

    //Postponed for customer support
    POSTPONED,

    //Denied by the system
    DENIED,

    //When the reservation passed successful
    COMPLETED

}

