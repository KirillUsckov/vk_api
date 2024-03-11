package ru.kduskov.vkapi;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
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
        ClassLoader classLoader = ApiApplication.class.getClassLoader();
        File f = new File(Objects.requireNonNull(classLoader.getResource("fbServicePrivateKey.json")).toString()).getParentFile();
        FileInputStream serviceAccount =
                new FileInputStream(f.getAbsolutePath());

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        FirebaseApp.initializeApp(options);

        SpringApplication.run(ApiApplication.class, args);
    }
}
