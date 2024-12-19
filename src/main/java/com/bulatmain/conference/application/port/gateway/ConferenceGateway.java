package com.bulatmain.conference.application.port.gateway;

import com.bulatmain.conference.application.model.dto.conference.ConferenceCreateDTO;
import com.bulatmain.conference.application.model.dto.conference.ConferenceDTO;
import com.bulatmain.conference.application.model.dto.talk.TalkCreateDTO;
import com.bulatmain.conference.application.model.dto.talk.TalkDTO;
import com.bulatmain.conference.application.port.gateway.exception.GatewayException;
import com.bulatmain.conference.domain.talk.entity.Talk;

import java.util.Collection;
import java.util.Optional;

public interface ConferenceGateway {
    Optional<ConferenceDTO> findByOrganizerIdAndName(String organizerId, String name)  throws GatewayException;
    ConferenceDTO save(ConferenceCreateDTO dto) throws GatewayException;

    Optional<ConferenceDTO> findById(String conferenceId) throws GatewayException;

    TalkDTO addTalk(TalkCreateDTO talkCreateDto) throws GatewayException;

    Collection<ConferenceDTO> getConferences() throws GatewayException;
}
