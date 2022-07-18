package com.retos.rentacar.servicios;

import com.retos.rentacar.modelo.Entity.Car.Car;
import com.retos.rentacar.repositorio.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CarServicesTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarServices carServices;
    private Car car;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        car = new Car("Land Cruiser", "Toyota", 2022, "Camioneta color negro");
    }

    @Test
    void get_all_the_list_of_cars() {
        when(carRepository.getAll()).thenReturn(Arrays.asList(car));
        assertNotNull(carRepository.getAll());
    }

    @Test
    void  get_first_car_by_id()  {
        Optional<Car> carToCompare = Optional.of(car);
        when(carRepository.getCar(1)).thenReturn(Optional.of((new Car("Land Cruiser", "Toyota", 2022, "Camioneta color negro"))));
        assertEquals(carToCompare.get().getName(),  carServices.getCar(1  ).get().getName());
    }

    @Test
    void save_2_cars_and_when_call_all_cars_return_3_cars() {
    }

    @Test
    // get_the_last_car_added
    void get_the_last_car_added() {
    }

    @Test
    void update() {
    }

    @Test
    void deleteCar() {
    }
}