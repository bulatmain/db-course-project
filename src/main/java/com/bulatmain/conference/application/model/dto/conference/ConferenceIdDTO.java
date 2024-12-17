package com.bulatmain.conference.application.model.dto.conference;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConferenceIdDTO {
    String id;
    String organizerId;
    String conferenceName;
}
