package com.retos.rentacar.modelo.DTO.Wrapper;

import com.retos.rentacar.modelo.DTO.DAO.ImageCarDTO;
import com.retos.rentacar.modelo.Entity.Car.ImageCar;
import com.retos.rentacar.modelo.Entity.Client.KeyClient;

public class ImageCarAndKeyClient {

    private ImageCarDTO image;
    private KeyClient key;

    public ImageCarAndKeyClient() {
    }

    public ImageCarAndKeyClient(ImageCarDTO image, KeyClient key) {
        this.image = image;
        this.key = key;
    }

    public ImageCarDTO getImage() {
        return image;
    }

    public void setImage(ImageCarDTO image) {
        this.image = image;
    }

    public KeyClient getKey() {
        return key;
    }

    public void setKey(KeyClient key) {
        this.key = key;
    }
}
