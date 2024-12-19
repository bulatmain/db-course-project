package com.bulatmain.conference.domain.conference.entity;

import com.bulatmain.conference.domain.conference.exception.TalkAlreadyExistsException;
import com.bulatmain.conference.domain.conference.value.ConferenceId;
import com.bulatmain.conference.domain.conference.value.ConferenceName;
import com.bulatmain.conference.domain.talk.entity.Talk;
import lombok.Getter;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

public class Conference {
    @Getter
    public final ConferenceId id;
    @Getter
    protected ConferenceName name;
    @Getter
    protected Collection<Talk> talks;
    // TODO: implement
//    protected Status status;
//    protected ConferenceDetail detail;

    public Conference(ConferenceId id, ConferenceName name) {
        this.id = id;
        this.name = name;
        this.talks = new HashSet<>();
    }

    public void setTalks(Collection<Talk> talks) {
        this.talks = Objects.requireNonNullElseGet(talks, HashSet::new);
    }

    public void addTalk(Talk talk) throws TalkAlreadyExistsException {
        Objects.requireNonNull(talk);
        if (hasTalk(talk)) {
            throw new TalkAlreadyExistsException(
                    String.format(
                            "Error: talk with id %s already exists in conference %s",
                            talk.id.get(),
                            id.get()
                    )
            );
        }
        talks.add(talk);
    }

    public boolean hasTalk(Talk talk) {
        Objects.requireNonNull(talk);
        return talks.stream().anyMatch(t ->
                Objects.equals(t.id, talk.id));
    }

}
