package com.gechev.discoverbulgaria.services.impl;

import com.gechev.discoverbulgaria.services.ValidationService;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@Service
public class ValidationServiceImpl implements ValidationService {

    private final Validator validator;

    public ValidationServiceImpl(Validator validator) {
        this.validator = validator;
    }

    @Override
    public <E> boolean isValid(E entity) {
        return  this.validator.validate(entity).size() == 0;
    }

    @Override
    public <E> Set<ConstraintViolation<E>> violations(E entity) {
        return this.validator.validate(entity);
    }
}
