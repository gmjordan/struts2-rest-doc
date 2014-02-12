package com.github.gmjordan.struts2restdoc.model;

import java.util.Set;

public class RestDoc {

	private String version;

	private String basePath;

	private Set<RestActionDoc> apis;

	private Set<RestObjectDoc> objects;

	public RestDoc(String version, String basePath) {
		super();
		this.version = version;
		this.basePath = basePath;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getBasePath() {
		return basePath;
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	public Set<RestActionDoc> getApis() {
		return apis;
	}

	public void setApis(Set<RestActionDoc> apis) {
		this.apis = apis;
	}

	@Override
	public String toString() {
		return "JSONDoc [version=" + version + ", basePath=" + basePath + ", apis=" + apis + ", objects=" + objects + "]";
	}
}
