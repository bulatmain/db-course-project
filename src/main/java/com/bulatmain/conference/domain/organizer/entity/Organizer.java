package com.bulatmain.conference.domain.organizer.entity;

import com.bulatmain.conference.domain.organizer.value.OrganizerId;
import lombok.Getter;

public class Organizer {
    @Getter
    protected final OrganizerId id;

    public Organizer(OrganizerId id) {
        this.id = id;
    }
}
