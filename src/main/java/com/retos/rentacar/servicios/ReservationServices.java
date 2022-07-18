
package com.retos.rentacar.servicios;

import com.retos.rentacar.modelo.Entity.Reservation.Reservation;
import com.retos.rentacar.repositorio.CountClients;
import com.retos.rentacar.repositorio.ReservationRepository;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationServices {
    @Autowired
    private ReservationRepository metodosCrudReservation;
    
    public List<Reservation> getAll(){
         return metodosCrudReservation.getAll();
    }
    
    public Optional<Reservation> getReservation(int idReservation){
        return metodosCrudReservation.getReservation(idReservation);
    }
    
    
    public Reservation save(Reservation reservation){
        if(reservation.getIdReservation()==null){
            return metodosCrudReservation.save(reservation);
        }else{
            Optional<Reservation> evt=metodosCrudReservation.getReservation(reservation.getIdReservation());
            if(evt.isEmpty()){
            return metodosCrudReservation.save(reservation);
            }else{
                return reservation;
            }
        
        
        }
    
    }
   
    public Reservation update(Reservation reservation){
        if(reservation.getIdReservation()!=null){
            Optional<Reservation> evt=metodosCrudReservation.getReservation(reservation.getIdReservation());
            if(!evt.isEmpty()){
                if(reservation.getStartDate()!=null){
                    evt.get().setStartDate(reservation.getStartDate());
                    }
                if(reservation.getDevolutionDate()!=null){
                    evt.get().setDevolutionDate(reservation.getDevolutionDate());
                    }
                metodosCrudReservation.save(evt.get());
                return reservation;
                }
            else{
                return reservation;
            }
        }
        else{
            return reservation;
                }
        }
    
    public boolean deleteReservation (int IdReservation) {
        Boolean aBoolean = getReservation(IdReservation).map(reservation ->{
            metodosCrudReservation.delete(reservation);
            return true;
        }).orElse(false);
        return aBoolean;
    }

    public List<Reservation> findAllByStatus(String status) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Reservation> findAllByStartDateAfterAndStartDateBefore(Date a, Date b) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Object[]> countTotalReservationsByClient() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    public StatusReservas reporteStatusServicio (){
        List<Reservation>completed= metodosCrudReservation.ReservacionStatusRepositorio("completed");
        List<Reservation>cancelled= metodosCrudReservation.ReservacionStatusRepositorio("cancelled");
        
        return new StatusReservas(completed.size(), cancelled.size() );
    }
    
    public List<Reservation> reporteTiempoServicio (String datoA, String datoB){
        SimpleDateFormat parser = new SimpleDateFormat ("yyyy-MM-dd");
        
        Date datoUno = new Date();
        Date datoDos = new Date();
        
        try{
             datoUno = parser.parse(datoA);
             datoDos = parser.parse(datoB);
        }catch(ParseException evt){
            evt.printStackTrace();
        }if(datoUno.before(datoDos)){
            return metodosCrudReservation.ReservacionTiempoRepositorio(datoUno, datoDos);
        }else{
            return new ArrayList<>();
        
        } 
    }

        public List<CountClients> reporteClientesServicio(){
            return metodosCrudReservation.getClientesRepositorio();
        }
    
}
