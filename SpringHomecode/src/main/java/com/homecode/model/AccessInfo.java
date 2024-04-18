package com.homecode.model;

import java.util.List;

public class AccessInfo {
    private long userId;
    private List<String> endpoints;
	public long getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public List<String> getEndpoints() {
		return endpoints;
	}
	public void setEndpoints(List<String> endpoints) {
		this.endpoints = endpoints;
	}
    
}
