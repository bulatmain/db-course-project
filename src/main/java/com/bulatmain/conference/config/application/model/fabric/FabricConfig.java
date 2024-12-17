package com.bulatmain.conference.config.application.model.fabric;

import com.bulatmain.conference.application.model.dto.map.ConferenceMapper;
import com.bulatmain.conference.application.model.dto.map.OrganizerMapper;
import com.bulatmain.conference.application.model.fabric.conference.ConferenceFabric;
import com.bulatmain.conference.application.model.fabric.conference.ConferenceIdFabric;
import com.bulatmain.conference.application.model.fabric.conference.ConferenceNameFabric;
import com.bulatmain.conference.application.model.fabric.organizer.OrganizerFabric;
import com.bulatmain.conference.application.model.fabric.organizer.OrganizerIdFabric;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FabricConfig {
    @Bean
    public ConferenceFabric conferenceFabric(
            ConferenceMapper conferenceMapper,
            ConferenceIdFabric conferenceIdFabric,
            ConferenceNameFabric conferenceNameFabric,
            OrganizerFabric organizerFabric
    ) {
        return new ConferenceFabric(
                conferenceMapper,
                conferenceIdFabric,
                conferenceNameFabric,
                organizerFabric
        );
    }

    @Bean
    public ConferenceIdFabric conferenceIdFabric() {
        return new ConferenceIdFabric();
    }

    @Bean
    public ConferenceNameFabric conferenceNameFabric() {
        return new ConferenceNameFabric();
    }

    @Bean
    public OrganizerFabric organizerFabric(
            OrganizerMapper organizerMapper,
            OrganizerIdFabric organizerIdFabric
    ) {
        return new OrganizerFabric(
                organizerMapper,
                organizerIdFabric
        );
    }

    @Bean
    public OrganizerIdFabric organizerIdFabric() {
        return new OrganizerIdFabric();
    }
}
