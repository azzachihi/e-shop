package com.shop.project.model;

import com.shop.project.model.User.UserRole;

public class UserDetails {

	private Integer idUser;
	private String username;
	private UserRole userRole;

	public UserDetails(User user) {
		this.idUser = user.getId();
		this.username = user.getUsername();
		this.userRole = user.getUserRole();
	}

	public Integer getIdUser() {
		return idUser;
	}

	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}
}
