
package com.retos.rentacar.controlador;

import com.retos.rentacar.modelo.Reservation;
import com.retos.rentacar.repositorio.CountClients;
import com.retos.rentacar.servicios.ReservationServices;
import com.retos.rentacar.servicios.StatusReservas;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/Reservation")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
        RequestMethod.DELETE })
public class ReservationWebRepository {
    @Autowired
    private ReservationServices servicios;

    @GetMapping("/report-status")
    public StatusReservas getReservas() {
        return servicios.reporteStatusServicio();
    }

    @GetMapping("/report-dates/{dateOne}/{dateTwo}")
    public List<Reservation> getReservasTiempo(@PathVariable("dateOne") String dateOne,
            @PathVariable("dateTwo") String dateTwo) {
        return servicios.reporteTiempoServicio(dateOne, dateTwo);
    }

    @GetMapping("/report-clients")
    public List<CountClients> getClientes() {
        return servicios.reporteClientesServicio();
    }


    
    @GetMapping("/all")
    public List<Reservation> getReservation() {
        return servicios.getAll();
    }

    @GetMapping("/{id}")
    public Optional<Reservation> getReservation(@PathVariable("id") int idReservation) {
        return servicios.getReservation(idReservation);
    }

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public Reservation save(@RequestBody Reservation reservation) {
        return servicios.save(reservation);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public boolean delete(@PathVariable("id") int idReservation) {
        return servicios.deleteReservation(idReservation);
    }

}
