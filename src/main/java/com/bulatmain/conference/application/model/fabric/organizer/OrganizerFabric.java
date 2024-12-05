package com.bulatmain.conference.application.model.fabric.organizer;

import com.bulatmain.conference.application.model.dto.map.OrganizerMapper;
import com.bulatmain.conference.application.model.dto.organizer.OrganizerDTO;
import com.bulatmain.conference.domain.organizer.entity.Organizer;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrganizerFabric {
    private final OrganizerMapper organizerMapper;

    public Organizer build(OrganizerDTO dto) {
        var idFabric = new OrganizerIdFabric();
        var id = idFabric.build(dto.getId());
        return new Organizer(id);
    }

}
