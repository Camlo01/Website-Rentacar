package com.retos.rentacar.servicios;

import com.retos.rentacar.modelo.DTO.DAO.CarDTO;
import com.retos.rentacar.modelo.Entity.Car.Car;
import com.retos.rentacar.modelo.Entity.Car.CarStatus;
import com.retos.rentacar.modelo.Entity.Gama.Gama;
import com.retos.rentacar.repositorio.CarRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class CarServicesTest {

    @Mock
    private CarRepository reposity;

    @Mock
    private ClientServices clientServices;

    @InjectMocks
    private CarServices service;

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
        car5.setGama(new Gama("name", "description"));
        car5.setId(5);

        car6 = new Car("Porsche", "Porsche", 2023, "carro de prueba 6");
        car6.setId(6);
        listOfCars = Arrays.asList(car1, car2, car3, car4, car5, car6);
    }

    @DisplayName("getCarById() should not be null with that exist")
    @Test
    public void getById_shouldNotBeNull() {
        int idOfCarToFind = 1;

        Mockito.when(reposity.getCarById(idOfCarToFind))
                .thenReturn(Optional.of(car1));

        Assertions.assertTrue(service.getCarById(idOfCarToFind).isPresent());
    }

    @DisplayName("getCarById() should be null with a car that does not exist")
    @Test
    public void getById_ShouldBeNullIfDoesNotExist() {
        int idOfCarToFind = 999;

        Mockito.when(reposity.getCarById(idOfCarToFind))
                .thenReturn(Optional.empty());

        Assertions.assertTrue(service.getCarById(idOfCarToFind).isEmpty());
    }

    @DisplayName("getLastCarAddedBookable() should not be null")
    @Test
    public void getLastCarAddedBookable_shouldNotBeNull() {

        Mockito.when(reposity.getLastCarAdded())
                .thenReturn(Optional.of(car5));

        Assertions.assertNotNull(service.getLastCarAdded().get());

    }

    @DisplayName("getLastCarAdded() should not be null")
    @Test
    public void getLastCarAdded_ShouldNotBeNull() {

        Mockito.when(reposity.getLastCarAdded())
                .thenReturn(Optional.of(car5));

        Assertions.assertNotNull(service.getLastCarAdded().get());
    }

    @DisplayName("saveVehicle() return the same vehicle")
    @Test
    public void saveVehicle() {

        Mockito.when(reposity.save(car2))
                .thenReturn(car2);

        Assertions.assertEquals(car2, service.saveVehicle(car2));
    }

    @DisplayName("updateVehicle() should not be null with a correct car to update")
    @Test
    public void updateVehicle_ShouldBeNotNull() {

//        Consult the car in the DB
        Mockito.when(reposity.getCarById(car5.getId()))
                .thenReturn(Optional.of(car5));

        Mockito.when(reposity.save(any(Car.class)))
                .thenReturn(car5);

        Assertions.assertNotNull(service.updateVehicle(car5));
    }

    @DisplayName("updateVehicle() should return null if the car does not exist")
    @Test
    public void updateVehicle_CarThatDoesNotExist() {

        Mockito.when(reposity.getCarById(car5.getId()))
                .thenReturn(Optional.empty());

        Assertions.assertNull(service.updateVehicle(car5));
    }

    @DisplayName("deleteVehicle() car that exist")
    @Test
    public void deleteVehicle_CarThatExist() {

        CarDTO carToDelete = new CarDTO(car1);

        Mockito.when(reposity.getCarById(carToDelete.getId()))
                .thenReturn(Optional.of(car1));

        Mockito.doNothing().when(reposity).delete(any(Car.class));

        Assertions.assertTrue(service.deleteVehicle(carToDelete));
    }

    @DisplayName("deleteVehicle() car that does not exist")
    @Test
    public void deleteVehicle_CarDoesNotExist() {

        CarDTO carToDelete = new CarDTO(car2);

        Mockito.when(reposity.getCarById(carToDelete.getId())).thenReturn(Optional.empty());

        Assertions.assertFalse(service.deleteVehicle(carToDelete));
    }
}