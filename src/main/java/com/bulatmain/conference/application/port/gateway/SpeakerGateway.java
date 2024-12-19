package com.bulatmain.conference.application.port.gateway;

import com.bulatmain.conference.application.model.dto.speaker.SpeakerCreateDTO;
import com.bulatmain.conference.application.model.dto.speaker.SpeakerDTO;
import com.bulatmain.conference.application.port.gateway.exception.GatewayException;

import java.util.Optional;

public interface SpeakerGateway {
    Optional<SpeakerDTO> findById(String id) throws GatewayException;

    SpeakerDTO save(SpeakerCreateDTO dto) throws GatewayException;
}
