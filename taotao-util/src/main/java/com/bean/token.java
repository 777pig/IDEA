package com.bean;

public class token {

	public token() {
		super();
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}

	public token(String token ) {
		super();
		this.token = token;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	public token(String url, String token) {
		super();
		this.url = url;
		this.token = token;
	}

	public String getJsssionId() {
		return jsssionId;
	}
	public void setJsssionId(String jsssionId) {
		this.jsssionId = jsssionId;
	}

	private String jsssionId;
	private String url;
	private String token;
}
