package com.github.gmjordan.struts2restdoc.model;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

import com.github.gmjordan.struts2restdoc.RestActionUtil;
import com.github.gmjordan.struts2restdoc.annotation.RestObjectField;

public class RestObjectFieldDoc {

	public String jsondocId = UUID.randomUUID().toString();

	private String name;

	private String type;

	private String multiple;

	private String description;

	private String format;

	private String[] allowedvalues;

	private String mapKeyObject;

	private String mapValueObject;

	private String map;

	public static RestObjectFieldDoc buildFromAnnotation(RestObjectField annotation, Field field) {
		RestObjectFieldDoc restPojoFieldDoc = new RestObjectFieldDoc();
		restPojoFieldDoc.setName(field.getName());
		restPojoFieldDoc.setDescription(annotation.description());
		String[] typeChecks = getFieldObject(field);
		restPojoFieldDoc.setType(typeChecks[0]);
		restPojoFieldDoc.setMultiple(String.valueOf(RestActionUtil.isMultiple(field.getType())));
		restPojoFieldDoc.setFormat(annotation.format());
		restPojoFieldDoc.setAllowedvalues(annotation.allowedvalues());
		restPojoFieldDoc.setMapKeyObject(typeChecks[1]);
		restPojoFieldDoc.setMapValueObject(typeChecks[2]);
		restPojoFieldDoc.setMap(typeChecks[3]);
		return restPojoFieldDoc;
	}

	public static String[] getFieldObject(Field field) {
		if (Map.class.isAssignableFrom(field.getType())) {
			Class<?> mapKeyClazz = null;
			Class<?> mapValueClazz = null;

			if (field.getGenericType() instanceof ParameterizedType) {
				ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
				Type mapKeyType = parameterizedType.getActualTypeArguments()[0];
				Type mapValueType = parameterizedType.getActualTypeArguments()[1];
				mapKeyClazz = (Class<?>) mapKeyType;
				mapValueClazz = (Class<?>) mapValueType;
			}
			return new String[] { RestActionUtil.getObjectNameFromAnnotatedClass(field.getType()), (mapKeyClazz != null) ? mapKeyClazz.getSimpleName().toLowerCase() : null, (mapValueClazz != null) ? mapValueClazz.getSimpleName().toLowerCase() : null, "map" };

		} else if (Collection.class.isAssignableFrom(field.getType())) {
			if (field.getGenericType() instanceof ParameterizedType) {
				ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
				Type type = parameterizedType.getActualTypeArguments()[0];
				if (type instanceof WildcardType) {
					return new String[] { RestActionUtil.WILDCARD, null, null, null };
				}
				Class<?> clazz = (Class<?>) type;
				return new String[] { RestActionUtil.getObjectNameFromAnnotatedClass(clazz), null, null, null };
			} else {
				return new String[] { RestActionUtil.UNDEFINED, null, null, null };
			}

		} else if (field.getType().isArray()) {
			Class<?> classArr = field.getType();
			return new String[] { RestActionUtil.getObjectNameFromAnnotatedClass(classArr.getComponentType()), null, null, null };

		}
		return new String[] { RestActionUtil.getObjectNameFromAnnotatedClass(field.getType()), null, null, null };
	}

	public String getMapKeyObject() {
		return mapKeyObject;
	}

	public void setMapKeyObject(String mapKeyObject) {
		this.mapKeyObject = mapKeyObject;
	}

	public String getMapValueObject() {
		return mapValueObject;
	}

	public void setMapValueObject(String mapValueObject) {
		this.mapValueObject = mapValueObject;
	}

	public String getMap() {
		return map;
	}

	public void setMap(String map) {
		this.map = map;
	}

	public String[] getAllowedvalues() {
		return allowedvalues;
	}

	public void setAllowedvalues(String[] allowedvalues) {
		this.allowedvalues = allowedvalues;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getMultiple() {
		return multiple;
	}

	public void setMultiple(String multiple) {
		this.multiple = multiple;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public RestObjectFieldDoc() {
		super();
	}
}
