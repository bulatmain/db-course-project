package com.bulatmain.conference.application.model.fabric.organizer;

import com.bulatmain.conference.domain.organizer.value.OrganizerId;

public class OrganizerIdFabric {
    public OrganizerId build(String record) {
        return () -> record;
    }
}
