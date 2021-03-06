package frontEnd.entryPoint;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages={
        "business.dto.CityDTO"})
@EnableAutoConfiguration
@ComponentScan(basePackages = {"persistence.dao", "business.service", "frontEnd.controller"})
public class Main {
    public static void main(String[] args) {

        SpringApplication.run(Main.class, args);

    }
}
