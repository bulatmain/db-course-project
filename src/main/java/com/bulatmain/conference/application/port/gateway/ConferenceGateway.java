package com.bulatmain.conference.application.port.gateway;

import com.bulatmain.conference.application.model.dto.conference.ConferenceCreateDTO;
import com.bulatmain.conference.application.model.dto.conference.ConferenceDTO;
import com.bulatmain.conference.application.port.gateway.exception.GatewayException;

import java.util.Optional;

public interface ConferenceGateway {
    Optional<ConferenceDTO> findByOrganizerIdAndName(String organizerId, String name)  throws GatewayException;
    ConferenceDTO save(ConferenceCreateDTO dto) throws GatewayException;
}
