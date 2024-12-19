package com.bulatmain.conference.application.port.event;

public class SpeakerRegisteredEvent extends Event {
    private final String speakerId;

    public SpeakerRegisteredEvent(String speakerId) {
        super();
        this.speakerId = speakerId;
    }

    @Override
    public String getRecord() {
        return "SpeakerRegisteredEvent: {" +
                timeOfOccurrence + ", " + speakerId +
                "}";
    }
}
