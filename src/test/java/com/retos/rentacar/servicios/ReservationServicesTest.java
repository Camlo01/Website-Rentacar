package com.retos.rentacar.servicios;

import com.retos.rentacar.modelo.DTO.ReservationDTO;
import com.retos.rentacar.modelo.Entity.Reservation.Reservation;
import com.retos.rentacar.modelo.Entity.Reservation.ReservationStatus;
import com.retos.rentacar.repositorio.ReservationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
class ReservationServicesTest {

    @Mock
    private ReservationRepository repository;
    @InjectMocks
    private ReservationServices services;

    @BeforeEach
    public void setUp() {
    }

    @DisplayName("getReservationById() should return null if does not exist")
    @Test
    public void getReservationById_thatDoesNotExist() {
        int idOfReservationToFind = 1;

        Mockito.when(repository.getReservationById(idOfReservationToFind))
                .thenReturn(Optional.empty());

        Assertions.assertNull(services.getReservationById(idOfReservationToFind));
    }

    @DisplayName("getReservationById() with existing reservation ")
    @Test
    public void getReservationById_thatExist() {
        int idOfReservationToFind = 1;

        Mockito.when(repository.getReservationById(idOfReservationToFind))
                .thenReturn(Optional.of(new Reservation()));

        Assertions.assertNotNull(services.getReservationById(idOfReservationToFind));
    }

    @DisplayName("getReservationByCode() should return null if does not exist")
    @Test
    public void getReservationByCode_thatDoesNotExist() {
        String reservationCode = "RANDOM_CODE";

        Mockito.when(repository.getReservationByCode(reservationCode))
                .thenReturn(Optional.empty());

        Assertions.assertNull(services.getReservationByCode(reservationCode));
    }

    @DisplayName("getReservationByCode() that exist")
    @Test
    public void getReservationByCode_thatExist() {
        String reservationCode = "RANDOM_CODE";
        Reservation reservationInDB = new Reservation(reservationCode);

        Mockito.when(repository.getReservationByCode(reservationCode))
                .thenReturn(Optional.of(reservationInDB));

        Assertions.assertNotNull(services.getReservationByCode(reservationCode));
    }

    @DisplayName("createReservation() return the reservation when is correctly created and car available")
    @Test
    public void createReservation_correctlyCreated() {
        ReservationDTO reservationCreated = new ReservationDTO("2023-01-01", "2023-01-10", 1, 1);

        Mockito.when(repository.isAvailableToReserve(eq(reservationCreated.getCar_id()), any(Date.class), any(Date.class)))
                .thenReturn(true);

        Mockito.when(repository.getReservationByCode(any(String.class)))
                .thenReturn(Optional.empty());

        Assertions.assertTrue(services.createReservation(reservationCreated));
    }

    @DisplayName("createReservation() If the car is not available for this date")
    @Test
    public void createReservation_carNotAvailable() {
        ReservationDTO reservationCreated = new ReservationDTO("2023-01-01", "2023-01-10", 2, 1);

        Mockito.when(repository.isAvailableToReserve(eq(reservationCreated.getCar_id()), any(Date.class), any(Date.class)))
                .thenReturn(false);

        Assertions.assertFalse(services.createReservation(reservationCreated));
    }

    @DisplayName("cancelReservation() correctly cancelled")
    @Test
    public void cancelReservation_correctlyCancelled() {
        Reservation reservationCancelled = new Reservation();
        reservationCancelled.setReservationStatus(ReservationStatus.CANCELLED);

        String codeOfReservationToCancel = "RANDOM_CODE";
        Mockito.doNothing().when(repository).changeStatusReservation("CANCELLED", any(String.class));

        Mockito.when(repository.getReservationByCode(any(String.class))).thenReturn(Optional.of(reservationCancelled));

        Assertions.assertTrue(services.cancelReservation(codeOfReservationToCancel));
    }

    @DisplayName("cancelReservation() that does not exist")
    @Test
    public void cancelReservation_ThatDoesNotExist() {
        Reservation reservationCancelled = new Reservation();
        reservationCancelled.setReservationStatus(ReservationStatus.CANCELLED);

        String codeOfReservationToCancel = "NONEXISTENT_CODE";
        Mockito.doNothing().when(repository).changeStatusReservation("CANCELLED", any(String.class));

        Mockito.when(repository.getReservationByCode(any(String.class))).thenReturn(Optional.of(reservationCancelled));

        Assertions.assertTrue(services.cancelReservation(codeOfReservationToCancel));
    }

}