package com.bulatmain.conference.application.model.fabric.talk;

import com.bulatmain.conference.application.model.dto.talk.TalkIdDTO;
import com.bulatmain.conference.application.model.fabric.Fabric;
import com.bulatmain.conference.domain.talk.entity.Talk;
import com.bulatmain.conference.domain.talk.value.TalkId;
import org.springframework.stereotype.Service;

@Service
public class TalkIdFabric implements Fabric<TalkId, TalkIdDTO> {
    @Override
    public TalkId create(TalkIdDTO dto) {
        return () -> dto.getConferenceId() + "$" + dto.getTalkName();
    }

    public TalkId create(String conferenceId, String talkName) {
        return create(
                TalkIdDTO.builder()
                        .conferenceId(conferenceId)
                        .talkName(talkName)
                        .build()
        );
    }

    @Override
    public TalkId restore(TalkIdDTO dto) {
        return dto::getId;
    }

    public TalkId restore(String id) {
        return restore(
                TalkIdDTO.builder()
                        .id(id)
                        .build()
        );
    }
}
