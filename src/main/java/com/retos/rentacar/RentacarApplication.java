package com.retos.rentacar;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan(basePackages = {"com.retos.rentacar.modelo"})
@SpringBootApplication
public class RentacarApplication implements CommandLineRunner {

    private final Log Logger = LogFactory.getLog(RentacarApplication.class);


    public static void main(String[] args) {
        SpringApplication.run(RentacarApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Logger.info("---------------------------");
        Logger.info("INICIADO CORRECTAMENTE! :D ");
        Logger.info("---------------------------");
    }


}
