package com.oozeander.courses_manager_api.mapper;

import com.oozeander.courses_manager_api.dto.RaceDto;
import com.oozeander.courses_manager_api.entity.Race;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ParticipantMapper.class})
public interface RaceMapper {

    @Mapping(target = "id", ignore = true)
    Race toModel(RaceDto dto);
}
