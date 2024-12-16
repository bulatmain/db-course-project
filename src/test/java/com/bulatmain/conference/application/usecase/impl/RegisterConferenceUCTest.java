package com.bulatmain.conference.application.usecase.impl;

import com.bulatmain.conference.application.model.dto.conference.ConferenceCreateDTO;
import com.bulatmain.conference.application.model.dto.map.ConferenceMapperImpl;
import com.bulatmain.conference.application.model.dto.map.OrganizerMapperImpl;
import com.bulatmain.conference.application.model.dto.organizer.OrganizerCreateDTO;
import com.bulatmain.conference.application.model.fabric.conference.ConferenceFabric;
import com.bulatmain.conference.application.port.event.ConferenceRegisteredEvent;
import com.bulatmain.conference.application.port.event.OrganizerRegisteredEvent;
import com.bulatmain.conference.application.port.gateway.*;
import com.bulatmain.conference.application.port.request.RegisterConferenceRequest;
import com.bulatmain.conference.application.usecase.exception.ConferenceAlreadyExistsException;
import com.bulatmain.conference.config.application.model.fabric.FabricConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;

@SpringBootTest(classes = {
        ConferenceMapperImpl.class,
        OrganizerMapperImpl.class,
        FabricConfig.class
})
public class RegisterConferenceUCTest {
    private RegisterConferenceUCImpl registerConferenceUC;

    private ConferenceGatewayTestImpl conferenceGateway;
    private OrganizerGatewayTestImpl organizerGateway;
    @Autowired
    private ConferenceFabric conferenceFabric;

    private EventPublisherTestImpl eventPublisher;

    @BeforeEach
    public void initFields() {
        conferenceGateway = new ConferenceGatewayTestImpl();
        organizerGateway = new OrganizerGatewayTestImpl();
        eventPublisher = new EventPublisherTestImpl();
        registerConferenceUC = new RegisterConferenceUCImpl(
                conferenceGateway,
                organizerGateway,
                conferenceFabric,
                eventPublisher
        );
    }


    @Test
    public void RegisterNewConferenceWithNewOrganizerOK() {
        var request = RegisterConferenceRequest.builder()
                .organizerId("org@gmail.com")
                .conferenceName("Big Conf!")
                .build();

        try {
            var id = registerConferenceUC.execute(request);
            Assertions.assertEquals(id, ConferenceGatewayTestImpl.getId(
                    request.getOrganizerId(),
                    request.getConferenceName()
            ));
            Assertions.assertTrue(
                    organizerGateway.findById(
                            request.getOrganizerId()
                    ).isPresent()
            );
            Assertions.assertTrue(
                    conferenceGateway.findByOrganizerIdAndName(
                            request.getOrganizerId(),
                            request.getConferenceName()
                    ).isPresent()
            );
            Assertions.assertTrue(eventPublisher.containsEvent(OrganizerRegisteredEvent.class, 1));
            Assertions.assertTrue(eventPublisher.containsEvent(ConferenceRegisteredEvent.class, 1));

        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    public void RegisterNewConferenceWithExistingOrganizerOK() {
        var request = RegisterConferenceRequest.builder()
                .organizerId("org@gmail.com")
                .conferenceName("Big Conf!")
                .build();

        organizerGateway.save(
                OrganizerCreateDTO.builder()
                        .id(request.getOrganizerId())
                        .build()
        );

        try {
            var id = registerConferenceUC.execute(request);
            Assertions.assertEquals(id, ConferenceGatewayTestImpl.getId(
                    request.getOrganizerId(),
                    request.getConferenceName()
            ));
            Assertions.assertTrue(
                    organizerGateway.findById(
                            request.getOrganizerId()
                    ).isPresent()
            );
            Assertions.assertTrue(
                    conferenceGateway.findByOrganizerIdAndName(
                            request.getOrganizerId(),
                            request.getConferenceName()
                    ).isPresent()
            );
            Assertions.assertTrue(eventPublisher.containsEvent(OrganizerRegisteredEvent.class, 0));
            Assertions.assertTrue(eventPublisher.containsEvent(ConferenceRegisteredEvent.class, 1));

        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    public void RegisterExistingConferenceException() {
        var request = RegisterConferenceRequest.builder()
                .organizerId("org@gmail.com")
                .conferenceName("Big Conf!")
                .build();

        organizerGateway.save(
                OrganizerCreateDTO.builder()
                        .id(request.getOrganizerId())
                        .build()
        );
        conferenceGateway.save(
                ConferenceCreateDTO.builder()
                        .organizerId(request.getOrganizerId())
                        .conferenceName(request.getConferenceName())
                        .build()
        );

        try {
            var id = registerConferenceUC.execute(request);
            Assertions.fail();

        } catch (ConferenceAlreadyExistsException e) {
            Assertions.assertTrue(
                    organizerGateway.findById(
                            request.getOrganizerId()
                    ).isPresent()
            );
            Assertions.assertTrue(
                    conferenceGateway.findByOrganizerIdAndName(
                            request.getOrganizerId(),
                            request.getConferenceName()
                    ).isPresent()
            );
            Assertions.assertTrue(eventPublisher.containsEvent(OrganizerRegisteredEvent.class, 0));
            Assertions.assertTrue(eventPublisher.containsEvent(ConferenceRegisteredEvent.class, 0));
        } catch (Exception e) {
            Assertions.fail();
        }
    }

}
