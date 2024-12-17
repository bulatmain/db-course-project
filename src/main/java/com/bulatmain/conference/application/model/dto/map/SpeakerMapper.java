package com.bulatmain.conference.application.model.dto.map;

import com.bulatmain.conference.application.model.dto.speaker.SpeakerCreateDTO;
import com.bulatmain.conference.application.model.dto.speaker.SpeakerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL
)
public interface SpeakerMapper {
    SpeakerDTO createDtoToDto(SpeakerCreateDTO dto);
}

