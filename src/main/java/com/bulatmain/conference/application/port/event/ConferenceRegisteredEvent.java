package com.bulatmain.conference.application.port.event;

public class ConferenceRegisteredEvent extends Event {
    protected final String conferenceId;

    public ConferenceRegisteredEvent(String conferenceId) {
        super();
        this.conferenceId = conferenceId;
    }


    @Override
    public String getRecord() {
        return "ConferenceRegisteredEvent: {" +
                timeOfOccurrence + ", " + conferenceId +
                "}";
    }
}
