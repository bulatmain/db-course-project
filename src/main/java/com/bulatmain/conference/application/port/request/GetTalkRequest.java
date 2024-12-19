package com.bulatmain.conference.application.port.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetTalkRequest {
    String id;
}
