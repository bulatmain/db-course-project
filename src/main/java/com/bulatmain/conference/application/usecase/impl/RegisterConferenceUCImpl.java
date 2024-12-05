package com.bulatmain.conference.application.usecase.impl;

import com.bulatmain.conference.application.model.dto.conference.ConferenceCreateDTO;
import com.bulatmain.conference.application.model.dto.conference.ConferenceDTO;
import com.bulatmain.conference.application.model.dto.organizer.OrganizerCreateDTO;
import com.bulatmain.conference.application.model.dto.organizer.OrganizerDTO;
import com.bulatmain.conference.application.model.fabric.conference.ConferenceFabric;
import com.bulatmain.conference.application.port.event.ConferenceRegisteredEvent;
import com.bulatmain.conference.application.port.gateway.ConferenceGateway;
import com.bulatmain.conference.application.port.gateway.EventPublisher;
import com.bulatmain.conference.application.port.gateway.OrganizerGateway;
import com.bulatmain.conference.application.port.request.RegisterConferenceRequest;
import com.bulatmain.conference.application.usecase.RegisterConferenceUC;
import com.bulatmain.conference.application.usecase.exception.ConferenceAlreadyExistsException;
import com.bulatmain.conference.domain.conference.entity.Conference;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
    public String execute(RegisterConferenceRequest request)
            throws ConferenceAlreadyExistsException {
        log.debug("Request: org {}, name {}", request.getOrganizerId(), request.getConferenceName());
        var organizerId = createOrganizerIfNotExist(request.getOrganizerId());
        checkNoSuchConferenceExist(request.getOrganizerId(), request.getConferenceName());
        var conferenceId = saveConference(request);
        log.info("Conference {} created", conferenceId);
        eventPublisher.publish(new ConferenceRegisteredEvent(conferenceId));
        return conferenceId;
    }

    /**
     * Checks if there is organizer with given id.
     * <br/>
     * Creates one if not.
     * @param organizerId
     * @return Organizer id
     */
    protected String createOrganizerIfNotExist(String organizerId) {
        var organizerDtoOpt = organizerGateway.findById(organizerId);
        if (organizerDtoOpt.isEmpty()) {
            var dto = organizerGateway.save(
                    OrganizerCreateDTO.builder()
                            .id(organizerId)
                            .build()
            );
            var id = dto.getId();
            log.debug("Organizer {} created", id);
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
            throws ConferenceAlreadyExistsException {
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
    protected String saveConference(RegisterConferenceRequest request) {
        var conference = conferenceFabric.build(request);
        var conferenceDTO = conferenceGateway.save(ConferenceCreateDTO.of(conference));
        return conferenceDTO.getId();
    }
}
