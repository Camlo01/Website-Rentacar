package com.retos.rentacar.servicios;

import com.retos.rentacar.repositorio.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReservationServicesTest {

    @Mock
    private ReservationRepository repository;
    @InjectMocks
    private ReservationServices services;

    @BeforeEach
    public void setUp() {
    }

    @DisplayName("tests de Reservation")
    @Test
    public void test() {
    }

}