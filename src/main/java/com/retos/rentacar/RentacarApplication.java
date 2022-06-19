package com.retos.rentacar;

import com.retos.rentacar.interfaces.*;
import com.retos.rentacar.modelo.*;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.sql.Date;
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
    @Autowired
    private MessageInterface messageInterface;
    @Autowired
    private ReservationInterface reservationInterface;

    public static void main(String[] args) {
        SpringApplication.run(RentacarApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        createGamaVehicles();
        createEveryObject();
    }

    public void createEveryObject() {
        createVehicles();
        createClients();
        createMessage();
        createReservation();
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
        List<Car> listOfCars = Arrays.asList(camionetaToyota, camionetaJeep, camionetaMercedes, camionetaBMW,
                camionetaFord, carroChevrolet);
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

    public void createMessage() {

        Message mensaje = new Message("Este carro es muy bonito");
        Message mensaje1 = new Message("El carro es dificil de conducir");
        Message mensaje2 = new Message("Este vehículo consume mucha gasolina");
        Message mensaje3 = new Message("El color blanco se ensucia muy fácil");
        Message mensaje4 = new Message("La camioneta es muy ancha");
        Message mensaje5 = new Message("El carro me lo entregaron sucio");
        Message mensaje6 = new Message("Me gustaría poder alquilar motos");
        Message mensaje7 = new Message("No tienen este carro disponible en otra ciudad?");
        Message mensaje8 = new Message("El carro estaba mal de los frenos");
        Message mensaje9 = new Message("No recomiendo para nada este servicio");
        Message mensaje10 = new Message("siempre los uso cuando estoy de vacaciones");
        Message mensaje11 = new Message("Deberían tener más carros eléctricos");
        Message mensaje12 = new Message("Este carro tiene muy mal motor");
        Message mensaje13 = new Message("Hace falta de mantenimiento a los carros");
        Message mensaje14 = new Message("no tienen del color que me gusta");

        List<Message> list = Arrays.asList(mensaje, mensaje1, mensaje2, mensaje3, mensaje4, mensaje5, mensaje6,
                mensaje7, mensaje8, mensaje9, mensaje10, mensaje11, mensaje12, mensaje13, mensaje14);

        try {
            messageInterface.saveAll(list);
            Logger.info("Messages added to H2 DataBase");

        } catch (Exception e) {
            Logger.info("No fue posible crear los mensajes: " + e);
        }

    }

    public void createReservation() {

        Date fechaInico = Date.valueOf("2022-06-10");
        Date fechaFinal = Date.valueOf("2022-06-12");

        Date fechaInico1 = Date.valueOf("2022-06-10");
        Date fechaFinal1 = Date.valueOf("2022-06-12");

        Date fechaInico2 = Date.valueOf("2022-06-10");
        Date fechaFinal2 = Date.valueOf("2022-06-12");

        Reservation reservation = new Reservation(fechaInico, fechaFinal, "RESERVADO");
        Reservation reservation1 = new Reservation(fechaInico1, fechaFinal1, "RESERVADO");
        Reservation reservation2 = new Reservation(fechaInico2, fechaFinal2, "RESERVADO");

        List<Reservation> list = Arrays.asList(reservation, reservation1, reservation2);

        try {
            reservationInterface.saveAll(list);
            Logger.info("Reservations added to H2 DataBase");

        } catch (Exception e) {
            Logger.info("No fue posible crear las reservaciones: " + e);
        }

    }

}
