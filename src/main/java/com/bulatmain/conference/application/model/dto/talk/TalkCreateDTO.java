package com.bulatmain.conference.application.model.dto.talk;

import lombok.Builder;
import lombok.Data;

import java.util.Collection;

@Data
@Builder
public class TalkCreateDTO {
    String id;
    String name;
    String conferenceId;
    Collection<String> speakerIds;

}
