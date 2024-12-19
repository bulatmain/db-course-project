package com.bulatmain.conference.application.model.fabric.speaker;

import com.bulatmain.conference.application.model.dto.map.SpeakerMapper;
import com.bulatmain.conference.application.model.dto.speaker.SpeakerCreateDTO;
import com.bulatmain.conference.application.model.dto.speaker.SpeakerDTO;
import com.bulatmain.conference.application.model.fabric.Fabric;
import com.bulatmain.conference.domain.speaker.entity.Speaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SpeakerFabric implements Fabric<Speaker, SpeakerDTO> {
    private final SpeakerMapper mapper;
    private final SpeakerIdFabric speakerIdFabric;

    public Speaker create(SpeakerCreateDTO dto) {
        return create(mapper.createDtoToDto(dto));
    }

    @Override
    public Speaker create(SpeakerDTO dto) {
        var id = speakerIdFabric.create(dto.getId());
        return new Speaker(id);
    }

    public Speaker create(String id) {
        return create(
                SpeakerDTO.builder()
                        .id(id)
                        .build()
        );
    }

    @Override
    public Speaker restore(SpeakerDTO dto) {
        return create(dto);
    }

    public Speaker restore(String id) {
        return create(id);
    }

}
