package br.com.carrental;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Class that starts the application following the convention of SpringBootApplication.
 *
 * @author Micael
 */

@SpringBootApplication(scanBasePackages = "br.com.carrental")
public class CarRentalApplication {
    public static void main(String[] args) {
        SpringApplication.run(CarRentalApplication.class, args);
    }
}
