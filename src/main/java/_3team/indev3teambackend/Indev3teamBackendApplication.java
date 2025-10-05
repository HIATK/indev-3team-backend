package _3team.indev3teambackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class Indev3teamBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(Indev3teamBackendApplication.class, args);
    }

}
