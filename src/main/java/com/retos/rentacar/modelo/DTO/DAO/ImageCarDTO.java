package com.retos.rentacar.modelo.DTO.DAO;

public class ImageCarDTO {

    private int id;
    private String url;
    private int idCar;

    public ImageCarDTO() {
    }

    public ImageCarDTO(int id) {
        this.id = id;
    }

    public ImageCarDTO(int id, String url, int idCar) {
        this.id = id;
        this.url = url;
        this.idCar = idCar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getIdCar() {
        return idCar;
    }

    public void setIdCar(int idCar) {
        this.idCar = idCar;
    }

    @Override
    public String toString() {
        return "ImageCarDTO{" +
                "url='" + url + '\'' +
                ", idCar=" + idCar +
                '}';
    }
}
