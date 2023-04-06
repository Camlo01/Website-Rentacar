package com.retos.rentacar.servicios;

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

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class GamaServicesTest {

    @Mock
    private GamaRepository repository;

    @InjectMocks
    private GamaServices service;

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

    @DisplayName("getGamaById() should not return null if exist")
    @Test
    public void getGamaById_ReturnClientIfExist() {
        int idOfGamaToFind = 1;

        Mockito.when(repository.getGamaById(idOfGamaToFind)).thenReturn(Optional.of(gama1));
        Assertions.assertNotEquals(Optional.empty(), service.getGamaById(idOfGamaToFind));
    }

    @DisplayName("getGamaById() should return null if does not exist")
    @Test
    public void getGamaById_ShouldReturnNullIfNotExist() {
        int idOfGamaToFind = 99;

        Mockito.when(repository.getGamaById(idOfGamaToFind)).thenReturn(Optional.empty());
        Assertions.assertEquals(Optional.empty(), service.getGamaById(idOfGamaToFind));
    }

    @DisplayName("saveGama() does not accept empty fields")
    @Test
    public void saveGama_notAcceptEmptyField() {
        Gama gamaCreated = new Gama("", "");
        Assertions.assertNull(service.saveGama(gamaCreated));
    }

    @DisplayName("saveGama() with a gama correctly created")
    @Test
    public void saveGama_correctlyCreated() {
        Gama gamaCreated = new Gama("large vehicles", "vehicles with a big capability");

        Mockito.when(repository.save(gamaCreated)).thenReturn(gamaCreated);

        Assertions.assertNotNull(service.saveGama(gamaCreated));
    }

    @DisplayName("updateGama() try update a gama that does not exist")
    @Test
    public void updateGama_doesNotExist() {
        Gama gamaUpdated = new Gama("new name", "new description");
        gamaUpdated.setId(99);

        Mockito.when(repository.getGamaById(gamaUpdated.getId())).thenReturn(Optional.empty());

        Assertions.assertNull(service.updateGama(gamaUpdated));
    }

    @DisplayName("updateGama() if theres no one change")
    @Test
    public void updateGama_theresNoOneChange() {
        Gama gamaNoChanges = new Gama("Gama baja", "vehículos ligeros");
        gamaNoChanges.setId(1);

        Mockito.when(repository.getGamaById(gamaNoChanges.getId())).thenReturn(Optional.of(gama1));

        Assertions.assertNull(service.updateGama(gamaNoChanges));
    }

    @DisplayName("updateGama() correctly updated")
    @Test
    public void updateGama_correctlyUpdated() {
        Gama gamaUpdated = new Gama("Gama baja", "vehículos ligeros que no cuentan con mucha potencia");
        gamaUpdated.setId(1);

        Mockito.when(repository.getGamaById(gamaUpdated.getId())).thenReturn(Optional.of(gama1));

        Mockito.when(repository.save(any(Gama.class))).thenReturn(gamaUpdated);

        Assertions.assertEquals(gamaUpdated, service.updateGama(gamaUpdated));
    }

    @DisplayName("deleteGama() that does not exist")
    @Test
    public void deleteGama_thatDoesNotExist() {
        Gama gamaToDelete = new Gama();
        gamaToDelete.setId(99);

        Mockito.when(repository.getGamaById(gamaToDelete.getId())).thenReturn(Optional.empty());

        Assertions.assertFalse(service.deleteGama(gamaToDelete));
    }

    @DisplayName("deleteGama() correctly deleted")
    @Test
    public void deleteGama_correctlyDeleted() {
        Gama gamaToDelete = new Gama();
        gamaToDelete.setId(1);

        Mockito.when(repository.getGamaById(gamaToDelete.getId())).thenReturn(Optional.of(gama1));

        Assertions.assertTrue(service.deleteGama(gamaToDelete));
    }


}