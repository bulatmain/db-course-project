package com.bulatmain.conference.application.port.event;

public class OrganizerRegisteredEvent extends Event {

    protected final String organizerId;

    public OrganizerRegisteredEvent(String organizerId) {
        super();
        this.organizerId = organizerId;
    }

    @Override
    public String getRecord() {
        return "OrganizerRegisteredEvent: {" +
                timeOfOccurrence + ", " + organizerId +
                "}";
    }
}
