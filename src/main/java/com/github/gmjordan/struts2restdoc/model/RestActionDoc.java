package com.github.gmjordan.struts2restdoc.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.github.gmjordan.struts2restdoc.annotation.RestAction;

public class RestActionDoc implements Comparable<RestActionDoc> {
	public String jsondocId = UUID.randomUUID().toString();

	private String name;

	private String description;

	private List<RestMethodDoc> methods;

	private String[] actionHeaderList;

	public static RestActionDoc buildFromAnnotation(RestAction restAction) {
		RestActionDoc RestActionDoc = new RestActionDoc();
		RestActionDoc.setDescription(restAction.description());
		RestActionDoc.setName(restAction.name());
		RestActionDoc.setActionHeaderList(restAction.actionHeaderList());
		return RestActionDoc;
	}

	public RestActionDoc() {
		methods = new ArrayList<RestMethodDoc>();
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

	public List<RestMethodDoc> getMethods() {
		return methods;
	}

	public void setMethods(List<RestMethodDoc> methods) {
		this.methods = methods;
	}

	public void addMethod(RestMethodDoc apiMethod) {
		methods.add(apiMethod);
	}

	@Override
	public int compareTo(RestActionDoc o) {
		return name.compareTo(o.getName());
	}

	public String[] getActionHeaderList() {
		return actionHeaderList;
	}

	public void setActionHeaderList(String[] actionHeaderList) {
		this.actionHeaderList = actionHeaderList;
	}

}
