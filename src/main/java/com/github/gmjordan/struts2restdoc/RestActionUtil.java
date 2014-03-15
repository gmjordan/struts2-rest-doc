package com.github.gmjordan.struts2restdoc;

import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.reflections.ReflectionUtils;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import com.github.gmjordan.struts2restdoc.annotation.RestAction;
import com.github.gmjordan.struts2restdoc.annotation.RestErrors;
import com.github.gmjordan.struts2restdoc.annotation.RestHeaders;
import com.github.gmjordan.struts2restdoc.annotation.RestMethod;
import com.github.gmjordan.struts2restdoc.annotation.RestObject;
import com.github.gmjordan.struts2restdoc.annotation.RestParams;
import com.github.gmjordan.struts2restdoc.annotation.RestResponseObjects;
import com.github.gmjordan.struts2restdoc.model.RestActionDoc;
import com.github.gmjordan.struts2restdoc.model.RestBodyObjectDoc;
import com.github.gmjordan.struts2restdoc.model.RestDoc;
import com.github.gmjordan.struts2restdoc.model.RestErrorDoc;
import com.github.gmjordan.struts2restdoc.model.RestHeaderDoc;
import com.github.gmjordan.struts2restdoc.model.RestMethodDoc;
import com.github.gmjordan.struts2restdoc.model.RestObjectDoc;
import com.github.gmjordan.struts2restdoc.model.RestParamDoc;
import com.github.gmjordan.struts2restdoc.model.RestResponseObjectDoc;
import com.github.gmjordan.struts2restdoc.model.util.RestParamType;

public class RestActionUtil {

	public static final String UNDEFINED = "undefined";

	public static final String WILDCARD = "wildcard";

	private static Reflections reflections = null;

	public static RestDoc getApiDoc(String version, String basePath, List<String> packages) {
		Set<URL> urls = new HashSet<URL>();
		FilterBuilder filter = new FilterBuilder();

		for (String pkg : packages) {
			urls.addAll(ClasspathHelper.forPackage(pkg));
			filter.includePackage(pkg);
		}
		reflections = new Reflections(new ConfigurationBuilder()
				.filterInputsBy(filter)
				.setUrls(urls)
				);

		RestDoc restDoc = new RestDoc(version, basePath);

		restDoc.setApis(getRestDocs(reflections.getTypesAnnotatedWith(RestAction.class)));
		restDoc.setObjects(getRestObjectDocs(reflections.getTypesAnnotatedWith(RestObject.class)));

		return restDoc;
	}

	public static Set<RestActionDoc> getRestDocs(Set<Class<?>> classes) {
		Set<RestActionDoc> restActionDocs = new TreeSet<RestActionDoc>();
		for (Class<?> controller : classes) {
			RestActionDoc restActionDoc = RestActionDoc.buildFromAnnotation(controller.getAnnotation(RestAction.class));

			restActionDoc.setMethods(getRestMethodDocs(controller));
			restActionDocs.add(restActionDoc);
		}
		return restActionDocs;
	}

	public static Set<RestObjectDoc> getRestObjectDocs(Set<Class<?>> classes) {
		Set<RestObjectDoc> pojoDocs = new TreeSet<RestObjectDoc>();
		for (Class<?> pojo : classes) {
			RestObject annotation = pojo.getAnnotation(RestObject.class);
			RestObjectDoc pojoDoc = RestObjectDoc.buildFromAnnotation(annotation, pojo);
			if (annotation.show()) {
				pojoDocs.add(pojoDoc);
			}
		}

		return pojoDocs;
	}

	private static List<RestMethodDoc> getRestMethodDocs(Class<?> controller) {
		List<RestMethodDoc> apiMethodDocs = new ArrayList<RestMethodDoc>();
		Method[] methods = controller.getMethods();
		for (Method method : methods) {
			if (method.isAnnotationPresent(RestMethod.class)) {
				RestMethodDoc apiMethodDoc = RestMethodDoc.buildFromAnnotation(method.getAnnotation(RestMethod.class));

				if (method.isAnnotationPresent(RestHeaders.class)) {
					apiMethodDoc.setHeaders(RestHeaderDoc.buildFromAnnotation(method.getAnnotation(RestHeaders.class)));
				}

				if (method.isAnnotationPresent(RestParams.class)) {
					apiMethodDoc.setPathparameters(RestParamDoc.buildFromAnnotation(method.getAnnotation(RestParams.class), RestParamType.PATH));
					apiMethodDoc.setQueryparameters(RestParamDoc.buildFromAnnotation(method.getAnnotation(RestParams.class), RestParamType.QUERY));
					apiMethodDoc.setPostparameters(RestParamDoc.buildFromAnnotation(method.getAnnotation(RestParams.class), RestParamType.POST));
				}

				apiMethodDoc.setBodyobject(RestBodyObjectDoc.buildFromAnnotation(method));

				if (method.isAnnotationPresent(RestResponseObjects.class)) {
					apiMethodDoc.setResponse(RestResponseObjectDoc.buildFromAnnotation(method.getAnnotation(RestResponseObjects.class)));
				}

				if (method.isAnnotationPresent(RestErrors.class)) {
					apiMethodDoc.setApierrors(RestErrorDoc.buildFromAnnotation(method.getAnnotation(RestErrors.class)));
				}

				apiMethodDocs.add(apiMethodDoc);
			}

		}
		return apiMethodDocs;
	}

	public static String getObjectNameFromAnnotatedClass(Class<?> clazz) {
		Class<?> annotatedClass = ReflectionUtils.forName(clazz.getName());
		if (annotatedClass.isAnnotationPresent(RestObject.class)) {
			return annotatedClass.getAnnotation(RestObject.class).name();
		}
		return clazz.getSimpleName().toLowerCase();
	}

	public static String getClassType(Class<?> clazz, String fieldname) throws NoSuchFieldException, SecurityException {
		Class<?> annotatedClass = ReflectionUtils.forName(clazz.getName());

		return annotatedClass.getDeclaredField(fieldname).getType().getName();

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

}
