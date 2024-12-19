package com.bulatmain.conference.application.model.fabric.talk;

import com.bulatmain.conference.application.model.dto.map.TalkMapper;
import com.bulatmain.conference.application.model.dto.talk.TalkCreateDTO;
import com.bulatmain.conference.application.model.dto.talk.TalkDTO;
import com.bulatmain.conference.application.model.fabric.Fabric;
import com.bulatmain.conference.application.model.fabric.listener.ListenerFabric;
import com.bulatmain.conference.application.model.fabric.speaker.SpeakerFabric;
import com.bulatmain.conference.application.port.request.RegisterTalkRequest;
import com.bulatmain.conference.domain.listener.entity.Listener;
import com.bulatmain.conference.domain.speaker.entity.Speaker;
import com.bulatmain.conference.domain.talk.entity.Talk;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TalkFabric implements Fabric<Talk, TalkDTO> {
    private final TalkMapper mapper;
    private final TalkIdFabric talkIdFabric;
    private final TalkNameFabric talkNameFabric;
    private final SpeakerFabric speakerFabric;
    private final ListenerFabric listenerFabric;

    public Talk create(TalkCreateDTO dto) {
        return create(mapper.createDtoToDto(dto));
    }

    @Override
    public Talk create(TalkDTO dto) {
        var id = talkIdFabric.create(dto.getConferenceId(), dto.getName());
        var name = talkNameFabric.create(dto.getName());
        return new Talk(id, name);
    }

    @Override
    public Talk restore(TalkDTO dto) {
        var id = talkIdFabric.restore(dto.getId());
        var name = talkNameFabric.restore(dto.getName());
        var speakers = restoreSpeakers(dto.getSpeakerIds());
        var listeners = restoreListeners(dto.getListenerIds());
        var talk = new Talk(id, name);
        talk.setSpeakers(speakers);
        talk.setListeners(listeners);
        return talk;
    }

    public Talk restore(String talkId) {
        return restore(TalkDTO.builder()
                .id(talkId)
                .build());
    }

    private Collection<Speaker> restoreSpeakers(Collection<String> speakerIds) {
        if (speakerIds == null) {
            return null;
        } else {
            return speakerIds.stream()
                    .map(speakerFabric::restore)
                    .collect(Collectors.toSet());
        }
    }

    private Collection<Listener> restoreListeners(Collection<String> listenerIds) {
        if (listenerIds == null) {
            return null;
        } else {
            return listenerIds.stream()
                    .map(listenerFabric::restore)
                    .collect(Collectors.toSet());
        }
    }

}
