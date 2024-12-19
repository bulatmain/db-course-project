package com.bulatmain.conference.application.usecase.impl;

import com.bulatmain.conference.application.model.dto.conference.ConferenceDTO;
import com.bulatmain.conference.application.port.gateway.ConferenceGateway;
import com.bulatmain.conference.application.port.gateway.exception.GatewayException;
import com.bulatmain.conference.application.usecase.GetConferencesUC;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetConferencesUCImpl implements GetConferencesUC {
    private final ConferenceGateway conferenceGateway;

    @Override
    public Collection<ConferenceDTO> execute()
            throws GatewayException {
        return conferenceGateway.getConferences();
    }
}
