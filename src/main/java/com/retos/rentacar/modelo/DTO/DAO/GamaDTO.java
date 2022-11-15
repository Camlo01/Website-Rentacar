package com.retos.rentacar.modelo.DTO.DAO;

import com.retos.rentacar.modelo.Entity.Gama.Gama;

public class GamaDTO {

    private Integer id;
    private String name;
    private String description;

    public GamaDTO() {
    }

    public GamaDTO(Gama gama) {
        this.id = gama.getIdGama();
        this.name = gama.getName();
        this.description = gama.getDescription();
    }


    public GamaDTO(Integer id) {
        this.id = id;
    }

    public GamaDTO(Integer id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
