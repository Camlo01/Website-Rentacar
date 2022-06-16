package com.retos.rentacar.modelo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "gama")
public class Gama {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
//    @Column(name = "id_Gama", nullable = false, unique = true)
    private Integer idGama;
    private String name;
    private String description;
     
    @OneToMany(cascade = {CascadeType.PERSIST},mappedBy="gama")
    @JsonIgnoreProperties("gama")
    private List<Car> cars;

    public Gama() {
    }

    public Integer getIdGama() {
        return idGama;
    }

    public void setIdGama(Integer idGama) {
        this.idGama = idGama;
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

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public Gama(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Gama(Integer idGama, String name, String description, List<Car> cars) {
        this.idGama = idGama;
        this.name = name;
        this.description = description;
        this.cars = cars;
    }
}