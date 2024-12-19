package com.bulatmain.conference.domain.organizer.entity;

import com.bulatmain.conference.domain.organizer.exception.ConferenceAlreadyExistsException;
import com.bulatmain.conference.domain.conference.entity.Conference;
import com.bulatmain.conference.domain.organizer.value.OrganizerId;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

public class Organizer {
    @Getter
    public final OrganizerId id;
    @Getter
    public Collection<Conference> conferences;

    public Organizer(OrganizerId id) {
        this.id = id;
        this.conferences = new HashSet<>();
    }

    public void setConferences(Collection<Conference> conferences) {
        this.conferences = Objects.requireNonNullElseGet(conferences, HashSet::new);
    }

    public void addConference(Conference conference)
        throws ConferenceAlreadyExistsException {
        Objects.requireNonNull(conference);
        if (hasConference(conference)) {
            throw new ConferenceAlreadyExistsException(
                    String.format("Error: conference with id %s already exists", conference.id)
            );
        }
        conferences.add(conference);
    }

    public boolean hasConference(Conference conference) {
        Objects.requireNonNull(conference);
        return conferences.stream().anyMatch(conf ->
                Objects.equals(conf.id, conference.id));
    }
}
