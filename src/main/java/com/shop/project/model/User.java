package com.shop.project.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Table(name = "user")
public class User {

	public enum UserRole {
		ROLE_USER, ROLE_ADMIN
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", columnDefinition = "INT(11)")
	private Integer id;

	@Column(name = "username", nullable = false, columnDefinition = "VARCHAR(100)")
	private String username;

	@Column(name = "password", nullable = false, columnDefinition = "VARCHAR(100)")
	private String password;

	@Enumerated(EnumType.STRING)
	@Column(name = "userRole", columnDefinition = "VARCHAR(100)")
	private UserRole userRole = UserRole.ROLE_USER;

	public User() {
	};

	public User(Integer id, String name, String pass, UserRole role) {
		this.id = id;
		this.username = name;
		this.password = pass;
		this.userRole = role;
	}

	public User(String name, String pass, UserRole role) {
		this.username = name;
		this.password = pass;
		this.userRole = role;
	}

	public User(String name, UserRole role) {
		this.username = name;
		this.userRole = role;
	}

	public User(String name, String pass) {
		this.username = name;
		this.password = pass;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

	@Override
	public String toString() {
		String toString = String
				.format("\n[id=%s, username=%s, password=%s, userRole=%s, id,username,password,userRole");

		return toString;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {

		if (!(object instanceof User)) {
			return false;
		}

		User other = (User) object;

		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}

		return true;
	}

}
