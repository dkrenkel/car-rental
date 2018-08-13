package br.com.carrental.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "br.com.carrental")
public class CarRentalApplication {
    public static void main(String[] args){
        SpringApplication.run(CarRentalApplication.class, args);
    }
}
