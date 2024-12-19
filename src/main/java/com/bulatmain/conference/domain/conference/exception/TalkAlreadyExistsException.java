package com.bulatmain.conference.domain.conference.exception;

import com.bulatmain.conference.application.usecase.exception.UseCaseException;
import com.bulatmain.conference.domain.common.exception.DomainException;
import lombok.experimental.StandardException;

@StandardException
public class TalkAlreadyExistsException extends DomainException {}
