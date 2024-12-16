package com.bulatmain.conference.application.model.dto.map;

import com.bulatmain.conference.application.model.dto.conference.ConferenceCreateDTO;
import com.bulatmain.conference.application.model.dto.conference.ConferenceDTO;
import com.bulatmain.conference.application.port.request.RegisterConferenceRequest;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL
)
public interface ConferenceMapper {

    ConferenceDTO createDtoToDto(ConferenceCreateDTO dto);
    ConferenceCreateDTO registerRequestToCreateDto(RegisterConferenceRequest dto);
}
