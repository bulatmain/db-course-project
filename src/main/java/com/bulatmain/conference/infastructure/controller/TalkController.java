package com.bulatmain.conference.infastructure.controller;

import com.bulatmain.conference.application.model.dto.talk.TalkBriefDTO;
import com.bulatmain.conference.application.port.request.RegisterTalkRequest;
import com.bulatmain.conference.application.usecase.GetTalksUC;
import com.bulatmain.conference.application.usecase.RegisterTalkUC;
import com.bulatmain.conference.application.usecase.exception.NoSuchConferenceException;
import com.bulatmain.conference.domain.conference.exception.TalkAlreadyExistsException;
import com.bulatmain.conference.domain.talk.exception.SpeakerAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Slf4j
@RestController
@RequestMapping(path = "api/talk")
@RequiredArgsConstructor
public class TalkController {
    private final RegisterTalkUC registerTalkUC;
    private final GetTalksUC getTalksUC;

    @PostMapping("/register")
    public ResponseEntity<String> registerTalk(@RequestBody RegisterTalkRequest request) {
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


    @GetMapping
    public ResponseEntity<Collection<TalkBriefDTO>> getConferences() {
        try {
            var talks = getTalksUC.execute();
            return new ResponseEntity<>(talks, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
