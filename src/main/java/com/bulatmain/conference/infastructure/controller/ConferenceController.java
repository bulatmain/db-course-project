package com.bulatmain.conference.infastructure.controller;

import com.bulatmain.conference.application.port.request.RegisterConferenceRequest;
import com.bulatmain.conference.application.usecase.RegisterConferenceUC;
import com.bulatmain.conference.application.usecase.exception.ConferenceAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/conference")
@RequiredArgsConstructor
public class ConferenceController {
    @Autowired
    private final RegisterConferenceUC registerConferenceUC;

    @PostMapping("/register")
    public ResponseEntity<String> registerConference(@RequestBody RegisterConferenceRequest request) {
        try {
            var id = registerConferenceUC.execute(request);
            return new ResponseEntity<>(id, HttpStatus.CREATED);
        } catch (ConferenceAlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
