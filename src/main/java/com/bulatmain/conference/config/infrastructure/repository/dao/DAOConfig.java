package com.bulatmain.conference.config.infrastructure.repository.dao;

import com.bulatmain.conference.application.port.gateway.ConferenceGateway;
import com.bulatmain.conference.application.port.gateway.OrganizerGateway;
import com.bulatmain.conference.infastructure.repository.dao.ConferenceDAO;
import com.bulatmain.conference.infastructure.repository.dao.OrganizerDAO;
import com.bulatmain.conference.infastructure.repository.db.CustomJdbcTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile({ "dev", "prod" })
public class DAOConfig {

    @Bean
    public OrganizerGateway organizerGateway(CustomJdbcTemplate customJdbcTemplate) {
        return new OrganizerDAO(customJdbcTemplate);
    }

    @Bean
    ConferenceGateway conferenceGateway(CustomJdbcTemplate customJdbcTemplate) {
        return new ConferenceDAO(customJdbcTemplate);
    }

}
