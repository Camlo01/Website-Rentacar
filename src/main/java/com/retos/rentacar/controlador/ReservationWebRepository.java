
package com.retos.rentacar.controlador;

import com.retos.rentacar.modelo.DTO.ReservationDTO;
import com.retos.rentacar.modelo.DTO.Wrapper.ClientAndKeyClient;
import com.retos.rentacar.modelo.DTO.Wrapper.ReservationAndKeyClient;
import com.retos.rentacar.modelo.Entity.Client.KeyClient;
import com.retos.rentacar.modelo.Entity.Reservation.Reservation;
import com.retos.rentacar.servicios.ReservationServices;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservation")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
        RequestMethod.DELETE})
public class ReservationWebRepository {
    @Autowired
    private ReservationServices services;


    // --- Requests as CLIENT

    // - GET

    // Client

    @PostMapping("/my-active-reservations")
    public List<Reservation> getMyReservations(@RequestBody KeyClient key) {
        return services.getActiveReservationsOfAClient(key);
    }


    @PostMapping("/history")
    @ResponseStatus(HttpStatus.CREATED)
    public List<Reservation> getAllMyReservations(@RequestBody KeyClient key) {
        return services.getMyReservationHistory(key);
    }

    @PostMapping("/reserve-vehicle")
    public ResponseEntity<String> reserveVehicle(@RequestBody ReservationDTO reservationDTO) {
        if (services.createReservation(reservationDTO)) {
            return new ResponseEntity<>("Created Successfully", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @PostMapping("/when-is-bookable")
    public List<Reservation> datesBetweenYourReservation(@RequestBody ReservationDTO reservationDTO) {
        return services.datesBetweenYourReservation(reservationDTO);
    }


    // --- Requests as EMPLOYEES
    // --- Requests as EMPLOYEES


    //--EMPLOYEES

    // Admin-Developer
    @PostMapping("/all-reservations")
    public List<Reservation> getAllReservations(@RequestBody KeyClient key) {
        return services.getAllReservations(key);
    }

    // Support
    @PostMapping("/all-reservations-by-client")
    public List<Reservation> getAllReservationsByClient(@RequestBody ClientAndKeyClient body) {
        return services.getReservationsOfAClient(body.getClient(), body.getKeyClient());
    }


    @PostMapping("/all-between={minDate}/and={maxDate}")
    public List<Reservation> getReservationsBetweenOneAndTwo(@PathVariable("minDate") String minDate, @PathVariable("maxDate") String maxDate,
                                                             @RequestBody KeyClient key) {
        return services.reservationsBetweenDates(minDate, maxDate, key);
    }


    @PostMapping("/reservation-by-code")
    public Optional<Reservation> getReservationByCode(@RequestBody ReservationAndKeyClient body) {
        return services.getReservationByCode(body.getReservation(), body.getKey());
    }


    // reports


    //----------------------------------------------------------------

    // - POST

    // - PUT

    @PutMapping("/edit-reservation")
    public Reservation editReservation(@RequestBody ReservationAndKeyClient body) {
        return services.editReservation(body.getReservation(), body.getKey());
    }

    @PutMapping("/cancel-reservation")
    public ResponseEntity<String> cancelReservation(@RequestBody ReservationAndKeyClient body) {
        Boolean cancelledSuccessfully = services.cancelReservation(body.getReservation(), body.getKey());

        if (cancelledSuccessfully){
            return new ResponseEntity<>("response", HttpStatus.CREATED);
        }else {
            return new ResponseEntity<>("response", HttpStatus.METHOD_NOT_ALLOWED);
        }
    }
        
    // - DELETE


    //----------------------RESOURCES

//    @GetMapping("/report-dates/{dateOne}/{dateTwo}")
//    public List<Reservation> getReservasTiempo(@PathVariable("dateOne") String dateOne,
//                                               @PathVariable("dateTwo") String dateTwo) {
//        return services.reporteTiempoServicio(dateOne, dateTwo);
//    }
//
//    @GetMapping("/report-clients")
//    public List<CountClients> getClientes() {
//        return services.reporteClientesServicio();
//    }
//
//
//    @GetMapping("/all")
//    public List<Reservation> getReservation() {
//        return services.getAll();
//    }
//
//    @GetMapping("/{id}")
//    public Optional<Reservation> getReservation(@PathVariable("id") int idReservation) {
//        return services.getReservation(idReservation);
//    }
//
//    @PostMapping("/save")
//    @ResponseStatus(HttpStatus.CREATED)
//    public Reservation save(@RequestBody Reservation reservation) {
//        return services.save(reservation);
//    }
//
//    @DeleteMapping("/delete/{id}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public boolean delete(@PathVariable("id") int idReservation) {
//        return services.deleteReservation(idReservation);
//    }

}
