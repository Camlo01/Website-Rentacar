package com.retos.rentacar.servicios.modelo.Entity.Car;


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
