package com.bulatmain.conference.domain.speaker.entity;

import com.bulatmain.conference.domain.speaker.value.SpeakerId;
import lombok.Getter;

public class Speaker {
    @Getter
    public final SpeakerId id;

    public Speaker(SpeakerId id) {
        this.id = id;
    }
}
