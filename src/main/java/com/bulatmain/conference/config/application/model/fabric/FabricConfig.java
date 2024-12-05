package com.bulatmain.conference.config.application.model.fabric;

import com.bulatmain.conference.application.model.dto.map.ConferenceMapper;
import com.bulatmain.conference.application.model.dto.map.OrganizerMapper;
import com.bulatmain.conference.application.model.fabric.conference.ConferenceFabric;
import com.bulatmain.conference.application.model.fabric.organizer.OrganizerFabric;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FabricConfig {
    @Bean
    public ConferenceFabric conferenceFabric(ConferenceMapper conferenceMapper) {
        return new ConferenceFabric(conferenceMapper);
    }

    @Bean
    public OrganizerFabric organizerFabric(OrganizerMapper organizerMapper) {
        return new OrganizerFabric(organizerMapper);
    }
}
