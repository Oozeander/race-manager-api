package com.oozeander.courses_manager_api.validator;

import com.oozeander.courses_manager_api.entity.Participant;
import com.oozeander.courses_manager_api.entity.Race;
import com.oozeander.courses_manager_api.exception.ParticipantsNumberNotLinearException;
import com.oozeander.courses_manager_api.exception.RaceNameNotUniqueException;
import com.oozeander.courses_manager_api.exception.RaceNumberNotUniqueException;
import com.oozeander.courses_manager_api.repository.RaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class RaceValidator {

    private final RaceRepository raceRepository;

    public void validate(Race race) {
        raceRepository.findAll().forEach(c -> {
            if (race.getName().equals(c.getName()))
                throw new RaceNameNotUniqueException();
            else if (race.getNumber() == c.getNumber())
                throw new RaceNumberNotUniqueException();
        });

        if (!isNumbersLinear(race.getParticipants()))
            throw new ParticipantsNumberNotLinearException();
    }

    private boolean isNumbersLinear(Set<Participant> participants) {
        // Convert the set to an array
        Integer[] numbers = participants.stream().map(Participant::getNumber)
                .distinct().toArray(Integer[]::new);

        // Sort the array
        Arrays.sort(numbers);

        // Check if the difference between consecutive numbers is always 1
        for (int i = 1; i < numbers.length; i++)
            if (numbers[i] - numbers[i - 1] != 1)
                return false;

        // Ensure that the first number is 1
        return numbers[0] == 1;
    }
}
