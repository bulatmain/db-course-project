package com.bulatmain.conference.application.model.dto.map;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrganizerMapper {
    OrganizerMapper INSTANCE = Mappers.getMapper(OrganizerMapper.class);


}
