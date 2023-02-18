package com.retos.rentacar.servicios;

import com.retos.rentacar.modelo.DTO.DAO.CarDTO;
import com.retos.rentacar.modelo.Entity.Car.Car;
import com.retos.rentacar.modelo.Entity.Car.CarStatus;
import com.retos.rentacar.modelo.Entity.Client.KeyClient;
import com.retos.rentacar.repositorio.CarRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CarServicesTest {

    @Mock
    private CarRepository carRepository;

    @Mock
    private ClientServices clientServices;

    @InjectMocks
    private CarServices carServices;

    private Car car1, car2, car3, car4, car5, car6;

    private List listOfCars;

    @BeforeEach
    public void setUp() {

        car1 = new Car("Ferrari", "Ferrari", 2020, "carro de prueba 1");
        car1.setId(1);

        car2 = new Car("Audi", "Audi", 2021, "carro de prueba 2");
        car2.setId(2);

        car3 = new Car("Ferrari", "Ferrari", 20222, "carro de prueba 3");
        car3.setId(3);

        car4 = new Car("BMW", "BMW", 2023, "carro de prueba 4");
        car4.setId(4);

        car5 = new Car("Mercedes", "Mercedes", 2023, "carro de prueba 5");
        car5.setCarStatus(CarStatus.BOOKABLE);
        car5.setId(5);

        car6 = new Car("Porsche", "Porsche", 2023, "carro de prueba 6");
        car6.setId(6);
        listOfCars = Arrays.asList(car1, car2, car3, car4, car5, car6);
    }

    @DisplayName("getAll() return list of cars")
    @Test
    public void testGetAll_ShouldNotReturnNull() {
        Mockito.when(carRepository.getAll()).thenReturn(listOfCars);
        Assertions.assertNotNull(carServices.getAll());
    }

    @DisplayName("getCar() return car by its id")
    @Test
    public void testGetCar_ShouldReturnFifthCar() {
        Mockito.when(carRepository.getCar(5)).thenReturn(Optional.of(car5));
        Optional<Car> car5 = carRepository.getCar(5);
        Assertions.assertEquals(5, car5.get().getId());
    }

    @DisplayName("saveVehicle() with a keyClient of a Client")
    @Test
    public void testSaveVehicle_AsClient() {
        KeyClient keyOfClient = new KeyClient("CLIENT");
        Mockito.when(clientServices.hasPermissions(keyOfClient, false))
                .thenReturn(false);
        Assertions.assertNull(carServices.saveVehicle(new CarDTO(), keyOfClient));
    }

    @DisplayName("Update() with keyClient that doesn't exist")
    @Test
    public void testUpdateVehicle_withNonexistentKey() {
        CarDTO carToSave = new CarDTO();
        carToSave.setId(1);
        KeyClient key = new KeyClient("DOESNTEXIST");

        Mockito.when(clientServices.hasPermissions(key, true)).thenReturn(false);
        Assertions.assertNull(carServices.updateVehicle(carToSave, key));
    }

    @DisplayName("DeleteCar() with keyClient of a Client")
    @Test
    public void testDeleteVehicle_asClient() {
        CarDTO carToDelete = new CarDTO();
        carToDelete.setId(5);
        KeyClient key = new KeyClient("CLIENT");

        Mockito.when(clientServices.hasPermissions(key, false)).thenReturn(false);

        Assertions.assertEquals(false, carServices.deleteVehicle(carToDelete, key));
    }

}