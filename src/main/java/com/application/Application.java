package com.application;

import java.time.LocalDateTime;
import java.time.ZoneId;

import java.util.Date;

import com.application.entities.AccountDao;
import com.application.repositories.AccountRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
@EnableCaching
@EnableCircuitBreaker
public class Application {

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Methods
    //~ ----------------------------------------------------------------------------------------------------------------

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    CommandLineRunner initDatabase(AccountRepository repository) {
        return
            args -> {
            repository.save(new AccountDao(1L, "RO00 RZBR 0000 0000 0000 0001", "RON", 10000L, Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant())));
            repository.save(new AccountDao(2L, "RO00 RZBR 0000 0000 0000 0002", "USD", 5000L, Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant())));
            repository.save(new AccountDao(3L, "RO00 RZBR 0000 0000 0000 0003", "CAD", 7000L, Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant())));
            repository.save(new AccountDao(4L, "RO00 RZBR 0000 0000 0000 0004", "EUR", 3000L, Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant())));
            repository.save(new AccountDao(5L, "RO00 RZBR 0000 0000 0000 0005", "RON", 11050L, Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant())));
        };
    }
}
