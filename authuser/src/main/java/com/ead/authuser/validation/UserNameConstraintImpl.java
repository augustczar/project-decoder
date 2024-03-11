package com.ead.authuser.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UserNameConstraintImpl implements ConstraintValidator<UserNameConstraint, String> {

	@Override
	public boolean isValid(String userName, ConstraintValidatorContext context) {
		if(userName == null || userName.trim().isEmpty() || userName.contains(" ")){
			return false;
		}
		return true;
	}
	

}
