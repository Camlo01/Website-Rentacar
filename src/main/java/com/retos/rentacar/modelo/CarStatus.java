package com.retos.rentacar.modelo;


public enum CarStatus {
    BOOKABLE,
    // Default value to not show immediately
    NOT_AVAILABLE,
    // Is already reserved
    OCCUPIED,
    // Can´t be reserved by regulations
    RESTRICTION,
    // Is on the mechanic
    REPAIR,


}
