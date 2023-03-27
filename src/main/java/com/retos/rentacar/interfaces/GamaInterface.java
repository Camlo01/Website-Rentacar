package com.retos.rentacar.interfaces;

import com.retos.rentacar.modelo.Entity.Gama.Gama;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Camilo
 */
@Repository
public interface GamaInterface extends CrudRepository<Gama, Integer> {

}
