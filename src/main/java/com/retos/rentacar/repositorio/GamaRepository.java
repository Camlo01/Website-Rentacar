/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.retos.rentacar.repositorio;

import com.retos.rentacar.interfaces.GamaInterface;
import com.retos.rentacar.modelo.Entity.Gama.Gama;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GamaRepository {

    @Autowired
    private GamaInterface gamaInterface;

    public void deleteById(Integer id) {
        gamaInterface.deleteById(id);
    }

    public Iterable<Gama> getAll() {
        return gamaInterface.findAll();
    }

    public Optional<Gama> getGamaById(int id) {
        return gamaInterface.findById(id);
    }

    public Gama save(Gama gama) {
        return gamaInterface.save(gama);
    }

    public void delete(Gama gama) {
        gamaInterface.delete(gama);
    }

}
