package com.homecode.model;

public class RoleInfo {

	private long userId;
	private String accountName;
	private String role;

	public RoleInfo() {
	}

	public RoleInfo(long u, String acc, String r) {

		userId = u;
		accountName = acc;
		role = r;

	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
