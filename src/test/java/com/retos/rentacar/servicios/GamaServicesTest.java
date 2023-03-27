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

    @DisplayName("getAll() should not be null")
    @Test
    public void testGetAll_ShouldNotReturnNull() {
        Mockito.when(gamaRepository.getAll()).thenReturn(listGama);

        Assertions.assertNotNull(gamaServices.getAll());
    }

    @DisplayName("getGamaById() should not be null if exist")
    @Test
    public void testGamaById_existingGama() {
        Mockito.when(gamaRepository.getGamaById(1))
                .thenReturn(Optional.of(gama1));

        Assertions.assertNotNull(gamaServices.getGamaById(1));
    }

    @DisplayName("saveGama() with a keyClient of a Client")
    @Test
    public void testSaveGama_AsClient() {
        KeyClient key = new KeyClient("CLIENT");

        Mockito.when(clientServices.hasPermissions(key, false))
                .thenReturn(false);

        Assertions.assertNull(gamaServices.saveGama(gama1, key));
    }

    @DisplayName("saveGama() with a keyClient of a Admin")
    @Test
    public void testSaveGama_AsAdmin() {
        KeyClient key = new KeyClient("ADMIN");

        Mockito.when(clientServices.hasPermissions(key, false))
                .thenReturn(true);

        Mockito.when(gamaRepository.save(gama1))
                .thenReturn(gama1);

        Assertions.assertNotNull(gamaServices.saveGama(gama1, key));
    }

    @DisplayName("saveGama() with a keyClient of a Developer")
    @Test
    public void testSaveGama_AsDeveloper() {
        KeyClient key = new KeyClient("DEVELOPER");

        Mockito.when(clientServices.hasPermissions(key, false))
                .thenReturn(true);

        Mockito.when(gamaRepository.save(gama1))
                .thenReturn(gama1);

        Assertions.assertNotNull(gamaServices.saveGama(gama1, key));
    }

    @DisplayName("saveGama() with a keyClient of a Support")
    @Test
    public void testSaveGama_AsSupport() {
        KeyClient key = new KeyClient("DEVELOPER");

        Mockito.when(clientServices.hasPermissions(key, false))
                .thenReturn(false);

        Assertions.assertNull(gamaServices.saveGama(gama1, key));
    }

    @DisplayName("saveGama() with no valid keyClient (doesn't exist)")
    @Test
    public void testSaveGama_WidthNoValidKeyClient() {
        KeyClient key = new KeyClient("NO_VALID");

        Mockito.when(clientServices.hasPermissions(key, false))
                .thenReturn(false);

        Assertions.assertNull(gamaServices.saveGama(gama1, key));
    }

    @DisplayName("updateGama() with KeyClient of a Admin")
    @Test
    public void testUpdateGama_AsAdmin() {
        KeyClient key = new KeyClient("ADMIN");

        Gama gamaInDB = new Gama("Old gama", "Old description");
        gamaInDB.setId(99);
        Gama newGama = new Gama("Gama updated", "New description");
        newGama.setId(99);

        Mockito.when(clientServices.hasPermissions(key, false))
                .thenReturn(true);

        Mockito.when(gamaRepository.getGamaById(newGama.getId()))
                .thenReturn(Optional.of(gamaInDB));

        Mockito.when(gamaRepository.save(gamaInDB))
                .thenReturn(newGama);

        Assertions.assertNotNull(gamaServices.updateGama(newGama, key));
    }

    @DisplayName("updateGama() with KeyClient of a Developer")
    @Test
    public void testUpdateGama_AsDeveloper() {
        KeyClient key = new KeyClient("DEVELOPER");

        Gama gamaInDB = new Gama("Old gama", "Old description");
        gamaInDB.setId(99);
        Gama newGama = new Gama("Gama updated", "New description");
        newGama.setId(99);

        Mockito.when(clientServices.hasPermissions(key, false))
                .thenReturn(true);

        Mockito.when(gamaRepository.getGamaById(newGama.getId()))
                .thenReturn(Optional.of(gamaInDB));

        Mockito.when(gamaRepository.save(gamaInDB))
                .thenReturn(newGama);

        Assertions.assertNotNull(gamaServices.updateGama(newGama, key));
    }

    @DisplayName("updateGama() with KeyClient of a Support")
    @Test
    public void testUpdateGama_AsSupport() {
        KeyClient key = new KeyClient("DEVELOPER");

        Gama newGama = new Gama("Gama updated", "New description");

        Mockito.when(clientServices.hasPermissions(key, false))
                .thenReturn(false);

        Assertions.assertNull(gamaServices.updateGama(newGama, key));
    }

    @DisplayName("updateGama() with KeyClient of a Client")
    @Test
    public void testUpdateGama_AsClient() {
        KeyClient key = new KeyClient("CLIENT");

        Gama newGama = new Gama("Gama updated", "New description");

        Mockito.when(clientServices.hasPermissions(key, false))
                .thenReturn(false);

        Assertions.assertNull(gamaServices.updateGama(newGama, key));
    }

    @DisplayName("deleteGama() with KeyClient of a Admin")
    @Test
    public void testDeleteGama_AsAdmin() {
        KeyClient key = new KeyClient("ADMIN");

        Gama gamaToDelete = new Gama("To delete", "description");
        gamaToDelete.setId(1);

        Mockito.when(clientServices.hasPermissions(key, false))
                .thenReturn(true);

        Mockito.when(gamaRepository.getGamaById(gamaToDelete.getId()))
                .thenReturn(Optional.of(gamaToDelete));
        Assertions.assertTrue(gamaServices.deleteGama(gamaToDelete, key));
    }

    @DisplayName("deleteGama() with KeyClient of a Developer")
    @Test
    public void testDeleteGama_AsDeveloper() {
        KeyClient key = new KeyClient("DEVELOPER");

        Gama gamaToDelete = new Gama("To delete", "description");
        gamaToDelete.setId(1);

        Mockito.when(clientServices.hasPermissions(key, false))
                .thenReturn(true);

        Mockito.when(gamaRepository.getGamaById(gamaToDelete.getId()))
                .thenReturn(Optional.of(gamaToDelete));

        Assertions.assertTrue(gamaServices.deleteGama(gamaToDelete, key));
    }

    @DisplayName("deleteGama() with KeyClient of a Support")
    @Test
    public void testDeleteGama_AsSupport() {
        KeyClient key = new KeyClient("SUPPORT");

        Gama gamaToDelete = new Gama("To delete", "description");
        gamaToDelete.setId(1);

        Mockito.when(clientServices.hasPermissions(key, false))
                .thenReturn(false);

        Assertions.assertFalse(gamaServices.deleteGama(gamaToDelete, key));
    }

    @DisplayName("deleteGama() with KeyClient of a Client")
    @Test
    public void testDeleteGama_AsClient() {
        KeyClient key = new KeyClient("CLIENT");

        Gama gamaToDelete = new Gama("To delete", "description");

        Mockito.when(clientServices.hasPermissions(key, false))
                .thenReturn(false);

        Assertions.assertFalse(gamaServices.deleteGama(gamaToDelete, key));
    }

}