package com.zalisoft.teamapi;

import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@Async
public class TeamAppBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeamAppBackendApplication.class, args);
    }

}
