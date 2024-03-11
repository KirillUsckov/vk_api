package ru.kduskov.vkapi;
import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileInputStream;
import java.util.Objects;

@SpringBootApplication
public class ApiApplication {
    @SneakyThrows
    public static void main(String[] args) {

        SpringApplication.run(ApiApplication.class, args);
    }
}
