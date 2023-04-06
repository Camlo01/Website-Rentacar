/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.retos.rentacar.servicios;

import com.retos.rentacar.modelo.Entity.Client.KeyClient;
import com.retos.rentacar.modelo.Entity.Gama.Gama;
import com.retos.rentacar.repositorio.GamaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class GamaServices {

    @Autowired
    private GamaRepository repository;

    @Autowired
    private ClientServices clientServices;

    /**
     * Return all gamas in the database
     *
     * @return Iterable of Gama
     */
    public Iterable<Gama> getAll() {
        return repository.getAll();
    }

    /**
     * Method in charge of return a car by its id
     *
     * @param id of gama to find
     * @return Optional of Gama
     */
    public Optional<Gama> getGamaById(int id) {
        return repository.getGamaById(id);
    }

    /**
     * Method in charge to save a new gama
     *
     * @param gama to save
     * @return the gama if was successfully saved
     */
    public Gama saveGama(Gama gama) {
        return repository.save(gama);
    }

    /**
     * Method in charge to update a gama
     *
     * @param gama updated to set
     * @return null if it could not be updated
     */
    public Gama updateGama(Gama gama) {
        return update(gama);
    }

    /**
     * Method in charge to delete a gama by its id
     *
     * @param gama to delete
     * @return true if was successfully deleted
     */
    public boolean deleteGama(Gama gama) {
        Optional<Gama> gamaToDeleteObtained = repository.getGamaById(gama.getId());
        if (gamaToDeleteObtained.isPresent()) {
            Gama gamaToDelete = gamaToDeleteObtained.get();
            repository.deleteById(gamaToDelete.getId());
            return true;
        }
        return false;
    }

    // UTILS METHODS

    /**
     * method in charge to update a gama
     *
     * @param gama to update
     * @return null if the gama could not be updated
     */
    private Gama update(Gama gama) {
        int idOfGamaToUpdate = gama.getId();

        Optional<Gama> gamaObtained = repository.getGamaById(idOfGamaToUpdate);

        if (gamaObtained.isPresent()) {
            Gama gamaInDB = gamaObtained.get();
            boolean isThereAnyChange = false;

            if (Objects.equals(gama.getName(), gamaInDB.getName())) {
                gamaInDB.setName(gama.getName());
                isThereAnyChange = true;
            }

            if (Objects.equals(gama.getDescription(), gamaInDB.getDescription())) {
                gamaInDB.setDescription(gama.getDescription());
                isThereAnyChange = true;
            }

            return (isThereAnyChange) ? repository.save(gamaInDB) : null;
        }
        return null;
    }

    /**
     * Method in charge of validate the permissions of a client by its keyClient
     *
     * @param key to validate
     * @return boolean value
     */
    public boolean hasPermissions(KeyClient key) {
        return clientServices.hasPermissions(key, false);
    }

}