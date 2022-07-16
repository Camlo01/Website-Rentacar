/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.retos.rentacar.interfaces;

import com.retos.rentacar.modelo.Client;

import java.util.Optional;

import com.retos.rentacar.modelo.KeyClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 * @author USUARIO
 */
public interface ClientInterface extends PagingAndSortingRepository<Client, Integer> {

    Optional<Client> findClientByEmail(String email);

    @Query(value = "SELECT * FROM CLIENT WHERE KEY_CLIENT = ?1 ", nativeQuery = true)
    Optional<Client> getClientByKeyClient(String awd);

}
