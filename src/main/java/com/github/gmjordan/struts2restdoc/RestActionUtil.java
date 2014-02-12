package com.github.gmjordan.struts2restdoc;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

import org.reflections.ReflectionUtils;
import org.reflections.Reflections;

import com.github.gmjordan.struts2restdoc.annotation.RestObject;
import com.github.gmjordan.struts2restdoc.model.RestDoc;

public class RestActionUtil {
	public static final String UNDEFINED = "undefined";

	public static final String WILDCARD = "wildcard";

	private static Reflections reflections = null;

	public static RestDoc getApiDoc(String version, String basePath, List<String> packages) {
		return null;
	}

	public static boolean isMultiple(Method method) {
		if (Collection.class.isAssignableFrom(method.getReturnType()) || method.getReturnType().isArray()) {
			return true;
		}
		return false;
	}

	public static boolean isMultiple(Class<?> clazz) {
		if (Collection.class.isAssignableFrom(clazz) || clazz.isArray()) {
			return true;
		}
		return false;
	}

	public static String getObjectNameFromAnnotatedClass(Class<?> clazz) {
		Class<?> annotatedClass = ReflectionUtils.forName(clazz.getName());
		if (annotatedClass.isAnnotationPresent(RestObject.class)) {
			return annotatedClass.getAnnotation(RestObject.class).name();
		}
		return clazz.getSimpleName().toLowerCase();
	}
}
