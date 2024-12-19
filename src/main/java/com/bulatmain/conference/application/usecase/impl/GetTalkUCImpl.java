package com.bulatmain.conference.application.usecase.impl;

import com.bulatmain.conference.application.model.dto.talk.TalkDTO;
import com.bulatmain.conference.application.port.gateway.TalkGateway;
import com.bulatmain.conference.application.port.gateway.exception.GatewayException;
import com.bulatmain.conference.application.port.request.GetTalkRequest;
import com.bulatmain.conference.application.usecase.GetTalkUC;
import com.bulatmain.conference.application.usecase.exception.NoSuchTalkException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetTalkUCImpl implements GetTalkUC {
    private final TalkGateway talkGateway;
    @Override
    public TalkDTO execute(GetTalkRequest request)
            throws GatewayException, NoSuchTalkException {
        var talkOpt = talkGateway.findById(request.getId());
        if (talkOpt.isEmpty()) {
            throw new NoSuchTalkException(
                    String.format(
                            "Error: no talk with id %s",
                            request.getId()
                    )
            );
        }
        return talkOpt.get();
    }
}
