package com.oozeander.courses_manager_api.service;

import com.oozeander.courses_manager_api.dto.RaceDto;
import com.oozeander.courses_manager_api.entity.Race;
import com.oozeander.courses_manager_api.mapper.RaceMapper;
import com.oozeander.courses_manager_api.publisher.RacePublisher;
import com.oozeander.courses_manager_api.repository.RaceRepository;
import com.oozeander.courses_manager_api.validator.RaceValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class RaceManagerService {

    private final RaceRepository raceRepository;
    private final RaceMapper raceMapper;
    private final RacePublisher racePublisher;
    private final RaceValidator raceValidator;

    public void saveAndPublishRace(RaceDto raceDto) {
        Race race = raceMapper.toModel(raceDto);
        raceValidator.validate(race);
        race.getParticipants().forEach(participant -> participant.addRace(race));
        raceRepository.save(race);
        log.info("Saved %s in DB".formatted(race));
        racePublisher.publish(raceDto);
    }
}
