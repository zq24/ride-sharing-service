package com.luv2code.springsecurity.demo.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.luv2code.springsecurity.demo.user.UserCrm;

public class FieldsValueMatchValidator implements ConstraintValidator<FieldsValueMatch, UserCrm> {

	// key-value pair
	private String first;
	private String second;
	private String message;

	@Override
	public void initialize(FieldsValueMatch constraintAnnotation) {
		first = constraintAnnotation.first();
		second = constraintAnnotation.second();
		message = constraintAnnotation.message();
		System.out.println("First: " + first);
		System.out.println("Second: " + second);
		System.out.println("Message: " + message);
	}

	@Override
	public boolean isValid(UserCrm value, ConstraintValidatorContext context) {
		String password = value.getPassword();
		String confirmedPassword = value.getConfirmedPassword();
		System.out.println("Template: " + context.getDefaultConstraintMessageTemplate());
		if ((password == null && confirmedPassword == null) || password.equals(confirmedPassword)) {
			return true;
		} else {
			context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
				.addPropertyNode(first)
				.addConstraintViolation();
			return false;
		}
	}
}