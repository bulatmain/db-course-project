package com.bulatmain.conference.application.usecase;

import com.bulatmain.conference.application.model.dto.conference.ConferenceDTO;
import com.bulatmain.conference.application.port.gateway.exception.GatewayException;
import com.bulatmain.conference.application.port.request.GetConferenceRequest;
import com.bulatmain.conference.application.usecase.exception.NoSuchConferenceException;

public interface GetConferenceUC {
    ConferenceDTO execute(GetConferenceRequest request)
            throws GatewayException, NoSuchConferenceException;
}
