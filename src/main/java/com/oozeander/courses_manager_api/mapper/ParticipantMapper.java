package com.oozeander.courses_manager_api.mapper;

import com.oozeander.courses_manager_api.dto.ParticipantDto;
import com.oozeander.courses_manager_api.entity.Participant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ParticipantMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "race", ignore = true)
    Participant toModel(ParticipantDto dto);
}
