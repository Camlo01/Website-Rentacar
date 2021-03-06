package com.retos.rentacar;

import com.retos.rentacar.interfaces.*;

import com.retos.rentacar.modelo.Entity.Car.Car;
import com.retos.rentacar.modelo.Entity.Car.CarStatus;
import com.retos.rentacar.modelo.Entity.Client.Client;
import com.retos.rentacar.modelo.Entity.Client.ClientType;
import com.retos.rentacar.modelo.Entity.Gama.Gama;
import com.retos.rentacar.modelo.Entity.Message.Message;
import com.retos.rentacar.modelo.Entity.Reservation.Reservation;
import com.retos.rentacar.modelo.Entity.Reservation.ReservationStatus;
import com.retos.rentacar.servicios.ClientServices;
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
    @Autowired
    private ClientServices cs;

    public static void main(String[] args) {
        SpringApplication.run(RentacarApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        createEveryObject();
        Logger.info("---------------------------");
        Logger.info("INICIADO CORRECTAMENTE! :D ");
        Logger.info("---------------------------");
    }

    public void createEveryObject() {

        // ------- Creating Gama Objects

        Gama baja = new Gama("Gama baja", "Veh??culos ligeros");
        Gama media = new Gama("Gama Media", "Veh??culos compactos");
        Gama alta = new Gama("Gama Alta", "Veh??culos pesados, deportivos o de lujo");
        Gama exclusiva = new Gama("Gama Exclusiva", "Veh??culos que para reservar tendr??s que expedir un permiso");
        Gama historicos = new Gama("Gama Historicos", "Veh??culos de hace m??s de 10 a??os");
        Gama comun = new Gama("Gama Com??n", "Los veh??culos m??s comunes que veras por la ciudad ");
        Gama gamaTest = new Gama("gama TEST", "gama de TEST creada para probar crud completo ");
        List<Gama> listOfGamas = Arrays.asList(baja, media, alta, exclusiva, historicos, comun, gamaTest);

        try {

            gamaInterface.saveAll(listOfGamas);
            Logger.info("Gamas added to H2 DataBase");
        } catch (Exception e) {
            System.out.println(e);
        }

        // ------- Creating Cars Objects

        Car camionetaToyota = new Car("Land Cruiser", "Toyota", 2022, "Camioneta color negro", media,
                CarStatus.BOOKABLE);
        Car camionetaJeep = new Car("Gladiador Orverland", "Jeep", 2021, "Camioneta negra grande", alta,
                CarStatus.BOOKABLE);
        Car camionetaMercedes = new Car("Mercedes-Benz Clase Gls 450", "Mercedes-Benz", 2022, "Camioneta Blanca",
                exclusiva, CarStatus.BOOKABLE);
        Car camionetaBMW = new Car("Bmx x7 Xdrive40i Pure", "BMW", 2022, "Camioneta con un hermoso azul", alta,
                CarStatus.BOOKABLE);
        Car camionetaFord = new Car("Ford Bronco Outer Banks", "Ford", 2021, "Camioneta amarilla", baja,
                CarStatus.BOOKABLE);
        Car carroChevrolet = new Car("CheChevrolet Corvetter Stingray Z51", "Chevrolet", 2014, "Carro rojo", baja,
                CarStatus.BOOKABLE);
        Car carroChevroletBlue = new Car("Sail 1,4 Ltz", "Chevrolet", 2018, "Carro color azul", media,
                CarStatus.BOOKABLE);
        Car carroSanderoUsado = new Car("Sandero 1,6 Expression", "Renault", 2012, "Carro bastante usado", media,
                CarStatus.BOOKABLE);
        Car carroSparkPeque??o = new Car("Spark gt 1,2 Mt", "Chevrolet", 2012, "Carro peque??o");
        Car carroSwiftGray = new Car("Swift 1.2 Dzire", "Suzuki", 2020, "color gris");
        Car carroEW212 = new Car("E W212", "Mercedes-benz", 2016, "blanco, se ensucia facil");
        Car carroQ530 = new Car("Q5 3.0 Supercharged", "Audi", 2015, "camioneta lujosa");
        Car carroJetta = new Car("Jetta 2.0", "Volkswagen", 2014, "versi??n Europa");
        Car carTest = new Car("Car of TEST brand", "TEST Brand", 2022, "Car of description");
        Car carTest2 = new Car("Car of TEST brand", "TEST Brand", 2022, "Car of description");
        Car carTest3 = new Car("Car of TEST brand", "TEST Brand", 2022, "Car of description");
        Car carTest4 = new Car("Car of TEST brand", "TEST Brand", 2022, "Car of description");
        Car carTest5 = new Car("Car of TEST brand", "TEST Brand", 2022, "Car of description");
        Car carTest6 = new Car("Car of TEST brand", "TEST Brand", 2022, "Car of description");
        Car carTest7 = new Car("Car of TEST brand", "TEST Brand", 2022, "Car of description");
        Car carTest8 = new Car("Car of TEST brand", "TEST Brand", 2022, "Car of description");
        Car carTest9 = new Car("Car of TEST brand", "TEST Brand", 2022, "Car of description");
        Car carTest10 = new Car("Car of TEST brand", "TEST Brand", 2022, "Car of description");
        Car carTest11 = new Car("Car of TEST brand", "TEST Brand", 2022, "Car of description", alta,
                CarStatus.BOOKABLE);
        Car carTest12 = new Car("Car of TEST brand", "TEST Brand", 2022, "Car of description", alta,
                CarStatus.BOOKABLE);
        Car carTest13 = new Car("Car of TEST brand", "TEST Brand", 2022, "Car of description", alta,
                CarStatus.BOOKABLE);
        Car carTest14 = new Car("Car of TEST brand", "TEST Brand", 2022, "Car of description", alta,
                CarStatus.BOOKABLE);
        Car carTest15 = new Car("Car of TEST brand", "TEST Brand", 2022, "Car of description", alta,
                CarStatus.BOOKABLE);
        Car carTest16 = new Car("Car of TEST brand", "TEST Brand", 2022, "Car of description", alta,
                CarStatus.BOOKABLE);
        Car carTest17 = new Car("Car of TEST brand", "TEST Brand", 2022, "Car of description", alta,
                CarStatus.BOOKABLE);
        Car carTest18 = new Car("Car of TEST brand", "TEST Brand", 2022, "Car of description", alta,
                CarStatus.BOOKABLE);
        Car carTest19 = new Car("Car of TEST brand", "TEST Brand", 2022, "Car of description", alta,
                CarStatus.BOOKABLE);
        Car carTest20 = new Car("Car of TEST brand", "TEST Brand", 2022, "Car of description", alta,
                CarStatus.BOOKABLE);
        Car carTest21 = new Car("Car of TEST brand", "TEST Brand", 2022, "Car of description", alta,
                CarStatus.BOOKABLE);
        Car carTest22 = new Car("Car of TEST brand", "TEST Brand", 2022, "Car of description", alta,
                CarStatus.BOOKABLE);
        Car carTest23 = new Car("Car of TEST brand", "TEST Brand", 2022, "Car of description", alta,
                CarStatus.BOOKABLE);
        Car carTest24 = new Car("Car of TEST brand", "TEST Brand", 2022, "Car of description", alta,
                CarStatus.BOOKABLE);
        Car carTest25 = new Car("Car of TEST brand", "TEST Brand", 2022, "Car of description", alta,
                CarStatus.BOOKABLE);
        Car carTest26 = new Car("Car of TEST brand", "TEST Brand", 2022, "Car of description", alta,
                CarStatus.BOOKABLE);
        Car carTest27 = new Car("Car of TEST brand", "TEST Brand", 2022, "Car of description", alta,
                CarStatus.BOOKABLE);
        Car lastCarAdded = new Car("Ferrari", "Ferrari", 2022, "Thse latest car added", alta, CarStatus.BOOKABLE);
        Car carWithEnum = new Car("ENUM", "ENUM", 2022, "Last bookable vehicle", alta, CarStatus.BOOKABLE);

        List<Car> listOfCars = Arrays.asList(camionetaToyota, camionetaJeep, camionetaMercedes, camionetaBMW,
                camionetaFord, carroChevrolet, carroChevroletBlue, carroSanderoUsado, carroSparkPeque??o, carroSwiftGray,
                carroEW212, carroQ530, carroJetta, carTest, carTest2, carTest3, carTest4, carTest5, carTest6, carTest7,
                carTest8, carTest9, carTest10, carTest11, carTest12, carTest13, carTest14, carTest15, carTest16,
                carTest17, carTest18, carTest19, carTest20, carTest21,
                carTest22, carTest23, carTest24, carTest25, carTest26, carTest27, lastCarAdded, carWithEnum);

        for (Car car : listOfCars) {
            Logger.debug("Carro: " + car.getName() + " su gama: " + car.getGama());
        }

        try {
            carInterface.saveAll(listOfCars);
            Logger.info("Cars added to H2 DataBase");
        } catch (Exception e) {
            Logger.debug("Oops!: " + e);
        }

        // ------- Creating Clients Objects

        // Format of date YYYY-MM-DD
        Date dateReusable = Date.valueOf("2004-07-24");
        Client a = new Client("a", "a", "a", dateReusable, ClientType.CLIENT, "cli");
        Client milo = new Client("Camilo", "Camilo@gmail.com", "12345*", dateReusable);
        Client matt = new Client("Matthew", "Mattew@gmail.com", "*54321", dateReusable);
        Client vale = new Client("Valeria", "Valeria@gmail.com", "password", dateReusable);
        Client clientObject = new Client("cliente", "cliente@mail.com", "cliente", dateReusable, ClientType.CLIENT);
        Client admin = new Client("Admin", "admin", "admin", dateReusable, ClientType.ADMIN, "adm");
        Client support = new Client("soporte", "soporte", "soporte", dateReusable, ClientType.SUPPORT, "sup");
        Client developer = new Client("developer", "developer", "developer", dateReusable, ClientType.DEVELOPER, "dev");


        List<Client> listOfClients = Arrays.asList(milo, matt, vale, a, clientObject, admin, support, developer);

        for (Client user : listOfClients) {
            Logger.debug(
                    "awdawdw Cliente: " + user.getName() + " Mensajes: " + user.getMessages() + " Reservations: "
                            + user.getReservations() + " LLave: " + user.getKeyClient());
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
        Message mensaje2 = new Message("Este veh??culo consume mucha gasolina", camionetaMercedes, matt);
        Message mensaje3 = new Message("El color blanco se ensucia muy f??cil", camionetaBMW, vale);
        Message mensaje4 = new Message("La camioneta es muy ancha", camionetaFord, matt);
        Message mensaje5 = new Message("El carro me lo entregaron sucio", carroChevrolet, milo);
        Message mensaje6 = new Message("Me gustar??a poder alquilar motos", carroChevrolet, matt);
        Message mensaje7 = new Message("No tienen este carro disponible en otra ciudad?", camionetaFord, matt);
        Message mensaje8 = new Message("El carro estaba mal de los frenos", camionetaBMW, vale);
        Message mensaje9 = new Message("No recomiendo para nada este servicio", camionetaMercedes, vale);
        Message mensaje10 = new Message("siempre los uso cuando estoy de vacaciones", camionetaJeep, matt);
        Message mensaje11 = new Message("Deber??an tener m??s carros el??ctricos", camionetaToyota, matt);
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

        //MATT
        Reservation reservation = new Reservation(matt, carTest, fechaInico, fechaFinal, ReservationStatus.REQUESTED);
        Reservation reservation1 = new Reservation(matt, carTest2, fechaInico1, fechaFinal1, ReservationStatus.ACTIVE);
        Reservation reservation2 = new Reservation(matt, carTest3, fechaInico2, fechaFinal2, ReservationStatus.CANCELLED);
        Reservation reservation3 = new Reservation(matt, carTest4, fechaInico2, fechaFinal2, ReservationStatus.POSTPONED);
        Reservation reservation4 = new Reservation(matt, carTest5, fechaInico2, fechaFinal2, ReservationStatus.DENIED);
        Reservation reservation5 = new Reservation(matt, carTest6, fechaInico2, fechaFinal2, ReservationStatus.COMPLETED);
        //VALE
        Reservation reservation6 = new Reservation(vale, carTest7, fechaInico2, fechaFinal2, ReservationStatus.REQUESTED);
        Reservation reservation7 = new Reservation(vale, carTest8, fechaInico2, fechaFinal2, ReservationStatus.ACTIVE);
        Reservation reservation8 = new Reservation(vale, carTest9, fechaInico2, fechaFinal2, ReservationStatus.CANCELLED);
        Reservation reservation9 = new Reservation(vale, carTest10, fechaInico2, fechaFinal2, ReservationStatus.POSTPONED);
        Reservation reservation10 = new Reservation(vale, carTest11, fechaInico2, fechaFinal2, ReservationStatus.DENIED);
        Reservation reservation11 = new Reservation(vale, carTest11, fechaInico2, fechaFinal2, ReservationStatus.COMPLETED);
        //MILO
        Reservation reservation12 = new Reservation(milo, carTest13, fechaInico2, fechaFinal2, ReservationStatus.REQUESTED);
        Reservation reservation13 = new Reservation(milo, carTest14, fechaInico2, fechaFinal2, ReservationStatus.ACTIVE);
        Reservation reservation14 = new Reservation(milo, carTest15, fechaInico2, fechaFinal2, ReservationStatus.CANCELLED);
        Reservation reservation15 = new Reservation(milo, carTest16, fechaInico2, fechaFinal2, ReservationStatus.POSTPONED);
        Reservation reservation16 = new Reservation(milo, carTest17, fechaInico2, fechaFinal2, ReservationStatus.DENIED);
        Reservation reservation17 = new Reservation(milo, carTest18, fechaInico2, fechaFinal2, ReservationStatus.COMPLETED);

        List<Reservation> listOfReservations = Arrays.asList(reservation, reservation1, reservation2, reservation3, reservation4, reservation5, reservation6, reservation7, reservation8, reservation9, reservation10, reservation11, reservation12, reservation13, reservation14, reservation15, reservation16, reservation17
        );

        try {
            reservationInterface.saveAll(listOfReservations);
            Logger.info("Reservations added to H2 DataBase");

        } catch (Exception e) {
            Logger.info("No fue posible crear las reservaciones: " + e);
        }

        // Iterable<Client> listaDeClientes = clientInterface.findAll();
        // listaDeClientes.forEach(client -> Logger.info(client));

    }

}
