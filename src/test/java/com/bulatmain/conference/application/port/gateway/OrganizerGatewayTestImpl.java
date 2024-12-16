package com.bulatmain.conference.application.port.gateway;

import com.bulatmain.conference.application.model.dto.organizer.OrganizerCreateDTO;
import com.bulatmain.conference.application.model.dto.organizer.OrganizerDTO;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
public class OrganizerGatewayTestImpl implements OrganizerGateway {
    public Set<OrganizerDTO> set = new HashSet<>();
    @Override
    public Optional<OrganizerDTO> findById(String id) {
        var dto = OrganizerDTO.builder()
                .id(id)
                .build();
        if (set.contains(dto)) {
            log.debug("OrganizerGatewayTestImpl: found organizer with id {}", id);
            return Optional.of(dto);
        }
        log.debug("OrganizerGatewayTestImpl: did not found organizer with id {}", id);
        return Optional.empty();
    }

    @Override
    public OrganizerDTO save(OrganizerCreateDTO cDto) {
        var dto = OrganizerDTO.builder()
                .id(cDto.getId())
                .build();
        set.add(dto);
        log.debug("OrganizerGatewayTestImpl: added organizer with id {}", cDto.getId());
        return dto;
    }
};
