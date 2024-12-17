package com.bulatmain.conference.application.usecase;

import com.bulatmain.conference.application.port.gateway.exception.GatewayException;
import com.bulatmain.conference.application.port.request.RegisterTalkRequest;

public interface RegisterTalkUC {
    String execute(RegisterTalkRequest request)
        throws GatewayException;
}
