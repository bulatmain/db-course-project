package com.bulatmain.conference.application.port.gateway;

import com.bulatmain.conference.application.port.event.Event;

public interface EventPublisher {
    void publish(Event event);
}
