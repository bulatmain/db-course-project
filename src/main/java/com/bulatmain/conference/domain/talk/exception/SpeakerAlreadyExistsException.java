package com.bulatmain.conference.domain.talk.exception;

import com.bulatmain.conference.domain.common.exception.DomainException;
import lombok.experimental.StandardException;

@StandardException
public class SpeakerAlreadyExistsException extends DomainException {}
