package com.retos.rentacar.modelo.Entity.Gama;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.retos.rentacar.modelo.DTO.DAO.GamaDTO;
import com.retos.rentacar.modelo.Entity.Car.Car;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "gama")
public class Gama implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_gama", nullable = false, unique = true)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;

//    @JsonIgnore
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "gama")
    @JsonIgnoreProperties("gama")
    private List<Car> cars;

    public Gama() {
    }
    public Gama(GamaDTO gamaDTO) {
        this.id = gamaDTO.getId();
        this.name = gamaDTO.getName();
        this.description = gamaDTO.getDescription();
    }

    public Gama(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Gama(Integer id, String name, String description, List<Car> cars) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cars = cars;
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

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }




}