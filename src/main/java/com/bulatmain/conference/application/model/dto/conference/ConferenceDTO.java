package com.bulatmain.conference.application.model.dto.conference;

import lombok.Builder;
import lombok.Data;

import java.util.Collection;

@Data
@Builder
public class ConferenceDTO {
    String id;
    String organizerId;
    String conferenceName;
    Collection<String> talkIds;
}
