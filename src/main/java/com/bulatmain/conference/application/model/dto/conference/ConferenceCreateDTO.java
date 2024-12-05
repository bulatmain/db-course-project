package com.bulatmain.conference.application.model.dto.conference;

import com.bulatmain.conference.domain.conference.entity.Conference;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConferenceCreateDTO {
    String organizerId;
    String conferenceName;

    public static ConferenceCreateDTO of(Conference conference) {
        return ConferenceCreateDTO.builder()
                .organizerId(conference.getOrganizerId().get())
                .conferenceName(conference.getName().get())
                .build();
    }
}
