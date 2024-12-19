package com.bulatmain.conference.infastructure.controller;

import com.bulatmain.conference.application.model.dto.conference.ConferenceBriefDTO;
import com.bulatmain.conference.application.model.dto.conference.ConferenceDTO;
import com.bulatmain.conference.application.port.gateway.exception.GatewayException;
import com.bulatmain.conference.application.port.request.GetConferenceRequest;
import com.bulatmain.conference.application.port.request.RegisterConferenceRequest;
import com.bulatmain.conference.application.usecase.GetConferenceUC;
import com.bulatmain.conference.application.usecase.GetConferencesUC;
import com.bulatmain.conference.application.usecase.RegisterConferenceUC;
import com.bulatmain.conference.application.usecase.exception.NoSuchConferenceException;
import com.bulatmain.conference.domain.organizer.exception.ConferenceAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Slf4j
@RestController
@RequestMapping(path = "api/conference")
@RequiredArgsConstructor
public class ConferenceController {

    private final RegisterConferenceUC registerConferenceUC;
    private final GetConferencesUC getConferencesUC;
    private final GetConferenceUC getConferenceUC;


    @PostMapping("/register")
    public ResponseEntity<String> registerConference(@RequestBody RegisterConferenceRequest request) {
        try {
            var id = registerConferenceUC.execute(request);
            return new ResponseEntity<>(id, HttpStatus.CREATED);
        } catch (ConferenceAlreadyExistsException e) {
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<Collection<ConferenceBriefDTO>> getConferences() {
        try {
            var conferences = getConferencesUC.execute();
            return new ResponseEntity<>(conferences, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{conferenceId}")
    public ResponseEntity<ConferenceDTO> getConference(@PathVariable("conferenceId") String conferenceId) {
        try {
            var request = GetConferenceRequest.builder()
                    .conferenceId(conferenceId)
                    .build();
            return new ResponseEntity<>(getConferenceUC.execute(request), HttpStatus.OK);
        } catch (NoSuchConferenceException e) {
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (GatewayException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

