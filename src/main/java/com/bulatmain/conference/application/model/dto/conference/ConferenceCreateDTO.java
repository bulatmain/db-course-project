package com.bulatmain.conference.application.model.dto.conference;

import com.bulatmain.conference.domain.conference.entity.Conference;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConferenceCreateDTO {
    String id;
    String organizerId;
    String conferenceName;

}
