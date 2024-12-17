package com.bulatmain.conference.application.model.fabric.organizer;

import com.bulatmain.conference.application.model.fabric.Fabric;
import com.bulatmain.conference.domain.organizer.value.OrganizerId;

public class OrganizerIdFabric implements Fabric<OrganizerId, String> {
    @Override
    public OrganizerId create(String record) {
        return () -> record;
    }

    @Override
    public OrganizerId restore(String record) {
        return create(record);
    }
}
