/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.retos.rentacar.servicios;

import com.retos.rentacar.modelo.Entity.Gama.Gama;
import com.retos.rentacar.repositorio.GamaRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GamaServices {

    @Autowired
    private GamaRepository gamaRepository;

    public Iterable<Gama> getAll() {
        return gamaRepository.getAll();
    }

    public Optional<Gama> getGama(int idGama) {
        return gamaRepository.getGamaById(idGama);
    }

    public Gama getGamaToBuild(int idGama) {
        Optional<Gama> toReturn = gamaRepository.getGamaById(idGama);
        return toReturn.get();
    }

    public Gama save(Gama gama) {
        if (gama.getId() == null) {
            return gamaRepository.save(gama);
        } else {
            Optional<Gama> evt = gamaRepository.getGamaById(gama.getId());
            if (evt.isEmpty()) {
                return gamaRepository.save(gama);
            } else {
                return gama;
            }
        }
    }

    public Gama update(Gama gama) {

        if (gama.getId() != null) {
            Optional<Gama> evt = gamaRepository.getGamaById(gama.getId());
            if (!evt.isEmpty()) {
                if (gama.getName() != null) {
                    evt.get().setName(gama.getName());
                }
                if (gama.getDescription() != null) {
                    evt.get().setDescription(gama.getDescription());
                }
                gamaRepository.save(evt.get());
                return gama;
            } else {
                return gama;
            }
        } else {
            return gama;
        }
    }

    public boolean deleteGama(int idGama) {
        Boolean aBoolean = getGama(idGama).map(gama -> {
            gamaRepository.delete(gama);
            return true;
        }).orElse(false);
        return aBoolean;
    }

}