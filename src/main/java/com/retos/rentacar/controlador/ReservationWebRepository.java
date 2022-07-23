
package com.retos.rentacar.controlador;

import com.retos.rentacar.modelo.DTO.ReservationAndKeyclient;
import com.retos.rentacar.modelo.Entity.Reservation.Reservation;
import com.retos.rentacar.repositorio.CountClients;
import com.retos.rentacar.servicios.ReservationServices;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/Reservation")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
        RequestMethod.DELETE})
public class ReservationWebRepository {
    @Autowired
    private ReservationServices services;


    // --- Peticiones HTTP Fijas

    // - GET

    // Client
    @GetMapping("/my-reservations")
    public List<Reservation> getMyReservations(@RequestBody Reservation body) {
        return null;
    }


    //--EMPLOYEES

    // Admin-Developer
    @GetMapping("/all-reservations")
    public List<Reservation> getAllReservations() {
        return null;
    }


    // ONLY FOR TEST
    @GetMapping("/reservation-by-id")
    public Optional<Reservation> getReservationById(@RequestBody ReservationAndKeyclient body) {
        return services.getReservationById(body);

    }

    //Support
    @GetMapping("/reservation-by-client")
    public Optional<Reservation> getReservationsByClient(@RequestBody Reservation body) {

        return Optional.empty();
    }

    // reports


    // - POST

    // client
    @PostMapping("/reserve-vehicle")
    @ResponseStatus(HttpStatus.CREATED)
    public Reservation reserveVehicle() {
        return null;
    }

    // - PUT

    @PutMapping("/edit-reservation")
    public Reservation editReservation() {
        return null;
    }

    @PutMapping("/cancel-reservation")
    public Reservation cancelReservation(@RequestBody ReservationAndKeyclient body) {
        return services.cancelReservation(body.getReservation(), body.getKeyClient());
    }

    // - DELETE


    // RESOURCES

    @GetMapping("/report-dates/{dateOne}/{dateTwo}")
    public List<Reservation> getReservasTiempo(@PathVariable("dateOne") String dateOne,
                                               @PathVariable("dateTwo") String dateTwo) {
        return services.reporteTiempoServicio(dateOne, dateTwo);
    }

    @GetMapping("/report-clients")
    public List<CountClients> getClientes() {
        return services.reporteClientesServicio();
    }


    @GetMapping("/all")
    public List<Reservation> getReservation() {
        return services.getAll();
    }

    @GetMapping("/{id}")
    public Optional<Reservation> getReservation(@PathVariable("id") int idReservation) {
        return services.getReservation(idReservation);
    }

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public Reservation save(@RequestBody Reservation reservation) {
        return services.save(reservation);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public boolean delete(@PathVariable("id") int idReservation) {
        return services.deleteReservation(idReservation);
    }

}
