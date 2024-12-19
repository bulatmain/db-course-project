package com.bulatmain.conference.application.usecase;

import com.bulatmain.conference.application.model.dto.conference.ConferenceDTO;
import com.bulatmain.conference.application.port.gateway.exception.GatewayException;

import java.util.Collection;

public interface GetConferencesUC {
    Collection<ConferenceDTO> execute() throws GatewayException;
}
