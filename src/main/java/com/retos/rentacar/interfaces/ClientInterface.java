package com.retos.rentacar.interfaces;

import com.retos.rentacar.modelo.Entity.Client.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

/**
 * @author Camilo
 */
public interface ClientInterface extends JpaRepository<Client, Integer> {

    Optional<Client> findClientByEmail(String email);

    @Query(value = "SELECT * FROM client WHERE key_client LIKE :key ", nativeQuery = true)
    Optional<Client> findClientByKeyClient(@PathVariable("key") String key);

}
