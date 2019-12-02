package com.gechev.discoverbulgaria.services.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseServiceModel {
    private String id;

    protected BaseServiceModel(){
    }
}
