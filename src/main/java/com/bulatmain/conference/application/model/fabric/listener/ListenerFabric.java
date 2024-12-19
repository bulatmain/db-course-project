package com.bulatmain.conference.application.model.fabric.listener;

import com.bulatmain.conference.application.model.dto.listener.ListenerDTO;
import com.bulatmain.conference.application.model.fabric.Fabric;
import com.bulatmain.conference.domain.listener.entity.Listener;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ListenerFabric implements Fabric<Listener, ListenerDTO> {
    private final ListenerIdFabric speakerIdFabric;

    @Override
    public Listener create(ListenerDTO dto) {
        var id = speakerIdFabric.create(dto.getId());
        return new Listener(id);
    }

    public Listener create(String id) {
        return create(
                ListenerDTO.builder()
                        .id(id)
                        .build()
        );
    }

    @Override
    public Listener restore(ListenerDTO dto) {
        return create(dto);
    }

    public Listener restore(String id) {
        return create(id);
    }

}