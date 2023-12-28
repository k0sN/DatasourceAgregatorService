package com.k0s.datasourceagregatorservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
public class AbstractBaseITest {

    @Autowired
    protected ObjectMapper objectMapper;
    @Container
    private static final DockerComposeContainer<?> dockerComposeContainer;

    static {
        dockerComposeContainer = new DockerComposeContainer<>(new File("docker-compose.yml"))
                .withExposedService("postgresdb", 5432, Wait.forListeningPort())
                .withExposedService("postgresdb2", 5432, Wait.forListeningPort())
                .withExposedService("mysqldb", 3306, Wait.forListeningPort());
    }
}
