package dev.molleapaza.sprintboot.webflux.app;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class SprintBootWebfluxApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SprintBootWebfluxApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
    }
}
