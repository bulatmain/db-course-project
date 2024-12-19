package com.bulatmain.conference.application.model.fabric.talk;

import com.bulatmain.conference.application.model.fabric.Fabric;
import com.bulatmain.conference.domain.talk.value.TalkName;
import org.springframework.stereotype.Service;

@Service
public class TalkNameFabric implements Fabric<TalkName, String> {
    @Override
    public TalkName create(String record) {
        return () -> record;
    }

    @Override
    public TalkName restore(String record) {
        return create(record);
    }
}
