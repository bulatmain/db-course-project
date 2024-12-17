package com.bulatmain.conference.application.model.dto.talk;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TalkCreateDTO {
    String name;
    String conferenceId;
}
