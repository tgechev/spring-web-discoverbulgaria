package com.gechev.discoverbulgaria.validations.annotations;

import com.gechev.discoverbulgaria.validations.validators.PasswordMatchesValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordMatchesValidator.class)
@Documented
public @interface PasswordMatches {
    String message() default "Паролите не съвпадат.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
