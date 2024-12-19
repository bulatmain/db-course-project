package com.bulatmain.conference.application.model.dto.map;

import com.bulatmain.conference.application.model.dto.listener.ListenerCreateDTO;
import com.bulatmain.conference.application.model.dto.listener.ListenerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL
)
public interface ListenerMapper {
    ListenerDTO createDtoToDto(ListenerCreateDTO dto);
}

