package com.bulatmain.conference.application.model.dto.organizer;

import lombok.Builder;
import lombok.Data;

import java.util.Collection;

@Data
@Builder
public class OrganizerDTO {
    String id;
    Collection<String> conferenceIds;
}
