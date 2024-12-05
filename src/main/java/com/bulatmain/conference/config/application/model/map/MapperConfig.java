package com.bulatmain.conference.config.application.model.map;

import com.bulatmain.conference.application.model.dto.map.ConferenceMapper;
import com.bulatmain.conference.application.model.dto.map.OrganizerMapper;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Mapper(
            componentModel = "spring",
            uses = MapperConfig.class,
            unmappedTargetPolicy = ReportingPolicy.IGNORE,
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL
    )
    interface ConferenceMapperMS extends ConferenceMapper {}

    @Mapper(
            componentModel = "spring",
            uses = MapperConfig.class,
            unmappedTargetPolicy = ReportingPolicy.IGNORE,
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL
    )
    interface OrganizerMapperMS extends OrganizerMapper {}
}
