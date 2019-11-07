package com.gechev.discoverbulgaria.services.impl;

import com.gechev.discoverbulgaria.services.PoiService;
import com.gechev.discoverbulgaria.services.models.PoiServiceModel;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class PoiServiceImpl implements PoiService {
    @Override
    public Set<PoiServiceModel> findAll() {
        return null;
    }

    @Override
    public PoiServiceModel findByName(String name) {
        return null;
    }
}
