package com.bsc.stokoin.config.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

@Slf4j
@Configuration
public class FireBaseConfig {

    @Value("${app.firebase-configuration-file}")
    private String firebaseConfigPath;

    @PostConstruct
    public void init() {
        try {

            Resource resource = new ClassPathResource(firebaseConfigPath);
            //현재경로 나타내는 메소드 java
            String currentDirectory = System.getProperty("user.dir");
            log.info("현재 작업 디렉토리: {}", currentDirectory);

            FirebaseOptions.Builder optionBuilder = FirebaseOptions.builder();
            FirebaseOptions options = optionBuilder
                    .setCredentials(GoogleCredentials.fromStream(resource.getInputStream()))
                    .build();
            FirebaseApp.initializeApp(options);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
