package com.bulatmain.conference.config.application.usecase;

import com.bulatmain.conference.application.model.fabric.conference.ConferenceFabric;
import com.bulatmain.conference.application.port.gateway.ConferenceGateway;
import com.bulatmain.conference.application.port.gateway.EventPublisher;
import com.bulatmain.conference.application.port.gateway.OrganizerGateway;
import com.bulatmain.conference.application.usecase.RegisterConferenceUC;
import com.bulatmain.conference.application.usecase.impl.RegisterConferenceUCImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class UseCaseConfig {
    @Bean
    public RegisterConferenceUC registerConferenceUC(
            ConferenceGateway conferenceGateway,
            OrganizerGateway organizerGateway,
            ConferenceFabric conferenceFabric,
            EventPublisher eventPublisher
    ) {
        return new RegisterConferenceUCImpl(
                conferenceGateway,
                organizerGateway,
                conferenceFabric,
                eventPublisher
        );
    }

}
