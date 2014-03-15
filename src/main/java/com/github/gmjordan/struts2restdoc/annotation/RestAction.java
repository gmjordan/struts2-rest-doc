package com.github.gmjordan.struts2restdoc.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface RestAction {
	/**
	 * A description of what the API does
	 * 
	 * @return
	 */
	public String description();

	/**
	 * The name of the API
	 * 
	 * @return
	 */
	public String name();

	public String[] actionHeaderList() default {};
}
