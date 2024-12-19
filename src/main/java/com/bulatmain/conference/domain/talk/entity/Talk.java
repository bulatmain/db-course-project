package com.bulatmain.conference.domain.talk.entity;

import com.bulatmain.conference.domain.listener.entity.Listener;
import com.bulatmain.conference.domain.speaker.entity.Speaker;
import com.bulatmain.conference.domain.talk.exception.SpeakerAlreadyExistsException;
import com.bulatmain.conference.domain.talk.value.TalkId;
import com.bulatmain.conference.domain.talk.value.TalkName;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;
import java.util.Objects;

public class Talk {
    @Getter
    public final TalkId id;
    @Getter
    protected TalkName name;
    @Getter
    protected Collection<Speaker> speakers;
    @Getter
    protected Collection<Listener> listeners;

    public Talk(TalkId id, TalkName name) {
        this.id = id;
        this.name = name;
        this.speakers = new HashSet<>();
        this.listeners = new HashSet<>();
    }

    public void setSpeakers(Collection<Speaker> speakers) {
        this.speakers = Objects.requireNonNullElseGet(speakers, HashSet::new);
    }


    public void setListeners(Collection<Listener> listeners) {
        this.listeners = Objects.requireNonNullElseGet(listeners, HashSet::new);
    }

    public void addSpeaker(Speaker speaker)
            throws SpeakerAlreadyExistsException {
        Objects.requireNonNull(speaker);
        if (hasSpeaker(speaker)) {
            throw new SpeakerAlreadyExistsException(
                    String.format(
                            "Error: speaker with id %s already exists",
                            speaker.getId().get()
                    )
            );
        }
    }

    public boolean hasSpeaker(Speaker speaker) {
        Objects.requireNonNull(speaker);
        return speakers.stream().anyMatch(s ->
                Objects.equals(s.id, speaker.id));
    }
}
