package com.gechev.discoverbulgaria.validations.validators;

import com.gechev.discoverbulgaria.validations.annotations.PasswordMatches;
import com.gechev.discoverbulgaria.web.models.UserRegisterModel;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {

    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext constraintValidatorContext) {
        UserRegisterModel user = (UserRegisterModel) obj;
        if(user.getPassword() == null || user.getConfirmPassword() == null){
            return false;
        }

        return user.getPassword().equals(user.getConfirmPassword());
    }
}
