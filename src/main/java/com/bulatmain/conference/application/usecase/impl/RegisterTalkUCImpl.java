package com.bulatmain.conference.application.usecase.impl;

import com.bulatmain.conference.application.model.dto.map.TalkMapper;
import com.bulatmain.conference.application.model.dto.speaker.SpeakerCreateDTO;
import com.bulatmain.conference.application.model.dto.speaker.SpeakerDTO;
import com.bulatmain.conference.application.model.dto.talk.TalkCreateDTO;
import com.bulatmain.conference.application.model.fabric.conference.ConferenceFabric;
import com.bulatmain.conference.application.model.fabric.speaker.SpeakerFabric;
import com.bulatmain.conference.application.model.fabric.talk.TalkFabric;
import com.bulatmain.conference.application.port.event.SpeakerRegisteredEvent;
import com.bulatmain.conference.application.port.event.TalkRegisteredEvent;
import com.bulatmain.conference.application.port.gateway.ConferenceGateway;
import com.bulatmain.conference.application.port.gateway.EventPublisher;
import com.bulatmain.conference.application.port.gateway.SpeakerGateway;
import com.bulatmain.conference.application.port.gateway.TalkGateway;
import com.bulatmain.conference.application.port.gateway.exception.GatewayException;
import com.bulatmain.conference.application.port.request.RegisterTalkRequest;
import com.bulatmain.conference.application.usecase.RegisterTalkUC;
import com.bulatmain.conference.application.usecase.exception.NoSuchConferenceException;
import com.bulatmain.conference.domain.conference.exception.TalkAlreadyExistsException;
import com.bulatmain.conference.domain.conference.entity.Conference;
import com.bulatmain.conference.domain.speaker.entity.Speaker;
import com.bulatmain.conference.domain.talk.entity.Talk;
import com.bulatmain.conference.domain.talk.exception.SpeakerAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegisterTalkUCImpl implements RegisterTalkUC {
    private final TalkMapper mapper;
    private final ConferenceFabric conferenceFabric;
    private final ConferenceGateway conferenceGateway;
    private final TalkGateway talkGateway;
    private final SpeakerGateway speakerGateway;
    private final TalkFabric talkFabric;
    private final SpeakerFabric speakerFabric;
    private final EventPublisher eventPublisher;

    @Override
    @Transactional
    public String execute(RegisterTalkRequest request)
            throws  NoSuchConferenceException,
                    TalkAlreadyExistsException,
                    SpeakerAlreadyExistsException,
                    GatewayException {
        var conf = findConference(request.getConferenceId());
        var talk = addTalk(conf, request);
        var speaker = speakerFabric.create(speakerCreateDtoFromReq(request));
        talk.addSpeaker(speaker);
        var speakerCreateDto = speakerCreateDtoFromReq(request);
        createSpeakerIfNotExists(talk.id.get(), speakerCreateDto);
        return talk.id.get();
    }   

    private Conference findConference(String conferenceId)
            throws NoSuchConferenceException, GatewayException {
        var confOpt = conferenceGateway.findById(conferenceId);
        if (confOpt.isEmpty()) {
            log.debug("Error: there are no conference with given id {}", conferenceId);
            throw new NoSuchConferenceException("Error: there are no conference with given id");
        }
        return conferenceFabric.restore(confOpt.get());
    }

    private Talk addTalk(Conference conf, RegisterTalkRequest request)
            throws TalkAlreadyExistsException, GatewayException {
        var talkCreateDto = talkCreateDtoFromReq(request);
        var talk = talkFabric.create(talkCreateDto);
        conf.addTalk(talk);
        talkCreateDto.setId(talk.id.get());
        conferenceGateway.addTalk(talkCreateDto);
        log.info("Talk {} created", talk.id.get());
        eventPublisher.publish(new TalkRegisteredEvent(talk.id.get()));
        return talk;
    }

    private void createSpeakerIfNotExists(String talkId, SpeakerCreateDTO speakerCreateDto) throws GatewayException {
        var speakerDtoOpt = speakerGateway.findById(speakerCreateDto.getId());
        if (speakerDtoOpt.isEmpty()) {
            var dto = talkGateway.addSpeaker(talkId, speakerCreateDto);
            log.info("Speaker {} created", dto.getId());
            eventPublisher.publish(new SpeakerRegisteredEvent(dto.getId()));
        }
    }

    private TalkCreateDTO talkCreateDtoFromReq(RegisterTalkRequest request) {
        return TalkCreateDTO.builder()
                .conferenceId(request.getConferenceId())
                .name(request.getName())
                .speakerIds(Set.of(request.getSpeakerId()))
                .build();
    }

    private SpeakerCreateDTO speakerCreateDtoFromReq(RegisterTalkRequest request) {
        return SpeakerCreateDTO.builder()
                .id(request.getSpeakerId())
                .build();

    }

}
