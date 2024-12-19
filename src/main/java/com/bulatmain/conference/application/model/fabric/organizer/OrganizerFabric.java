package com.bulatmain.conference.application.model.fabric.organizer;

import com.bulatmain.conference.application.model.dto.map.OrganizerMapper;
import com.bulatmain.conference.application.model.dto.organizer.OrganizerDTO;
import com.bulatmain.conference.application.model.fabric.Fabric;
import com.bulatmain.conference.application.model.fabric.conference.ConferenceFabric;
import com.bulatmain.conference.domain.conference.entity.Conference;
import com.bulatmain.conference.domain.organizer.entity.Organizer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class OrganizerFabric implements Fabric<Organizer, OrganizerDTO> {
    private final OrganizerMapper organizerMapper;
    private final OrganizerIdFabric idFabric;
    private final ConferenceFabric conferenceFabric;

    @Override
    public Organizer create(OrganizerDTO dto) {
        var id = idFabric.create(dto.getId());
        return new Organizer(id);
    }

    @Override
    public Organizer restore(OrganizerDTO dto) {
        var id = idFabric.create(dto.getId());
        var conferences = restoreConferences(dto.getConferenceIds());
        var organizer = new Organizer(id);
        organizer.setConferences(conferences);
        return organizer;
    }

    private Collection<Conference> restoreConferences(Collection<String> conferenceIds) {
        if (conferenceIds == null) {
            return null;
        } else {
            return conferenceIds.stream()
                    .map(conferenceFabric::restore)
                    .toList();
        }
    }

    public Organizer create(String id) {
        return create(OrganizerDTO.builder()
                .id(id)
                .build()
        );
    }

    public Organizer restore(String id) {
        return restore(OrganizerDTO.builder()
                .id(id)
                .build()
        );
    }

}
