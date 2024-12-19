package com.bulatmain.conference.application.usecase;

import com.bulatmain.conference.application.model.dto.conference.ConferenceBriefDTO;
import com.bulatmain.conference.application.model.dto.conference.ConferenceDTO;
import com.bulatmain.conference.application.port.gateway.exception.GatewayException;

import java.util.Collection;

public interface GetConferencesUC {
    Collection<ConferenceBriefDTO> execute() throws GatewayException;
}
