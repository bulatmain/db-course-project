package com.bulatmain.conference.application.usecase.impl;

import com.bulatmain.conference.application.model.dto.talk.TalkBriefDTO;
import com.bulatmain.conference.application.port.gateway.TalkGateway;
import com.bulatmain.conference.application.port.gateway.exception.GatewayException;
import com.bulatmain.conference.application.usecase.GetTalksUC;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetTalksUCImpl implements GetTalksUC {
    private final TalkGateway talkGateway;
    @Override
    public Collection<TalkBriefDTO> execute() throws GatewayException {
        return talkGateway.getTalks();
    }
}
