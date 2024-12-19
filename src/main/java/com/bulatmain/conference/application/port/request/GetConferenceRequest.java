package com.bulatmain.conference.application.port.request;

import lombok.Builder;
import lombok.Data;

import java.util.Objects;

@Data
public class GetConferenceRequest {
    String conferenceId;

    public GetConferenceRequest(String conferenceId) {
        Objects.requireNonNull(conferenceId);

        this.conferenceId = conferenceId;
    }
}
