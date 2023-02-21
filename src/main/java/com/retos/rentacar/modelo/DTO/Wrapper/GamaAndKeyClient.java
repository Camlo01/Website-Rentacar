package com.retos.rentacar.modelo.DTO.Wrapper;

import com.retos.rentacar.modelo.Entity.Client.KeyClient;
import com.retos.rentacar.modelo.Entity.Gama.Gama;

public class GamaAndKeyClient {

    private Gama gama;
    private KeyClient key;

    public GamaAndKeyClient(Gama gama, KeyClient key) {
        this.gama = gama;
        this.key = key;
    }

    public Gama getGama() {
        return gama;
    }

    public void setGama(Gama gama) {
        this.gama = gama;
    }

    public KeyClient getKey() {
        return key;
    }

    public void setKey(KeyClient key) {
        this.key = key;
    }
}
