package com.bulatmain.conference.application.port.gateway;

import com.bulatmain.conference.application.model.dto.organizer.OrganizerCreateDTO;
import com.bulatmain.conference.application.model.dto.organizer.OrganizerDTO;

import java.util.Optional;

public interface OrganizerGateway {
    Optional<OrganizerDTO> findById(String id);
    OrganizerDTO save(OrganizerCreateDTO dto);
}
