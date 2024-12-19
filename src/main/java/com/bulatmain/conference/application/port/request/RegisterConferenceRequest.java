package com.bulatmain.conference.application.port.request;

import lombok.Builder;
import lombok.Data;

import java.util.Objects;

@Data
public class RegisterConferenceRequest {
    public RegisterConferenceRequest(String conferenceName, String organizerId) {
        Objects.requireNonNull(conferenceName);
        Objects.requireNonNull(organizerId);

        this.conferenceName = conferenceName;
        this.organizerId = organizerId;
    }

    String conferenceName;
    String organizerId;



}
