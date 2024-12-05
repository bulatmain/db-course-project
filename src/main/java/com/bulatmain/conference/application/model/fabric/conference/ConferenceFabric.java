package com.bulatmain.conference.application.model.fabric.conference;

import com.bulatmain.conference.application.model.dto.conference.ConferenceCreateDTO;
import com.bulatmain.conference.application.model.dto.conference.ConferenceDTO;
import com.bulatmain.conference.application.model.dto.map.ConferenceMapper;
import com.bulatmain.conference.application.model.fabric.organizer.OrganizerIdFabric;
import com.bulatmain.conference.application.port.request.RegisterConferenceRequest;
import com.bulatmain.conference.domain.conference.entity.Conference;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ConferenceFabric {
    protected final ConferenceMapper mapper;
    public Conference build(ConferenceDTO dto) {
        var nameFabric = new ConferenceNameFabric();
        var name = nameFabric.build(dto.getConferenceName());
        var organizerIdFabric = new OrganizerIdFabric();
        var organizerId = organizerIdFabric.build(dto.getOrganizerId());
        return new Conference(name, organizerId);
    }

    public Conference build(ConferenceCreateDTO dto) {
        return build(mapper.createDtoToDto(dto));
    }

    public Conference build(RegisterConferenceRequest request) {
        return build(mapper.registerRequestToCreateDto(request));
    }
}
