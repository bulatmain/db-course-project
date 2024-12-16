package com.bulatmain.conference.application.model.fabric.conference;

import com.bulatmain.conference.domain.conference.value.ConferenceName;

public class ConferenceNameFabric {
    ConferenceName build(String name) {
        return () -> name;
    }
}
