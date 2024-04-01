package com.oozeander.courses_manager_api.validator;

import com.oozeander.courses_manager_api.entity.Participant;
import com.oozeander.courses_manager_api.entity.Race;
import com.oozeander.courses_manager_api.exception.ParticipantsNumberNotLinearException;
import com.oozeander.courses_manager_api.exception.RaceNameNotUniqueException;
import com.oozeander.courses_manager_api.exception.RaceNumberNotUniqueException;
import com.oozeander.courses_manager_api.repository.RaceRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class RaceValidatorTest {

    @Mock
    private RaceRepository raceRepository;
    @InjectMocks
    private RaceValidator raceValidator;

    @Nested
    class ValidateRace {

        private static final String GIVEN_NAME = "Prix d'Amérique";
        private static final int GIVEN_NUMBER = 1;
        private static final String NAME = "Prix de France";
        private static final int NUMBER = 2;

        private static Stream<Arguments> should_check_if_linear_participants_numbers() {
            return Stream.of(
                    Arguments.of(1, 2, 4),
                    Arguments.of(2, 3, 4)
            );
        }

        @Test
        void should_throw_if_given_name_already_exists() {
            given(raceRepository.findAll()).willReturn(
                    List.of(Race.builder().name(GIVEN_NAME).build())
            );

            assertThatExceptionOfType(RaceNameNotUniqueException.class)
                    .isThrownBy(() -> raceValidator.validate(
                            Race.builder().name(GIVEN_NAME).build()
                    ));
        }

        @Test
        void should_throw_if_given_number_already_exists() {
            given(raceRepository.findAll()).willReturn(
                    List.of(Race.builder().name(GIVEN_NAME).number(GIVEN_NUMBER).build())
            );

            assertThatExceptionOfType(RaceNumberNotUniqueException.class)
                    .isThrownBy(() -> raceValidator.validate(
                            Race.builder().name("Prix de France").number(GIVEN_NUMBER).build()
                    ));
        }

        @MethodSource
        @ParameterizedTest
        void should_check_if_linear_participants_numbers(
                int participant1Number, int participant2Number, int participant3Number
        ) {
            given(raceRepository.findAll()).willReturn(
                    List.of(
                            Race.builder()
                                    .name(GIVEN_NAME)
                                    .number(GIVEN_NUMBER)
                                    .build()
                    )
            );

            assertThatExceptionOfType(ParticipantsNumberNotLinearException.class)
                    .isThrownBy(() -> raceValidator.validate(
                            Race.builder()
                                    .name(NAME)
                                    .number(NUMBER)
                                    .participants(
                                            Set.of(
                                                    Participant.builder().number(participant1Number).build(),
                                                    Participant.builder().number(participant2Number).build(),
                                                    Participant.builder().number(participant3Number).build()
                                            )
                                    )
                                    .build()
                    ));
        }

        @Test
        void should_validate_race() {
            given(raceRepository.findAll()).willReturn(
                    List.of(
                            Race.builder()
                                    .name(GIVEN_NAME)
                                    .number(GIVEN_NUMBER)
                                    .build()
                    )
            );

            assertThatNoException().isThrownBy(() -> raceValidator.validate(
                    Race.builder()
                            .name(NAME)
                            .number(NUMBER)
                            .participants(
                                    Set.of(
                                            Participant.builder().number(1).build(),
                                            Participant.builder().number(2).build(),
                                            Participant.builder().number(3).build()
                                    )
                            )
                            .build()
            ));
        }
    }
}
