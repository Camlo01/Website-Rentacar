/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.retos.rentacar.repositorio;

import com.retos.rentacar.interfaces.GamaInterface;
import com.retos.rentacar.modelo.Gama;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class GamaRepository {
    
    @Autowired
    private GamaInterface crudGama;
    
    public List<Gama> getAll(){
        return (List<Gama>) crudGama.findAll();
    }
    public Optional <Gama> getGama(int id){
        return crudGama.findById(id);
    }
    public Gama save(Gama gama){
        return crudGama.save(gama);
    }
    public void delete(Gama gama){
        crudGama.delete(gama);
    }
}
