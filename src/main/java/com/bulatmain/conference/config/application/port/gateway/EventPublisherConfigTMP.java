package com.bulatmain.conference.config.application.port.gateway;

import com.bulatmain.conference.application.port.event.Event;
import com.bulatmain.conference.application.port.gateway.EventPublisher;
import com.bulatmain.conference.application.port.gateway.exception.GatewayException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventPublisherConfigTMP {
    @Bean
    public EventPublisher eventPublisher() {
        return new EventPublisherImpl();
    }

    @Slf4j
    public static class EventPublisherImpl implements EventPublisher {
        @Override
        public void publish(Event event) throws GatewayException {
            log.info(event.getRecord());
        }
    }
}
