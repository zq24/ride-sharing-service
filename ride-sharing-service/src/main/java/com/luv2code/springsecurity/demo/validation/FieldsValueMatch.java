package com.luv2code.springsecurity.demo.validation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy=FieldsValueMatchValidator.class)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RUNTIME)
public @interface FieldsValueMatch {
	
	public String message() default "field does not match";
	
	public Class<?>[] groups() default{};
	
	public Class<? extends Payload>[] payload() default{};
	
	String first();
	String second();
	
	@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
	@Retention(RUNTIME)
	@interface List {
		FieldsValueMatch[] value();
	}
}
