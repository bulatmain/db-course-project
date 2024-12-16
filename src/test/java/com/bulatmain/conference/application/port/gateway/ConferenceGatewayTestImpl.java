package com.bulatmain.conference.application.port.gateway;

import com.bulatmain.conference.application.model.dto.conference.ConferenceCreateDTO;
import com.bulatmain.conference.application.model.dto.conference.ConferenceDTO;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
public class ConferenceGatewayTestImpl implements ConferenceGateway{
    public Set<ConferenceDTO> set = new HashSet<>();
    @Override
    public Optional<ConferenceDTO> findByOrganizerIdAndName(String organizerId, String name) {
        var dto = ConferenceDTO.builder()
                .organizerId(organizerId)
                .conferenceName(name)
                .id(getId(organizerId, name))
                .build();
        if (set.contains(dto)) {
            log.debug(
                    "ConferenceGatewayTestImpl: found conference with orgId {} and name {}",
                    organizerId,
                    name
            );
            return Optional.of(dto);
        }
        log.debug(
                "ConferenceGatewayTestImpl: did not found conference with orgId {} and name {}",
                organizerId,
                name
        );
        return Optional.empty();
    }

    @Override
    public ConferenceDTO save(ConferenceCreateDTO cDto) {
        var organizerId = cDto.getOrganizerId();
        var name = cDto.getConferenceName();
        var id = getId(organizerId, name);
        var dto = ConferenceDTO.builder()
                .organizerId(organizerId)
                .conferenceName(name)
                .id(id)
                .build();
        set.add(dto);
        log.debug(
                "ConferenceGatewayTestImpl: saved conference with orgId {} and name {}",
                organizerId,
                name
        );
        return dto;
    }

    public static String getId(String organizerId, String name) {
        return organizerId + "$" + name;
    }
};
