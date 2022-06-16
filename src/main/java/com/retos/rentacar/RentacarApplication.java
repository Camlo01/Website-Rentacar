package com.retos.rentacar;

import com.retos.rentacar.interfaces.CarInterface;
import com.retos.rentacar.interfaces.ClientInterface;
import com.retos.rentacar.interfaces.GamaInterface;
import com.retos.rentacar.modelo.Car;
import com.retos.rentacar.modelo.Client;
import com.retos.rentacar.modelo.Gama;
import com.retos.rentacar.repositorio.GamaRepository;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.security.auth.login.LoginException;
import java.util.Arrays;
import java.util.List;

@EntityScan(basePackages = {"com.retos.rentacar.modelo"})
@SpringBootApplication
public class RentacarApplication implements CommandLineRunner {

    private final Log Logger = LogFactory.getLog(RentacarApplication.class);

    @Autowired
    private GamaInterface gamaInterface;
    @Autowired
    private CarInterface carInterface;
    @Autowired
    private ClientInterface clientInterface;


    public static void main(String[] args) {
        SpringApplication.run(RentacarApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        createGamaVehicles();
        createVehicles();
        createClients();
    }

    public void createGamaVehicles() {
        Gama baja = new Gama("Gama baja", "Vehículos ligeros");
        Gama media = new Gama("Gama Media", "Vehículos compactos");
        Gama alta = new Gama("Gama Alta", "Vehículos pesados, deportivos o de lujo");
        Gama exclusiva = new Gama("Gama Exclusiva", "Vehículos que para reservar tendrás que expedir un permiso");
        Gama historicos = new Gama("Gama Historicos", "Vehículos de hace más de 10 años");
        Gama comun = new Gama("Gama Común", "Los vehículos más comunes que veras por la ciudad ");
        List<Gama> listOfGamas = Arrays.asList(baja, media, alta, exclusiva, historicos, comun);

        try {

            gamaInterface.saveAll(listOfGamas);
            Logger.info("Gamas added to H2 DataBase");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void createVehicles() {
        Car camionetaToyota = new Car("Land Cruiser", "Toyota", 2022, "Camioneta color negro");
        Car camionetaJeep = new Car("Gladiador Orverland", "Jeep", 2021, "Camioneta negra grande");
        Car camionetaMercedes = new Car("Mercedes-Benz Clase Gls 450", "Mercedes-Benz", 2022, "Camioneta Blanca");
        Car camionetaBMW = new Car("Bmx x7 Xdrive40i Pure", "BMW", 2022, "Camioneta con un hermoso azul");
        Car camionetaFord = new Car("Ford Bronco Outer Banks", "Ford", 2021, "Camioneta amarilla");
        Car carroChevrolet = new Car("Chevrolet Corvetter Stingray Z51", "Chevrolet", 2014, "Carro rojo");
        List<Car> listOfCars = Arrays.asList(camionetaToyota, camionetaJeep, camionetaMercedes, camionetaBMW, camionetaFord, carroChevrolet);
        try {
            carInterface.saveAll(listOfCars);
            Logger.info("Cars added to H2 DataBase");
        } catch (Exception e) {
            Logger.debug("Oops!: " + e);
        }

    }

    public void createClients() {
        Client milo = new Client("Camilo", "Camilo@gmail.com", "12345*", 18);
        Client matt = new Client("Mattew", "Mattew@gmail.com", "*54321", 22);
        Client vale = new Client("Valeria", "Valeria@gmail.com", "C0ntra?se(*a", 19);
        List<Client> listOfClients = Arrays.asList(milo, matt, vale);

        try {
            clientInterface.saveAll(listOfClients);
            Logger.info("Clients added to H2 DataBase");

        } catch (Exception e) {
            Logger.info("No fue posible crear el cliente: " + e);
        }
    }

}
