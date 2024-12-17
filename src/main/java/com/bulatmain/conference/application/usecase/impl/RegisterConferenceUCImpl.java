package com.bulatmain.conference.application.usecase.impl;

import com.bulatmain.conference.application.model.dto.conference.ConferenceCreateDTO;
import com.bulatmain.conference.application.model.dto.organizer.OrganizerCreateDTO;
import com.bulatmain.conference.application.model.fabric.conference.ConferenceFabric;
import com.bulatmain.conference.application.port.event.ConferenceRegisteredEvent;
import com.bulatmain.conference.application.port.event.OrganizerRegisteredEvent;
import com.bulatmain.conference.application.port.gateway.ConferenceGateway;
import com.bulatmain.conference.application.port.gateway.EventPublisher;
import com.bulatmain.conference.application.port.gateway.OrganizerGateway;
import com.bulatmain.conference.application.port.gateway.exception.GatewayException;
import com.bulatmain.conference.application.port.request.RegisterConferenceRequest;
import com.bulatmain.conference.application.usecase.RegisterConferenceUC;
import com.bulatmain.conference.application.usecase.exception.ConferenceAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
public class RegisterConferenceUCImpl implements RegisterConferenceUC {
    protected final ConferenceGateway conferenceGateway;
    protected final OrganizerGateway organizerGateway;
    protected final ConferenceFabric conferenceFabric;
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
        log.debug("Request: org {}, name {}", request.getOrganizerId(), request.getConferenceName());
        createOrganizerIfNotExist(request.getOrganizerId());
        checkNoSuchConferenceExist(request.getOrganizerId(), request.getConferenceName());
        return saveConference(request);
    }

    /**
     * Checks if there is organizer with given id.
     * <br/>
     * Creates one if not.
     * @param organizerId
     * @return Organizer id
     */
    protected String createOrganizerIfNotExist(String organizerId) throws GatewayException {
        var organizerDtoOpt = organizerGateway.findById(organizerId);
        if (organizerDtoOpt.isEmpty()) {
            var dto = organizerGateway.save(
                    OrganizerCreateDTO.builder()
                            .id(organizerId)
                            .build()
            );
            var id = dto.getId();
            log.info("Organizer {} created", id);
            eventPublisher.publish(new OrganizerRegisteredEvent(id));
            return id;
        }
        return organizerDtoOpt.get().getId();
    }

    /**
     * Throws ConferenceAlreadyExistsException if such conference exists.
     * <br/>
     * Passes otherwise.
     * @param name
     * @throws ConferenceAlreadyExistsException
     */
    protected void checkNoSuchConferenceExist(String organizerId, String name)
            throws ConferenceAlreadyExistsException, GatewayException {
        var confDtoOpt = conferenceGateway.findByOrganizerIdAndName(organizerId, name);
        if (confDtoOpt.isPresent()) {
            log.error("ERROR: conference with given organizer id {} and name {} exists", organizerId, name);
            throw new ConferenceAlreadyExistsException("Error: such conference already exists");
        }
    }

    /**
     * Saves conference by request
     * @param request
     * @return
     */
    protected String saveConference(RegisterConferenceRequest request) throws GatewayException {
        var conference = conferenceFabric.create(request);
        var conferenceDTO = conferenceGateway.save(ConferenceCreateDTO.of(conference));
        var id = conferenceDTO.getId();
        eventPublisher.publish(new ConferenceRegisteredEvent(id));
        log.info("Conference {} created", id);
        return id;
    }
}
