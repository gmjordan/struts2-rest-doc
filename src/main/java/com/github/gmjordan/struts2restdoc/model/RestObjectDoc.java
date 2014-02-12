package com.github.gmjordan.struts2restdoc.model;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.github.gmjordan.struts2restdoc.annotation.RestObject;
import com.github.gmjordan.struts2restdoc.annotation.RestObjectField;

public class RestObjectDoc implements Comparable<RestObjectDoc> {

	public String jsondocId = UUID.randomUUID().toString();

	private String name;

	private String description;

	private List<RestObjectFieldDoc> fields;

	@SuppressWarnings("rawtypes")
	public static RestObjectDoc buildFromAnnotation(RestObject annotation, Class clazz) {
		List<RestObjectFieldDoc> fieldDocs = new ArrayList<RestObjectFieldDoc>();
		for (Field field : clazz.getDeclaredFields()) {
			if (field.getAnnotation(RestObjectField.class) != null) {
				fieldDocs.add(RestObjectFieldDoc.buildFromAnnotation(field.getAnnotation(RestObjectField.class), field));
			}
		}

		Class<?> c = clazz.getSuperclass();
		if (c != null) {
			if (c.isAnnotationPresent(RestObject.class)) {
				RestObjectDoc objDoc = RestObjectDoc.buildFromAnnotation(c.getAnnotation(RestObject.class), c);
				fieldDocs.addAll(objDoc.getFields());
			}
		}

		return new RestObjectDoc(annotation.name(), annotation.description(), fieldDocs);
	}

	public RestObjectDoc(String name, String description, List<RestObjectFieldDoc> fields) {
		super();
		this.name = name;
		this.description = description;
		this.fields = fields;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public List<RestObjectFieldDoc> getFields() {
		return fields;
	}

	@Override
	public int compareTo(RestObjectDoc o) {
		return name.compareTo(o.getName());
	}
}
