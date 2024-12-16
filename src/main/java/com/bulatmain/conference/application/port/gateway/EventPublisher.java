package com.bulatmain.conference.application.port.gateway;

import com.bulatmain.conference.application.port.event.Event;
import com.bulatmain.conference.application.port.gateway.exception.GatewayException;

public interface EventPublisher {
    void publish(Event event) throws GatewayException;
}
