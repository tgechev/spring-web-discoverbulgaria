package com.gechev.discoverbulgaria.events;

import org.springframework.context.ApplicationEvent;

public class PoiEvent extends ApplicationEvent {

    public PoiEvent(Object source) {
        super(source);
    }
}
