package com.github.gmjordan.struts2restdoc.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target(value = ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RestObjectField {

	/**
	 * A drescription of what the field is
	 * 
	 * @return
	 */
	public String description();

	/**
	 * The format pattern for this field
	 * 
	 * @return
	 */
	public String format() default "";

	/**
	 * The allowed values for this field
	 * 
	 * @return
	 */
	public String[] allowedvalues() default {};
}
