package com.retos.rentacar.servicios;

import com.retos.rentacar.modelo.Entity.Client.KeyClient;
import com.retos.rentacar.modelo.Entity.Gama.Gama;
import com.retos.rentacar.repositorio.GamaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class GamaServicesTest {

    @Mock
    private GamaRepository gamaRepository;

    @Mock
    private ClientServices clientServices;

    @InjectMocks
    private GamaServices gamaServices;

    private Gama gama1, gama2, gama3;

    private List<Gama> listGama = new ArrayList<>();

    @BeforeEach
    public void setUp() {

        gama1 = new Gama("Gama baja", "vehículos ligeros");
        gama1.setId(1);

        gama2 = new Gama("Gama media", "vehículos medianos");
        gama2.setId(2);

        gama3 = new Gama("Gama alta", "vehículos costosos");
        gama3.setId(3);

        listGama.add(gama1);
        listGama.add(gama2);
        listGama.add(gama3);

    }

}