package com.gechev.discoverbulgaria.events;

import org.springframework.context.ApplicationEvent;

public class FactEvent extends ApplicationEvent {

    public FactEvent(Object source) {
        super(source);
    }
}
