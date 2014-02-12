package com.github.gmjordan.struts2restdoc.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target(value = ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RestObject {

	/**
	 * The name of the object, to be referenced by other annotations with an "object" attribute
	 * 
	 * @see ApiBodyObject
	 * @see ApiResponseObject
	 * @return
	 */
	public String name();

	/**
	 * A description of what the object contains or represents
	 * 
	 * @return
	 */
	public String description() default "";

	/**
	 * Whether to build the json documentation for this object or not. Default value is true
	 * 
	 * @return
	 */
	public boolean show() default true;
}
