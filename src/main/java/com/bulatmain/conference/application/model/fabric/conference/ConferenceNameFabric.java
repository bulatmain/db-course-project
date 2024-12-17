package com.bulatmain.conference.application.model.fabric.conference;

import com.bulatmain.conference.application.model.fabric.Fabric;
import com.bulatmain.conference.domain.conference.value.ConferenceName;

public class ConferenceNameFabric implements Fabric<ConferenceName, String> {
    @Override
    public ConferenceName create(String name) {
        return () -> name;
    }

    @Override
    public ConferenceName restore(String record) {
        return create(record);
    }
}
