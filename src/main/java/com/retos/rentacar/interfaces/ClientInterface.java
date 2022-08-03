/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.retos.rentacar.interfaces;

import com.retos.rentacar.modelo.Entity.Client.Client;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author USUARIO
 */
public interface ClientInterface extends JpaRepository<Client, Integer> {

    Optional<Client> findClientByEmail(String email);

    //    @Query(value = "SELECT * FROM CLIENT WHERE KEY_CLIENT LIKE '?1' ", nativeQuery = true)
    Optional<Client> findByKeyClient(String key);

}
