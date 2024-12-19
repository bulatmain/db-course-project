package com.bulatmain.conference.application.usecase.impl;

import com.bulatmain.conference.application.model.dto.conference.ConferenceDTO;
import com.bulatmain.conference.application.port.gateway.ConferenceGateway;
import com.bulatmain.conference.application.port.gateway.exception.GatewayException;
import com.bulatmain.conference.application.port.request.GetConferenceRequest;
import com.bulatmain.conference.application.usecase.GetConferenceUC;
import com.bulatmain.conference.application.usecase.exception.NoSuchConferenceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetConferenceUCImpl implements GetConferenceUC {
    private final ConferenceGateway conferenceGateway;

    @Override
    public ConferenceDTO execute(GetConferenceRequest request)
            throws GatewayException, NoSuchConferenceException {
        var confOpt = conferenceGateway.findById(request.getConferenceId());
        if (confOpt.isEmpty()) {
            throw new NoSuchConferenceException(
                    String.format(
                            "Error: there is no conference with id %s",
                            request.getConferenceId())
            );
        }
        return confOpt.get();
    }
}
