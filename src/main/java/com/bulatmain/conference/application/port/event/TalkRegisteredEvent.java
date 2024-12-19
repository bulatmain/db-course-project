package com.bulatmain.conference.application.port.event;

public class TalkRegisteredEvent extends Event {
    private final String talkId;

    public TalkRegisteredEvent(String talkId) {
        super();
        this.talkId = talkId;
    }

    @Override
    public String getRecord() {
        return "TalkRegisteredEvent: {" +
                timeOfOccurrence + ", " + talkId +
                "}";
    }
}
