package com.bulatmain.conference.application.model.fabric.conference;

import com.bulatmain.conference.application.model.dto.conference.ConferenceCreateDTO;
import com.bulatmain.conference.application.model.dto.conference.ConferenceDTO;
import com.bulatmain.conference.application.model.dto.map.ConferenceMapper;
import com.bulatmain.conference.application.model.fabric.Fabric;
import com.bulatmain.conference.application.model.fabric.organizer.OrganizerFabric;
import com.bulatmain.conference.application.model.fabric.talk.TalkFabric;
import com.bulatmain.conference.application.port.request.RegisterConferenceRequest;
import com.bulatmain.conference.domain.conference.entity.Conference;
import com.bulatmain.conference.domain.talk.entity.Talk;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConferenceFabric implements Fabric<Conference, ConferenceDTO> {
    private final ConferenceMapper mapper;
    private final ConferenceIdFabric idFabric;
    private final ConferenceNameFabric nameFabric;
    private final TalkFabric talkFabric;

    @Override
    public Conference create(ConferenceDTO dto) {
        var name = nameFabric.create(dto.getConferenceName());
        var id = idFabric.create(dto.getConferenceName(), dto.getOrganizerId());
        return new Conference(id, name);
    }

    @Override
    public Conference restore(ConferenceDTO dto) {
        var name = nameFabric.restore(dto.getConferenceName());
        var id = idFabric.restore(dto.getId());
        var talks = restoreTalks(dto.getTalkIds());
        var conf = new Conference(id, name);
        conf.setTalks(talks);
        return conf;
    }

    public Conference restore(String id) {
        return restore(
                ConferenceDTO.builder()
                        .id(id)
                        .build()
        );
    }

    public Conference create(ConferenceCreateDTO dto) {
        return create(mapper.createDtoToDto(dto));
    }

    public Conference create(RegisterConferenceRequest request) {
        return create(mapper.registerRequestToCreateDto(request));
    }

    private Collection<Talk> restoreTalks(Collection<String> talkIds) {
        if (talkIds == null) {
            return null;
        } else {
            return talkIds.stream()
                    .map(talkFabric::restore)
                    .collect(Collectors.toSet());
        }
    }
}
