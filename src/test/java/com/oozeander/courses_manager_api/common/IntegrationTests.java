package com.oozeander.courses_manager_api.common;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

@DirtiesContext
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:tc:postgresql:///test",
        "spring.datasource.driver-class-name=org.testcontainers.jdbc.ContainerDatabaseDriver",
        "spring.datasource.username=root",
        "spring.datasource.password=admin"
})
@EmbeddedKafka(partitions = 1, brokerProperties =
        {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
public class IntegrationTests {

    static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:16.2");

    @BeforeAll
    static void setup() {
        postgreSQLContainer.start();
    }

    @AfterAll
    static void tearDown() {
        postgreSQLContainer.close();
    }
}
