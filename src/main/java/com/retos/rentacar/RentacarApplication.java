package com.retos.rentacar;

import com.retos.rentacar.interfaces.*;
import com.retos.rentacar.modelo.*;
import com.retos.rentacar.repositorio.ClientRepository;

import ch.qos.logback.classic.Logger;
import lombok.val;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.lang.reflect.Array;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@EntityScan(basePackages = { "com.retos.rentacar.modelo" })
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
        createEveryObject();
    }

    public void createEveryObject() {

        // ------- Creating Gama Objects

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

        // ------- Creating Cars Objects

        Car camionetaToyota = new Car("Land Cruiser", "Toyota", 2022, "Camioneta color negro", media);
        Car camionetaJeep = new Car("Gladiador Orverland", "Jeep", 2021, "Camioneta negra grande", alta);
        Car camionetaMercedes = new Car("Mercedes-Benz Clase Gls 450", "Mercedes-Benz", 2022, "Camioneta Blanca",
                exclusiva);
        Car camionetaBMW = new Car("Bmx x7 Xdrive40i Pure", "BMW", 2022, "Camioneta con un hermoso azul", alta);
        Car camionetaFord = new Car("Ford Bronco Outer Banks", "Ford", 2021, "Camioneta amarilla", baja);
        Car carroChevrolet = new Car("CheChevrolet Corvetter Stingray Z51", "Chevrolet", 2014, "Carro rojo", baja);
        List<Car> listOfCars = Arrays.asList(camionetaToyota, camionetaJeep, camionetaMercedes, camionetaBMW,
                camionetaFord, carroChevrolet);

        for (Car car : listOfCars) {
            Logger.debug("Carro: " + car.getName() + " su gama: " + car.getGama().getName());
        }

        try {
            carInterface.saveAll(listOfCars);
            Logger.info("Cars added to H2 DataBase");
        } catch (Exception e) {
            Logger.debug("Oops!: " + e);
        }

        // ------- Creating Clients Objects

        Client milo = new Client("Camilo", "Camilo@gmail.com", "12345*", 18);
        Client matt = new Client("Matthew", "Mattew@gmail.com", "*54321", 22);
        Client vale = new Client("Valeria", "Valeria@gmail.com", "C0ntra?se(*a", 19);
        List<Client> listOfClients = Arrays.asList(milo, matt, vale);

        for (Client client : listOfClients) {
            Logger.debug(
                    "awdawdw Cliente: " + client.getName() + " Mensajes: " + client.getMessages() + " Reservations: "
                            + client.getReservations());
        }

        try {
            clientInterface.saveAll(listOfClients);
            Logger.info("Clients added to H2 DataBase");

        } catch (Exception e) {
            Logger.info("No fue posible crear el cliente: " + e);
        }

        // ------- Creating Messages Objects

        Message mensaje = new Message("Este carro es muy bonito", camionetaToyota, milo);
        Message mensaje1 = new Message("El carro es dificil de conducir", camionetaJeep, vale);
        Message mensaje2 = new Message("Este vehículo consume mucha gasolina", camionetaMercedes, matt);
        Message mensaje3 = new Message("El color blanco se ensucia muy fácil", camionetaBMW, vale);
        Message mensaje4 = new Message("La camioneta es muy ancha", camionetaFord, matt);
        Message mensaje5 = new Message("El carro me lo entregaron sucio", carroChevrolet, milo);
        Message mensaje6 = new Message("Me gustaría poder alquilar motos", carroChevrolet, matt);
        Message mensaje7 = new Message("No tienen este carro disponible en otra ciudad?", camionetaFord, matt);
        Message mensaje8 = new Message("El carro estaba mal de los frenos", camionetaBMW, vale);
        Message mensaje9 = new Message("No recomiendo para nada este servicio", camionetaMercedes, vale);
        Message mensaje10 = new Message("siempre los uso cuando estoy de vacaciones", camionetaJeep, matt);
        Message mensaje11 = new Message("Deberían tener más carros eléctricos", camionetaToyota, matt);
        Message mensaje12 = new Message("Este carro tiene muy mal motor", camionetaBMW, vale);
        Message mensaje13 = new Message("Hace falta de mantenimiento a los carros", camionetaFord, vale);
        Message mensaje14 = new Message("no tienen del color que me gusta", camionetaJeep, milo);

        List<Message> listOfMessages = Arrays.asList(mensaje, mensaje1, mensaje2, mensaje3, mensaje4, mensaje5,
                mensaje6, mensaje7, mensaje8, mensaje9, mensaje10, mensaje11, mensaje12, mensaje13, mensaje14);

        try {
            messageInterface.saveAll(listOfMessages);
            Logger.info("Messages added to H2 DataBase");

        } catch (Exception e) {
            Logger.info("No fue posible crear los mensajes: " + e);
        }

        // ------- Creating Reservation Objects

        Date fechaInico = Date.valueOf("2022-06-10");
        Date fechaFinal = Date.valueOf("2022-06-12");

        Date fechaInico1 = Date.valueOf("2022-06-10");
        Date fechaFinal1 = Date.valueOf("2022-06-12");

        Date fechaInico2 = Date.valueOf("2022-06-10");
        Date fechaFinal2 = Date.valueOf("2022-06-12");

        Reservation reservation = new Reservation(fechaInico, fechaFinal, "RESERVADO", camionetaBMW, matt);
        Reservation reservation1 = new Reservation(fechaInico1, fechaFinal1, "RESERVADO", carroChevrolet, milo);
        Reservation reservation2 = new Reservation(fechaInico2, fechaFinal2, "RESERVADO", camionetaFord, vale);

        List<Reservation> listOfReservations = Arrays.asList(reservation, reservation1, reservation2);

        try {
            reservationInterface.saveAll(listOfReservations);
            Logger.info("Reservations added to H2 DataBase");

        } catch (Exception e) {
            Logger.info("No fue posible crear las reservaciones: " + e);
        }

        Iterable<Client> listaDeClientes = clientInterface.findAll();
        listaDeClientes.forEach(client -> Logger.info(client));

    }

}
