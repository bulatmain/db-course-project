package com.bulatmain.conference.application.model.dto.map;

import com.bulatmain.conference.application.model.dto.conference.ConferenceCreateDTO;
import com.bulatmain.conference.application.model.dto.conference.ConferenceDTO;
import com.bulatmain.conference.application.port.request.RegisterConferenceRequest;
import org.mapstruct.factory.Mappers;

public interface ConferenceMapper {

    ConferenceDTO createDtoToDto(ConferenceCreateDTO dto);
    ConferenceCreateDTO registerRequestToCreateDto(RegisterConferenceRequest dto);
}
