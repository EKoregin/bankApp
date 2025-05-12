package ru.korevg.processing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients("ru.korevg.processing.client")
public class ProcessingApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProcessingApplication.class, args);
    }

}
