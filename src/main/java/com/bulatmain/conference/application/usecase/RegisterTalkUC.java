package com.bulatmain.conference.application.usecase;

import com.bulatmain.conference.application.port.gateway.exception.GatewayException;
import com.bulatmain.conference.application.port.request.RegisterTalkRequest;
import com.bulatmain.conference.application.usecase.exception.NoSuchConferenceException;
import com.bulatmain.conference.application.usecase.exception.TalkAlreadyExistsException;

public interface RegisterTalkUC {
    String execute(RegisterTalkRequest request)
            throws NoSuchConferenceException, TalkAlreadyExistsException, GatewayException;
}
