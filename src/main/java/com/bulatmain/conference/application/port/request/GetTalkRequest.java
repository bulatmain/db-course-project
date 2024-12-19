package com.bulatmain.conference.application.port.request;

import lombok.Builder;
import lombok.Data;

import java.util.Objects;

@Data
public class GetTalkRequest {
    public GetTalkRequest(String id) {
        Objects.requireNonNull(id);

        this.id = id;
    }

    String id;
}
