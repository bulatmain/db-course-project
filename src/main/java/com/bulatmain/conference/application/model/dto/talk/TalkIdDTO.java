package com.bulatmain.conference.application.model.dto.talk;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TalkIdDTO {
    String id;
    String conferenceId;
    String talkName;
}
