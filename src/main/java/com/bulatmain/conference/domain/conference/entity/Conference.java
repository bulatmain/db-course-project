package com.bulatmain.conference.domain.conference.entity;

import com.bulatmain.conference.domain.conference.value.ConferenceName;
import com.bulatmain.conference.domain.organizer.value.OrganizerId;
import lombok.Getter;

public class Conference {
    @Getter
    protected OrganizerId organizerId;
    @Getter
    protected ConferenceName name;
    // TODO: implement
//    protected Status status;
//    protected ConferenceDetail detail;

    public Conference(ConferenceName name, OrganizerId organizerId) {
        this.name = name;
        this.organizerId = organizerId;
    }

}
