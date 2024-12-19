package com.bulatmain.conference.application.model.fabric.conference;

import com.bulatmain.conference.application.model.dto.conference.ConferenceIdDTO;
import com.bulatmain.conference.application.model.fabric.Fabric;
import com.bulatmain.conference.domain.conference.value.ConferenceId;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ConferenceIdFabric implements Fabric<ConferenceId, ConferenceIdDTO> {
    @Override
    public ConferenceId create(ConferenceIdDTO dto) {
        return () -> String.valueOf(Objects.hash(dto.getOrganizerId(), dto.getConferenceName()));
    }

    public ConferenceId create(String name, String orgId) {
        return create(
                ConferenceIdDTO.builder()
                        .conferenceName(name)
                        .organizerId(orgId)
                        .build()
        );
    }

    @Override
    public ConferenceId restore(ConferenceIdDTO dto) {
        return dto::getId;
    }

    public ConferenceId restore(String id) {
        return restore(
                ConferenceIdDTO.builder()
                        .id(id)
                        .build()
        );
    }

}
