package com.oozeander.courses_manager_api.publisher;

import com.oozeander.courses_manager_api.common.IntegrationTests;
import com.oozeander.courses_manager_api.dto.RaceDto;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Collections;

@SpringBootTest
class RacePublisherTest extends IntegrationTests {

    @Autowired
    private RacePublisher racePublisher;
    @Value("${kafka.topic}")
    private String topic;

    @Nested
    class PublishRace {

        @Test
        void should_publish_message_to_the_broker_topic() {
            racePublisher.publish(new RaceDto(
                    LocalDate.now(),
                    1,
                    "Prix d'Amérique",
                    Collections.emptySet()
            ));
        }
    }
}