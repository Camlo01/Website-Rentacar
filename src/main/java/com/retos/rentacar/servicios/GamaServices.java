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
    private GamaRepository metodosCrudGama;

    public Iterable<Gama> getAll() {
        return metodosCrudGama.getAll();
    }

    public Optional<Gama> getGama(int idGama) {
        return metodosCrudGama.getGama(idGama);
    }

    public Gama getGamaToBuild(int idGama) {
        Optional<Gama> toReturn = metodosCrudGama.getGama(idGama);
        return toReturn.get();
    }

    public Gama save(Gama gama) {
        if (gama.getId() == null) {
            return metodosCrudGama.save(gama);
        } else {
            Optional<Gama> evt = metodosCrudGama.getGama(gama.getId());
            if (evt.isEmpty()) {
                return metodosCrudGama.save(gama);
            } else {
                return gama;
            }
        }
    }

    public Gama update(Gama gama) {

        if (gama.getId() != null) {
            Optional<Gama> evt = metodosCrudGama.getGama(gama.getId());
            if (!evt.isEmpty()) {
                if (gama.getName() != null) {
                    evt.get().setName(gama.getName());
                }
                if (gama.getDescription() != null) {
                    evt.get().setDescription(gama.getDescription());
                }
                metodosCrudGama.save(evt.get());
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
            metodosCrudGama.delete(gama);
            return true;
        }).orElse(false);
        return aBoolean;
    }

}