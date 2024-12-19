package com.bulatmain.conference.application.usecase;

import com.bulatmain.conference.application.model.dto.talk.TalkBriefDTO;
import com.bulatmain.conference.application.port.gateway.exception.GatewayException;

import java.util.Collection;

public interface GetTalksUC {
    Collection<TalkBriefDTO> execute() throws GatewayException;
}
