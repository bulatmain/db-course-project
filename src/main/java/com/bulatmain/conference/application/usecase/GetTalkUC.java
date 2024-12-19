package com.bulatmain.conference.application.usecase;

import com.bulatmain.conference.application.model.dto.talk.TalkDTO;
import com.bulatmain.conference.application.port.gateway.exception.GatewayException;
import com.bulatmain.conference.application.port.request.GetTalkRequest;
import com.bulatmain.conference.application.usecase.exception.NoSuchTalkException;

public interface GetTalkUC {
    TalkDTO execute(GetTalkRequest request)
            throws GatewayException, NoSuchTalkException;
}
