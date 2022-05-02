package com.retos.rentacar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan(basePackages = {"com.retos.rentacar.modelo"})
@SpringBootApplication
public class RentacarApplication {

	public static void main(String[] args) {
		SpringApplication.run(RentacarApplication.class, args);
	}

}
