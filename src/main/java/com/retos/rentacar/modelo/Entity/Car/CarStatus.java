package com.retos.rentacar.modelo.Entity.Car;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum CarStatus {

    BOOKABLE,
    
    // Default value to not show immediately
    NOT_AVAILABLE,
    
    // Is already reserved
    OCCUPIED,
    
    // Can´t be reserved by regulations
    RESTRICTION,
    
    //The car is on the mechanic
    REPAIR,

}
