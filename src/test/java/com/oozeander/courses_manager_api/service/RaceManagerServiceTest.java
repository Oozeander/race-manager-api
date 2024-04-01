package com.oozeander.courses_manager_api.service;

import com.oozeander.courses_manager_api.dto.ParticipantDto;
import com.oozeander.courses_manager_api.dto.RaceDto;
import com.oozeander.courses_manager_api.entity.Race;
import com.oozeander.courses_manager_api.exception.ParticipantsNumberNotLinearException;
import com.oozeander.courses_manager_api.exception.RaceNameNotUniqueException;
import com.oozeander.courses_manager_api.exception.RaceNumberNotUniqueException;
import com.oozeander.courses_manager_api.mapper.RaceMapper;
import com.oozeander.courses_manager_api.publisher.RacePublisher;
import com.oozeander.courses_manager_api.repository.RaceRepository;
import com.oozeander.courses_manager_api.validator.RaceValidator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class RaceManagerServiceTest {

    private static final RaceDto COURSE_DTO = new RaceDto(
            LocalDate.now(),
            1,
            "Prix d'Amérique",
            Set.of(
                    new ParticipantDto(1, "HORSY DREAM"),
                    new ParticipantDto(2, "HOOKER  BERRY"),
                    new ParticipantDto(3, "IDAO DE TILLARD")
            )
    );
    @Mock
    private RaceRepository raceRepository;
    @Mock
    private RaceValidator raceValidator;
    @Mock
    private RaceMapper raceMapper;
    @Mock
    private RacePublisher racePublisher;
    @InjectMocks
    private RaceManagerService raceManagerService;

    @Nested
    class SaveAndPublishRace {

        private static Stream<Arguments> should_not_save_and_publish_race_when_validator_fails() {
            return Stream.of(
                    Arguments.of(new RaceNumberNotUniqueException()),
                    Arguments.of(new RaceNameNotUniqueException()),
                    Arguments.of(new ParticipantsNumberNotLinearException())
            );
        }

        @Test
        void should_save_and_publish_race() {
            given(raceMapper.toModel(any(RaceDto.class))).willReturn(Race.builder().build());

            assertThatNoException().isThrownBy(() ->
                    raceManagerService.saveAndPublishRace(COURSE_DTO));
        }

        @MethodSource
        @ParameterizedTest
        void should_not_save_and_publish_race_when_validator_fails(Throwable throwable) {
            given(raceMapper.toModel(any(RaceDto.class))).willReturn(Race.builder().build());
            doThrow(throwable).when(raceValidator).validate(any(Race.class));

            assertThatExceptionOfType(throwable.getClass()).isThrownBy(() ->
                    raceManagerService.saveAndPublishRace(COURSE_DTO));
        }
    }
}