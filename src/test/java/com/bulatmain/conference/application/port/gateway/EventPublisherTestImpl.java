package com.bulatmain.conference.application.port.gateway;

import com.bulatmain.conference.application.port.event.Event;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class EventPublisherTestImpl implements EventPublisher {
    public Set<Event> events = new HashSet<>();

    @Override
    public void publish(Event event) {
        events.add(event);
        log.debug("EventPublisherTestImpl: {}", event.getRecord());
    }

    public boolean containsEvent(Class<? extends Event> eventClass, int times) {
        return times == events.stream()
                .filter(event -> event.getClass() == eventClass)
                .count();
    }
}
