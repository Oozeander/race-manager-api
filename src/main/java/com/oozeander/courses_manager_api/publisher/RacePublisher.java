package com.oozeander.courses_manager_api.publisher;

import com.oozeander.courses_manager_api.dto.RaceDto;
import com.oozeander.courses_manager_api.entity.Race;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class RacePublisher {

    private final KafkaTemplate<String, Race> kafkaTemplate;
    private final NewTopic courses;

    public void publish(RaceDto raceDto) {
        kafkaTemplate.send(
                MessageBuilder
                        .withPayload(raceDto)
                        .setHeader(KafkaHeaders.TOPIC, courses.name())
                        .build()
        ).whenComplete((result, err) ->
                log.info("Sent %s to the %s topic at offset=[%s]"
                        .formatted(raceDto, courses.name(), result.getRecordMetadata().offset()))
        );
    }
}
