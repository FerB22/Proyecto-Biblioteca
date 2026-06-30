package com.biblioteca.Prestamo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient // <--- ¡Esta es la línea mágica que lo conecta a Eureka!
public class PrestamoApplication {

    public static void main(String[] args) {
        SpringApplication.run(PrestamoApplication.class, args);
    }

}