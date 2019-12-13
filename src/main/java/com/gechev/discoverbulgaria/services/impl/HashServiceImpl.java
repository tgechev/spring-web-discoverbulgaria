package com.gechev.discoverbulgaria.services.impl;

import com.gechev.discoverbulgaria.services.HashService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class HashServiceImpl implements HashService {

    private final PasswordEncoder passwordEncoder;

    public HashServiceImpl(PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String hash(String str){
        return passwordEncoder.encode(str);
    }
}
