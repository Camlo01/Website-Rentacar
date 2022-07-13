package com.retos.rentacar;

import com.retos.rentacar.interfaces.*;
import com.retos.rentacar.modelo.*;

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
        Logger.info("INICIADO CORRECTAMENTE! :D");

    }

    public void createEveryObject() {

        // ------- Creating Gama Objects

        Gama baja = new Gama("Gama baja", "Vehículos ligeros");
        Gama media = new Gama("Gama Media", "Vehículos compactos");
        Gama alta = new Gama("Gama Alta", "Vehículos pesados, deportivos o de lujo");
        Gama exclusiva = new Gama("Gama Exclusiva", "Vehículos que para reservar tendrás que expedir un permiso");
        Gama historicos = new Gama("Gama Historicos", "Vehículos de hace más de 10 años");
        Gama comun = new Gama("Gama Común", "Los vehículos más comunes que veras por la ciudad ");
        Gama gamaTest = new Gama("gama TEST", "gama de TEST creada para probar crud completo ");
        List<Gama> listOfGamas = Arrays.asList(baja, media, alta, exclusiva, historicos, comun, gamaTest);

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
        Car carroChevroletBlue = new Car("Sail 1,4 Ltz", "Chevrolet", 2018, "Carro color azul");
        Car carroSanderoUsado = new Car("Sandero 1,6 Expression", "Renault", 2012, "Carro bastante usado");
        Car carroSparkPequeño = new Car("Spark gt 1,2 Mt", "Chevrolet", 2012, "Carro pequeño");
        Car carroSwiftGray = new Car("Swift 1.2 Dzire", "Suzuki", 2020, "color gris");
        Car carroEW212 = new Car("E W212", "Mercedes-benz", 2016, "blanco, se ensucia facil");
        Car carroQ530 = new Car("Q5 3.0 Supercharged", "Audi", 2015, "camioneta lujosa");
        Car carroJetta = new Car("Jetta 2.0", "Volkswagen", 2014, "versión Europa");
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
        Car carTest11 = new Car("Car of TEST brand", "TEST Brand", 2022, "Car of description");
        Car carTest12 = new Car("Car of TEST brand", "TEST Brand", 2022, "Car of description");
        Car carTest13 = new Car("Car of TEST brand", "TEST Brand", 2022, "Car of description");
        Car carTest14 = new Car("Car of TEST brand", "TEST Brand", 2022, "Car of description");
        Car carTest15 = new Car("Car of TEST brand", "TEST Brand", 2022, "Car of description");
        Car carTest16 = new Car("Car of TEST brand", "TEST Brand", 2022, "Car of description");
        Car carTest17 = new Car("Car of TEST brand", "TEST Brand", 2022, "Car of description");
        Car carTest18 = new Car("Car of TEST brand", "TEST Brand", 2022, "Car of description");
        Car carTest19 = new Car("Car of TEST brand", "TEST Brand", 2022, "Car of description");
        Car carTest20 = new Car("Car of TEST brand", "TEST Brand", 2022, "Car of description");
        Car carTest21 = new Car("Car of TEST brand", "TEST Brand", 2022, "Car of description");
        Car carTest22 = new Car("Car of TEST brand", "TEST Brand", 2022, "Car of description");
        Car carTest23 = new Car("Car of TEST brand", "TEST Brand", 2022, "Car of description");
        Car carTest24 = new Car("Car of TEST brand", "TEST Brand", 2022, "Car of description");
        Car carTest25 = new Car("Car of TEST brand", "TEST Brand", 2022, "Car of description");
        Car carTest26 = new Car("Car of TEST brand", "TEST Brand", 2022, "Car of description");
        Car carTest27 = new Car("Car of TEST brand", "TEST Brand", 2022, "Car of description");
        Car lastCarAdded = new Car("Ferrari", "Ferrari", 2022, "The latest car added", alta);
        Car carWithEnum = new Car("ENUM", "ENUM", 2022, "vehicle with enum", alta, CarStatus.OCCUPIED);

        List<Car> listOfCars = Arrays.asList(camionetaToyota, camionetaJeep, camionetaMercedes, camionetaBMW,
                camionetaFord, carroChevrolet, carroChevroletBlue, carroSanderoUsado, carroSparkPequeño, carroSwiftGray,
                carroEW212, carroQ530, carroJetta, carTest, carTest2, carTest3, carTest4, carTest5, carTest6, carTest7,
                carTest8, carTest9, carTest10, carTest11, carTest12, carTest13, carTest14, carTest15, carTest16,
                carTest17, carTest18, carTest19, carTest20, carTest21,
                carTest22, carTest23, carTest24, carTest25, carTest26, carTest27, lastCarAdded, carWithEnum);

        for (Car car : listOfCars) {
            Logger.debug("Carro: " + car.getName() + " su gama: " + car.getGama());
            System.out.println("CAR ENUM AWDAW" + car.getCarStatus());
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
        Client a = new Client("a", "a", "a", dateReusable);
        Client milo = new Client("Camilo", "Camilo@gmail.com", "12345*", dateReusable);
        Client matt = new Client("Matthew", "Mattew@gmail.com", "*54321", dateReusable);
        Client vale = new Client("Valeria", "Valeria@gmail.com", "password", dateReusable);
        Client ClientTest = new Client("ClientTest", "TestClient@gmail.com",
                "Cl13Nt3_?", dateReusable);
        List<Client> listOfClients = Arrays.asList(milo, matt, vale, ClientTest, a);

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

        Reservation reservation = new Reservation(fechaInico, fechaFinal, camionetaBMW, matt);
        Reservation reservation1 = new Reservation(fechaInico1, fechaFinal1, carroChevrolet, milo);
        Reservation reservation2 = new Reservation(fechaInico2, fechaFinal2, camionetaFord, vale);
        Reservation ReservationTest = new Reservation(fechaInico2, fechaFinal2, carroJetta, matt);

        List<Reservation> listOfReservations = Arrays.asList(reservation, reservation1, reservation2
                , ReservationTest
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
