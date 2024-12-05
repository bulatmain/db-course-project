package com.bulatmain.conference.application.port.event;

import lombok.Getter;

import java.time.Instant;

@Getter
public abstract class Event {

    Instant timeOfOccurence;

    public Event() {
        this.timeOfOccurence = Instant.now();
    }

    public abstract String getRecord();
}
