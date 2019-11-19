package com.gechev.discoverbulgaria.services;

import javax.validation.ConstraintViolation;
import java.util.Set;

public interface ValidationService {

    <E> boolean isValid(E entity);

    <E> Set<ConstraintViolation<E>> violations(E entity);
}
