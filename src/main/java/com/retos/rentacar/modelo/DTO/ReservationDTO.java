package com.retos.rentacar.modelo.DTO;

import java.util.Date;

import com.retos.rentacar.modelo.Entity.Reservation.ReservationCode;

public class ReservationDTO {

    private String startDate;
    private String devolutionDate;
    private String code;
    private int car_id;
    private int client_id;

    public ReservationDTO(String startDate, String devolutionDate, int car_id, int client_id) {
        this.startDate =startDate;
        this.devolutionDate = devolutionDate;
        this.code = new ReservationCode().generateReservationCode();
        this.car_id = car_id;
        this.client_id = client_id;
    }


    public ReservationDTO() {
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getDevolutionDate() {
        return devolutionDate;
    }

    public void setDevolutionDate(String devolutionDate) {
        this.devolutionDate = devolutionDate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getCar_id() {
        return car_id;
    }

    public void setCar_id(int car_id) {
        this.car_id = car_id;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }
}
