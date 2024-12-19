package com.bulatmain.conference.application.port.request;

import lombok.Builder;
import lombok.Data;

import java.util.Objects;

@Data
public class RegisterTalkRequest {
    String speakerId;

    public RegisterTalkRequest(String speakerId, String conferenceId, String name) {
        Objects.requireNonNull(speakerId);
        Objects.requireNonNull(conferenceId);
        Objects.requireNonNull(name);

        this.speakerId = speakerId;
        this.conferenceId = conferenceId;
        this.name = name;
    }

    String conferenceId;
    String name;

}
