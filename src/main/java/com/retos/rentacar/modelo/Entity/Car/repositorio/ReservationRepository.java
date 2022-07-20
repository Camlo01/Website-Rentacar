package com.retos.rentacar.repositorio;

import com.retos.rentacar.interfaces.ReservationInterface;
import com.retos.rentacar.modelo.Entity.Client.Client;
import com.retos.rentacar.modelo.Entity.Reservation.Reservation;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class ReservationRepository {
    @Autowired
    private ReservationInterface crudReservation;
    
    public List<Reservation> getAll(){
        return (List<Reservation>) crudReservation.findAll();
    }
    public Optional <Reservation> getReservation(int id){
        return crudReservation.findById(id);
    }
    
    public Reservation save(Reservation reservation){
        return crudReservation.save(reservation);
    }
    public void delete(Reservation reservation){
        crudReservation.delete(reservation);
    }
    
    /**************************************************/
    public List<Reservation> ReservacionStatusRepositorio (String status){
         return crudReservation.findAllByStatus(status);
     }
     
     public List<Reservation> ReservacionTiempoRepositorio (Date a, Date b){
         return crudReservation.findAllByStartDateAfterAndStartDateBefore(a, b);
     
     }
     
     public List<CountClients> getClientesRepositorio(){
         List<CountClients> res = new ArrayList<>();
         List<Object[]> report = crudReservation.countTotalReservationsByClient();
         for(int i=0; i<report.size(); i++){
             res.add(new CountClients((Long)report.get(i)[1],(Client) report.get(i)[0]));
         }
         return res;
     }
}
