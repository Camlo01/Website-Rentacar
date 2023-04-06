package com.retos.rentacar.servicios;

import com.retos.rentacar.interfaces.CarInterface;
import com.retos.rentacar.modelo.DTO.DAO.ImageCarDTO;
import com.retos.rentacar.modelo.Entity.Car.Car;
import com.retos.rentacar.modelo.Entity.Car.ImageCar;
import com.retos.rentacar.repositorio.ImageCarRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ImageCarServicesTest {

    @Mock
    private ImageCarRepository repository;
    @Mock
    private CarInterface carInterface;

    @InjectMocks
    private ImageCarServices service;

    private ImageCar img1, img2, img3;

    private List<ImageCar> images;

    @BeforeEach
    public void setUp() {

        img1 = new ImageCar(1, "www.image1.com");
        img2 = new ImageCar(2, "www.image2.com");
        img3 = new ImageCar(3, "www.image3.com");


        images = new ArrayList<>();
        images.add(img1);
        images.add(img2);
        images.add(img3);
    }

    @DisplayName("getImageCarById() of a image that exist")
    @Test
    public void getImageCarById_ThatExist() {
        int idOfImageToFind = 1;

        Mockito.when(repository.getImageCarById(idOfImageToFind)).thenReturn(Optional.of(img1));

        Assertions.assertNotEquals(Optional.empty(), service.getImageCarById(idOfImageToFind));
    }

    @DisplayName("getImageCarId() of a image that does not exist")
    @Test
    public void getImageCarId_ThatDoesNotExist() {
        int idOfImageToFind = 99;

        Mockito.when(repository.getImageCarById(idOfImageToFind)).thenReturn(Optional.empty());

        Assertions.assertEquals(Optional.empty(), service.getImageCarById(idOfImageToFind));
    }

    @DisplayName("getImagesOfCar() of a car that exist")
    @Test
    public void getImagesOfCar_thatExist() {
        int idOfCarFromWhichGetImages = 1;

        Mockito.when(repository.getImagesOfCar(idOfCarFromWhichGetImages)).thenReturn(images);

        Assertions.assertEquals(3, service.getImagesOfCar(idOfCarFromWhichGetImages).size());
    }

    @DisplayName("getImagesOfCar() car that does not exist")
    @Test
    public void getImagesOfCar_ThatDoesNotExist() {
        int idOfCarFromWhichGetImages = 99;

        Mockito.when(repository.getImagesOfCar(idOfCarFromWhichGetImages)).thenReturn(new ArrayList<>());

        Assertions.assertEquals(0, service.getImagesOfCar(idOfCarFromWhichGetImages).size());
    }

    @DisplayName("saveImageCar() not allows empty field")
    @Test
    public void saveImageCar_notEmptyField() {
        ImageCarDTO imgToCreate = new ImageCarDTO();
        imgToCreate.setIdCar(1);
        imgToCreate.setUrl("www.imagecar.com");

        Mockito.when(carInterface.findById(imgToCreate.getIdCar())).thenReturn(Optional.of(new Car()));

        Assertions.assertNull(service.saveImageCar(imgToCreate));
    }

    @DisplayName("saveImageCar() correctly created")
    @Test
    public void saveImageCar_correctlyCreated() {
        ImageCarDTO imgToCreate = new ImageCarDTO();
        imgToCreate.setIdCar(1);
        imgToCreate.setUrl("www.imageOfCar.com");

        Mockito.when(carInterface.findById(imgToCreate.getIdCar())).thenReturn(Optional.of(new Car()));

        Mockito.when(repository.save(ArgumentMatchers.any(ImageCar.class))).thenReturn(ArgumentMatchers.any(ImageCar.class));

        Assertions.assertNull(service.saveImageCar(imgToCreate));
    }

    @DisplayName("deleteImageCar() that does not exist")
    @Test
    public void deleteImageCar_thatDoesNotExist() {
        ImageCarDTO imgToDelete = new ImageCarDTO();
        imgToDelete.setId(99);

        Mockito.when(repository.getImageCarById(imgToDelete.getId())).thenReturn(Optional.empty());

        Assertions.assertFalse(service.deleteImageCar(imgToDelete));
    }

    @DisplayName("deleteImageCar() that exist")
    @Test
    public void deleteImageCar_thatExist() {
        ImageCarDTO imgToDelete = new ImageCarDTO();
        imgToDelete.setId(1);

        Mockito.when(repository.getImageCarById(imgToDelete.getId()))
                .thenReturn(Optional.of(new ImageCar()));

        Assertions.assertTrue(service.deleteImageCar(imgToDelete));
    }

}