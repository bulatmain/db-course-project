package com.bulatmain.conference.application.model.fabric.listener;

import com.bulatmain.conference.application.model.fabric.Fabric;
import com.bulatmain.conference.domain.listener.value.ListenerId;
import org.springframework.stereotype.Service;

@Service
public class ListenerIdFabric implements Fabric<ListenerId, String> {
    @Override
    public ListenerId create(String record) {
        return () -> record;
    }

    @Override
    public ListenerId restore(String record) {
        return create(record);
    }
}
