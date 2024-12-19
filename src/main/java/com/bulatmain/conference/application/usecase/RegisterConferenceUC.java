package com.bulatmain.conference.application.usecase;

import com.bulatmain.conference.application.port.gateway.exception.GatewayException;
import com.bulatmain.conference.application.port.request.RegisterConferenceRequest;
import com.bulatmain.conference.domain.organizer.exception.ConferenceAlreadyExistsException;

public interface RegisterConferenceUC {
    String execute(RegisterConferenceRequest request)
            throws ConferenceAlreadyExistsException, GatewayException;
}
