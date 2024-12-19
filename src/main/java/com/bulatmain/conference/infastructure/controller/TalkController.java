package com.bulatmain.conference.infastructure.controller;

import com.bulatmain.conference.application.port.gateway.exception.GatewayException;
import com.bulatmain.conference.application.port.request.RegisterConferenceRequest;
import com.bulatmain.conference.application.port.request.RegisterTalkRequest;
import com.bulatmain.conference.application.usecase.RegisterConferenceUC;
import com.bulatmain.conference.application.usecase.RegisterTalkUC;
import com.bulatmain.conference.application.usecase.exception.NoSuchConferenceException;
import com.bulatmain.conference.domain.conference.exception.TalkAlreadyExistsException;
import com.bulatmain.conference.domain.organizer.exception.ConferenceAlreadyExistsException;
import com.bulatmain.conference.domain.talk.exception.SpeakerAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(path = "api/talk")
@RequiredArgsConstructor
public class TalkController {
    @Autowired
    private final RegisterTalkUC registerTalkUC;

    @PostMapping("/register")
    public ResponseEntity<String> registerConference(@RequestBody RegisterTalkRequest request) {
        try {
            var id = registerTalkUC.execute(request);
            return new ResponseEntity<>(id, HttpStatus.CREATED);
        } catch (NoSuchConferenceException |
                 TalkAlreadyExistsException |
                 SpeakerAlreadyExistsException e) {
            log.debug(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
