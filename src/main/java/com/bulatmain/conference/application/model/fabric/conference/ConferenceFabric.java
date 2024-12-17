package com.bulatmain.conference.application.model.fabric.conference;

import com.bulatmain.conference.application.model.dto.conference.ConferenceCreateDTO;
import com.bulatmain.conference.application.model.dto.conference.ConferenceDTO;
import com.bulatmain.conference.application.model.dto.map.ConferenceMapper;
import com.bulatmain.conference.application.model.fabric.Fabric;
import com.bulatmain.conference.application.model.fabric.organizer.OrganizerFabric;
import com.bulatmain.conference.application.port.request.RegisterConferenceRequest;
import com.bulatmain.conference.domain.conference.entity.Conference;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ConferenceFabric implements Fabric<Conference, ConferenceDTO> {
    private final ConferenceMapper mapper;
    private final ConferenceIdFabric idFabric;
    private final ConferenceNameFabric nameFabric;
    private final OrganizerFabric organizerFabric;
    @Override
    public Conference create(ConferenceDTO dto) {
        var name = nameFabric.create(dto.getConferenceName());
        var organizer = organizerFabric.create(dto.getOrganizerId());
        var id = idFabric.create(dto.getConferenceName(), dto.getOrganizerId());
        return new Conference(id, name, organizer);
    }

    @Override
    public Conference restore(ConferenceDTO dto) {
        var name = nameFabric.restore(dto.getConferenceName());
        var organizer = organizerFabric.restore(dto.getOrganizerId());
        var id = idFabric.restore(dto.getId());
        return new Conference(id, name, organizer);
    }

    public Conference create(ConferenceCreateDTO dto) {
        return create(mapper.createDtoToDto(dto));
    }

    public Conference create(RegisterConferenceRequest request) {
        return create(mapper.registerRequestToCreateDto(request));
    }
}
