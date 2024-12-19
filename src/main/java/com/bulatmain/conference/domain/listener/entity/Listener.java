package com.bulatmain.conference.domain.listener.entity;

import com.bulatmain.conference.domain.listener.value.ListenerId;
import lombok.Getter;

public class Listener {

    @Getter
    public final ListenerId id;

    public Listener(ListenerId id) {
        this.id = id;
    }
}
