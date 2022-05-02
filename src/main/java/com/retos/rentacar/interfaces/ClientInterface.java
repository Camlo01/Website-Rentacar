/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.retos.rentacar.interfaces;

import com.retos.rentacar.modelo.Client;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author USUARIO
 */
public interface ClientInterface extends CrudRepository<Client,Integer> {
    
}
