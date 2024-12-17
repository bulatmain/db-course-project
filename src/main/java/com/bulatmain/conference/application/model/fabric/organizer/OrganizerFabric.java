package com.bulatmain.conference.application.model.fabric.organizer;

import com.bulatmain.conference.application.model.dto.map.OrganizerMapper;
import com.bulatmain.conference.application.model.dto.organizer.OrganizerDTO;
import com.bulatmain.conference.application.model.fabric.Fabric;
import com.bulatmain.conference.domain.organizer.entity.Organizer;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrganizerFabric implements Fabric<Organizer, OrganizerDTO> {
    private final OrganizerMapper organizerMapper;
    private final OrganizerIdFabric idFabric;

    @Override
    public Organizer create(OrganizerDTO dto) {
        var id = idFabric.create(dto.getId());
        return new Organizer(id);
    }

    @Override
    public Organizer restore(OrganizerDTO dto) {
        return create(dto);
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
