package com.bulatmain.conference.application.usecase.impl;

import com.bulatmain.conference.application.model.dto.conference.ConferenceCreateDTO;
import com.bulatmain.conference.application.model.dto.map.ConferenceMapper;
import com.bulatmain.conference.application.model.dto.organizer.OrganizerCreateDTO;
import com.bulatmain.conference.application.model.dto.organizer.OrganizerDTO;
import com.bulatmain.conference.application.model.fabric.conference.ConferenceFabric;
import com.bulatmain.conference.application.model.fabric.organizer.OrganizerFabric;
import com.bulatmain.conference.application.port.event.ConferenceRegisteredEvent;
import com.bulatmain.conference.application.port.event.OrganizerRegisteredEvent;
import com.bulatmain.conference.application.port.gateway.ConferenceGateway;
import com.bulatmain.conference.application.port.gateway.EventPublisher;
import com.bulatmain.conference.application.port.gateway.OrganizerGateway;
import com.bulatmain.conference.application.port.gateway.exception.GatewayException;
import com.bulatmain.conference.application.port.request.RegisterConferenceRequest;
import com.bulatmain.conference.application.usecase.RegisterConferenceUC;
import com.bulatmain.conference.domain.organizer.exception.ConferenceAlreadyExistsException;
import com.bulatmain.conference.domain.organizer.entity.Organizer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegisterConferenceUCImpl implements RegisterConferenceUC {
    protected final ConferenceMapper mapper;
    protected final ConferenceFabric conferenceFabric;
    protected final ConferenceGateway conferenceGateway;
    protected final OrganizerFabric organizerFabric;
    protected final OrganizerGateway organizerGateway;
    protected final EventPublisher eventPublisher;

    /**
     * Registers conference
     * @param request
     * @return conference id as String
     * @throws ConferenceAlreadyExistsException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String execute(RegisterConferenceRequest request)
            throws ConferenceAlreadyExistsException, GatewayException {
        var org = createOrganizerIfNotExist(request.getOrganizerId());
        var conf = conferenceFabric.create(request);
        org.addConference(conf);
        var createDTO = ConferenceCreateDTO.builder()
                .id(conf.id.get())
                .organizerId(org.id.get())
                .conferenceName(conf.getName().get())
                .build();
        return saveConference(createDTO);
    }

    /**
     * Checks if there is organizer with given id.
     * <br/>
     * Creates one if not.
     * @param organizerId
     * @return Organizer
     */
    protected Organizer createOrganizerIfNotExist(String organizerId) throws GatewayException {
        var organizerDtoOpt = organizerGateway.findById(organizerId);
        OrganizerDTO dto;
        if (organizerDtoOpt.isEmpty()) {
            dto = organizerGateway.save(
                    OrganizerCreateDTO.builder()
                            .id(organizerId)
                            .build()
            );
            var id = dto.getId();
            log.info("Organizer {} created", id);
            eventPublisher.publish(new OrganizerRegisteredEvent(id));
        } else {
            dto = organizerDtoOpt.get();
        }
        return organizerFabric.restore(dto);
    }

    /**
     * Saves conference by request
     * @param request
     * @return
     */
    protected String saveConference(ConferenceCreateDTO createDTO) throws GatewayException {
        var dto = organizerGateway.addConference(createDTO);
        var id = dto.getId();
        eventPublisher.publish(new ConferenceRegisteredEvent(id));
        log.info("Conference {} created", id);
        return id;
    }
}
