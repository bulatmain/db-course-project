package com.bulatmain.conference.application.port.event;

import lombok.Getter;

import java.time.Instant;

@Getter
public abstract class Event {

    Instant timeOfOccurrence;

    public Event() {
        this.timeOfOccurrence = Instant.now();
    }

    public abstract String getRecord();
}
