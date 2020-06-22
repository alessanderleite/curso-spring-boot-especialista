package com.example.app.validation.constraintvalidation;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.example.app.validation.NotEmptyList;

public class NotEmptyListValidator 
		implements ConstraintValidator<NotEmptyList, List<Object>> {

	@Override
	public boolean isValid(List<Object> list, 
						   ConstraintValidatorContext constraintValidatorContext) {
		return list != null && !list.isEmpty();
	}

	@Override
	public void initialize(NotEmptyList constraintAnnotation) {
	}

}
