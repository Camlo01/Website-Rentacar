
package com.retos.rentacar.controlador;

import com.retos.rentacar.modelo.DTO.ReservationDTO;
import com.retos.rentacar.modelo.DTO.Wrapper.ReservationAndKeyClient;
import com.retos.rentacar.modelo.Entity.Client.KeyClient;
import com.retos.rentacar.modelo.Entity.Reservation.Reservation;
import com.retos.rentacar.servicios.ClientServices;
import com.retos.rentacar.servicios.ReservationServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservation")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
        RequestMethod.DELETE})
public class ReservationWebRepository {

    @Autowired
    private ReservationServices service;

    @PostMapping("/id/{id}")
    public ResponseEntity<?> getReservationById(@PathVariable("id") int id, @RequestBody KeyClient whoRequest) {

        if (hasPermissions(whoRequest)) {
            Reservation reservationFound = service.getReservationById(id);

            if (reservationFound.getId() == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else return new ResponseEntity<>(reservationFound, HttpStatus.OK);

        } else return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<?> getReservationByCode(@PathVariable String code) {

        Reservation reservationFound = service.getReservationByCode(code);
        if (reservationFound.getCode() == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else return new ResponseEntity<>(reservationFound, HttpStatus.OK);
    }

    @PostMapping("/by-client/{email}")
    public ResponseEntity<?> getReservationsOfClient(@PathVariable("email") String email, @RequestBody KeyClient whoRequest) {
        if (hasPermissionsIncludeSupport(whoRequest)) {
            List<Reservation> listOfReservations = service.getReservationsOfClient(email);
            return new ResponseEntity<>(listOfReservations, HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    }

    @PostMapping("/by-client/{email}/{status}")
    public ResponseEntity<?> getReservationsOfClientByStatus(@PathVariable("email") String email,
                                                             @PathVariable("status") String status,
                                                             @RequestBody KeyClient whoRequest) {
        if (hasPermissionsIncludeSupport(whoRequest)) {
            List<Reservation> listOfReservations = service.getReservationsOfClientByStatus(email, status);
            return new ResponseEntity<>(listOfReservations, HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/by-client/{email}/between/{start}/{end}")
    public ResponseEntity<?> getReservationsOfClientBetweenDates(@RequestBody KeyClient whoRequest,
                                                                 @PathVariable("email") String email,
                                                                 @PathVariable("start") String start,
                                                                 @PathVariable("end") String end) {
        if (hasPermissionsIncludeSupport(whoRequest)) {
            List<Reservation> listOfReservations = service.getReservationsOfClientBetweenDates(email, start, end);
            return new ResponseEntity<>(listOfReservations, HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    }

    @PostMapping("/create")
    public ResponseEntity<?> createReservation(@RequestBody ReservationAndKeyClient body) {

        boolean hasPermission = (hasPermissions(body.getKey()) || isReservationOwner(body.getReservation(), body.getKey()));
        if (hasPermission) {

            boolean wasSuccessfully = service.createReservation(body.getReservation());
            if (wasSuccessfully) {
                return new ResponseEntity<>(HttpStatus.CREATED);
            } else {
                return
                        new ResponseEntity<>("The car could not be created", HttpStatus.BAD_REQUEST);
            }
        } else
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/cancel/{code}")
    public ResponseEntity<?> cancelReservation(@PathVariable("code") String code,
                                               @RequestBody KeyClient key) {

        boolean hasPermission = (hasPermissions(key) || isReservationOwner(code, key));

        boolean wasSuccessfully = service.cancelReservation(key, code);
        if (hasPermission) {
            if (wasSuccessfully) {
                return new ResponseEntity<>(HttpStatus.CREATED);
            } else
                return new ResponseEntity<>("The reservation could not be cancelled", HttpStatus.BAD_REQUEST);
        } else
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    /**
     * Admin requests
     */

    @PostMapping("/reservations/between/{start}/{end}")
    public ResponseEntity<?> getReservationsBetweenDates(@RequestBody KeyClient key,
                                                         @PathVariable("start") String start,
                                                         @PathVariable("end") String end) {
        if (hasPermissionsIncludeSupport(key)) {
            List<Reservation> listReservations = service.getReservationsBetweenDates(start, end);
            if (listReservations.size() > 0) {
                return new ResponseEntity<>(listReservations, HttpStatus.OK);
            } else return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/reservations/between/{start}/{end}/status/{status}")
    public ResponseEntity<?> getReservationByStatusBetweenDates(@RequestBody KeyClient key,
                                                                @PathVariable("status") String status,
                                                                @PathVariable("start") String start,
                                                                @PathVariable("end") String end) {
        if (hasPermissionsIncludeSupport(key)) {
            List<Reservation> listReservations = service.getReservationByStatusBetweenDates(status, start, end);
            if (listReservations.size() > 0) {
                return new ResponseEntity<>(listReservations, HttpStatus.OK);
            } else return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

//    util methods

    /**
     * Method in charge to evaluate the permissions of a client by its keyClient
     *
     * @param key to evaluate
     * @return boolean value
     */
    private boolean hasPermissions(KeyClient key) {
        return service.hasPermissions(key);
    }

    /**
     * Method in charge to evaluate the permissions of a client by its keyClient including support
     *
     * @param key to evaluate
     * @return boolean value
     */
    private boolean hasPermissionsIncludeSupport(KeyClient key) {
        return service.hasPermissionsIncludeSupport(key);
    }

    /**
     * Method in charge to evaluate if the key correspond to who reserve the vehicle
     *
     * @param reservationDTO to evaluate
     * @param key            to evaluate
     * @return boolean value
     */
    private boolean isReservationOwner(ReservationDTO reservationDTO, KeyClient key) {
        return service.isReservationOwner(reservationDTO, key);
    }

    /**
     * Method in charge to evaluate if the key correspond to who reserve the vehicle
     *
     * @param code of the reservation to evaluate
     * @param key  to evaluate
     * @return boolean value
     */
    private boolean isReservationOwner(String code, KeyClient key) {
        return service.isReservationOwner(code, key);
    }

}
