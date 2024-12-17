package com.bulatmain.conference.domain.conference.entity;

import com.bulatmain.conference.domain.conference.value.ConferenceId;
import com.bulatmain.conference.domain.conference.value.ConferenceName;
import com.bulatmain.conference.domain.organizer.entity.Organizer;
import lombok.Getter;

public class Conference {
    @Getter
    public final ConferenceId id;
    @Getter
    protected final Organizer organizer;
    @Getter
    protected ConferenceName name;
    // TODO: implement
//    protected Status status;
//    protected ConferenceDetail detail;

    public Conference(ConferenceId id, ConferenceName name, Organizer organizer) {
        this.id = id;
        this.name = name;
        this.organizer = organizer;
    }

}
