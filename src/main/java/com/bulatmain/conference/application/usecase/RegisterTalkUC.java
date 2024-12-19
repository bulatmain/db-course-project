package com.bulatmain.conference.application.usecase;

import com.bulatmain.conference.application.port.gateway.exception.GatewayException;
import com.bulatmain.conference.application.port.request.RegisterTalkRequest;
import com.bulatmain.conference.application.usecase.exception.NoSuchConferenceException;
import com.bulatmain.conference.domain.conference.exception.TalkAlreadyExistsException;
import com.bulatmain.conference.domain.talk.exception.SpeakerAlreadyExistsException;

public interface RegisterTalkUC {
    String execute(RegisterTalkRequest request)
            throws NoSuchConferenceException, TalkAlreadyExistsException, GatewayException, SpeakerAlreadyExistsException;
}
