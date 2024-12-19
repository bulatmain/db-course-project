package com.bulatmain.conference.application.model.fabric.speaker;

import com.bulatmain.conference.application.model.fabric.Fabric;
import com.bulatmain.conference.domain.speaker.value.SpeakerId;
import org.springframework.stereotype.Service;

@Service
public class SpeakerIdFabric implements Fabric<SpeakerId, String> {
    @Override
    public SpeakerId create(String record) {
        return () -> record;
    }

    @Override
    public SpeakerId restore(String record) {
        return create(record);
    }
}
