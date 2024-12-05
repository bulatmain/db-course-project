package com.bulatmain.conference.application.port.gateway;

import com.bulatmain.conference.application.model.dto.conference.ConferenceCreateDTO;
import com.bulatmain.conference.application.model.dto.conference.ConferenceDTO;

import java.util.Optional;

public interface ConferenceGateway {
    Optional<ConferenceDTO> findByOrganizerIdAndName(String organizerId, String name);
    ConferenceDTO save(ConferenceCreateDTO dto);
}
