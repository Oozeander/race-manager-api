package com.oozeander.courses_manager_api.mapper;

import com.oozeander.courses_manager_api.dto.ParticipantDto;
import com.oozeander.courses_manager_api.dto.RaceDto;
import com.oozeander.courses_manager_api.entity.Participant;
import com.oozeander.courses_manager_api.entity.Race;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest(classes = {RaceMapperImpl.class, ParticipantMapperImpl.class})
class RaceMapperTest {

    private static final RaceDto COURSE_DTO = new RaceDto(
            LocalDate.now(),
            1,
            "Prix d'Amérique",
            Set.of(
                    new ParticipantDto(1, "HOOKER BERRY"),
                    new ParticipantDto(2, "IDAO DE TILLARD"),
                    new ParticipantDto(3, "HORSY DREAM")
            )
    );

    @Autowired
    private RaceMapper raceMapper;

    @Test
    void should_map_dto_to_entity() {
        Race race = raceMapper.toModel(COURSE_DTO);

        assertThat(race).extracting(
                Race::getName, Race::getId, Race::getDate, Race::getNumber
        ).containsExactly(COURSE_DTO.name(), null, COURSE_DTO.date(), COURSE_DTO.number());

        assertThat(race.getParticipants())
                .hasSize(COURSE_DTO.participants().size())
                .extracting(Participant::getNumber, Participant::getId, Participant::getName)
                .containsAll(
                        List.of(
                                tuple(1, null, "HOOKER BERRY"),
                                tuple(2, null, "IDAO DE TILLARD"),
                                tuple(3, null, "HORSY DREAM"))
                );
    }

    @Test
    void should_return_null_if_given_course_is_null() {
        assertThat(raceMapper.toModel(null)).isNull();
    }

    @Test
    void should_return_empty_set_if_given_partants_are_null() {
        RaceDto raceDto = new RaceDto(
                LocalDate.now(),
                1,
                "Prix d'Amérique",
                Collections.emptySet()
        );

        Race race = raceMapper.toModel(raceDto);

        assertAll(
                () -> assertThat(race).extracting(Race::getName, Race::getId, Race::getDate, Race::getNumber)
                        .containsExactly(COURSE_DTO.name(), null, COURSE_DTO.date(), COURSE_DTO.number()),
                () -> assertThat(race.getParticipants()).isEmpty()
        );
    }
}