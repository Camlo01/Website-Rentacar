package com.retos.rentacar.servicios;

import com.retos.rentacar.repositorio.ImageCarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ImageCarServicesTest {

    @Mock
    private ImageCarRepository repository;
    @Mock
    private ClientServices clientServices;

    @InjectMocks
    private ImageCarServices imageCarServices;

    @BeforeEach
    private void setUp() {
        System.out.println("null");
    }

    @Test
    private void emptyTest() {

    }
}