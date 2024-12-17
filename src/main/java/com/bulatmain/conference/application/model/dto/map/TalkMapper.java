package com.bulatmain.conference.application.model.dto.map;

import com.bulatmain.conference.application.model.dto.talk.TalkCreateDTO;
import com.bulatmain.conference.application.model.dto.talk.TalkDTO;
import com.bulatmain.conference.application.port.request.RegisterTalkRequest;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL
)
public interface TalkMapper {

    TalkDTO createDtoToDto(TalkCreateDTO dto);
    TalkCreateDTO registerRequestToCreateDto(RegisterTalkRequest dto);
}