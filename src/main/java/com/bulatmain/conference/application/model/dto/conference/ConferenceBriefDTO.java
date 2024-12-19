package com.bulatmain.conference.application.model.dto.conference;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConferenceBriefDTO {
    String id;
    String organizerId;
    String conferenceName;
}
