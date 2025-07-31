package ec.edu.espe.rancho;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RanchoApplication {

    public static void main(String[] args) {
        SpringApplication.run(RanchoApplication.class, args);
    }

}
