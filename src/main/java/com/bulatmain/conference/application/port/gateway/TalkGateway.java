package com.bulatmain.conference.application.port.gateway;

import com.bulatmain.conference.application.model.dto.speaker.SpeakerCreateDTO;
import com.bulatmain.conference.application.model.dto.speaker.SpeakerDTO;
import com.bulatmain.conference.application.model.dto.talk.TalkCreateDTO;
import com.bulatmain.conference.application.model.dto.talk.TalkDTO;
import com.bulatmain.conference.application.port.gateway.exception.GatewayException;

import java.util.Optional;

public interface TalkGateway {
    Optional<TalkDTO> findByConfIdAndName(String conferenceId, String name)
            throws GatewayException;

    Optional<TalkDTO> findById(String id) throws GatewayException;
    TalkDTO save(TalkCreateDTO dto) throws GatewayException;

    SpeakerDTO addSpeaker(String talkId, SpeakerCreateDTO speakerCreateDto) throws GatewayException;

}