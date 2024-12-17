package com.bulatmain.conference.application.usecase.impl;

import com.bulatmain.conference.application.port.gateway.ConferenceGateway;
import com.bulatmain.conference.application.port.gateway.EventPublisher;
import com.bulatmain.conference.application.port.gateway.exception.GatewayException;
import com.bulatmain.conference.application.port.request.RegisterTalkRequest;
import com.bulatmain.conference.application.usecase.RegisterTalkUC;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
public class RegisterTalkUCImpl implements RegisterTalkUC {
    private final ConferenceGateway conferenceGateway;
    private final TalkGateway talkGateway;
    private final SpeakerGateway speakerGateway;
    private final TalkFabric talkFabric;
    private final SpeakerFabric speakerFabric;
    private final EventPublisher eventPublisher;

    @Override
    @Transactional
    public String execute(RegisterTalkRequest request) throws GatewayException {
        // 1. Check if given conference exists
        checkSuchConferenceExists(request.getConferenceId());
        // 2. Check if given talk does not exist
        checkNoSuchTalkExists(request.getConferenceId(), request.getName());
        // 3. Create new talk
        var talkId = saveTalk(request);
        // 4. Create new speaker
        saveSpeaker(request);
        // 5. return created talk id
        return talkId;
    }

    private void checkSuchConferenceExists(String conferenceId) {
        var confOpt = conferenceGateway.findById(conferenceId);
        if (confOpt.isEmpty()) {
            log.debug("Error: there are no conference with given id {}", conferenceId);
            throw new NoSuchConferenceException("Error: there are no conference with given id");
        }
    }

    private void checkNoSuchTalkExists(String conferenceId, String name) {
        var talkOpt = talkGateway.findByConfIdAndName(
                conferenceId,
                name
        );
        if (talkOpt.isPresent()) {
            log.debug("Error: talk with given conference {} and name {} already exists", conferenceId, name);
            throw new TalkAlreadyExistsException("Error: talk with given id already exists");
        }
    }

    private String saveTalk(RegisterTalkRequest request) {
        var talk = talkFabric.build(request);
        var talkDTO = talkGateway.save(TalkCreateDTO.of(talk));
        var id = talkDTO.getId();
        log.info("Talk {} created", id);
        eventPublisher.publish(new TalkRegisteredEvent(id));
        return id;
    }

    private String saveSpeaker(RegisterTalkRequest request) {
        var speaker = speakerFabric.build(request);
        var speakerDTO = speakerGateway.save(SpeakerCreateDTO.of(speaker));
        var id = speakerDTO.getId();
        log.info("Speaker {} created", id);
        eventPublisher.publish(new SpeakerRegisteredEvent(id));
        return id;
    }

}
