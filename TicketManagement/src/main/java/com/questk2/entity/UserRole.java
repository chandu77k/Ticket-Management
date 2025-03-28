package com.questk2.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Entity class representing a User's Role.
 * This class maps to the 'user_role' table in the database.
 */
@Entity
@Table(name="user_role")
public class UserRole {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(description = "Id for the User role")
	@NotBlank(message = "Id is auto generated")
	private Long id;
	
	@Schema(description = "User Id from the User table")
	@ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
	@JsonBackReference
    private User user;
	
	@Schema(description = "Role name for the User")
	@Column(name = "role")
	private String role;
	
	public UserRole() {
		
	}

	public UserRole(User user, String role) {
		super();
		this.user = user;
		this.role = role;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "UserRole [id=" + id + ", user=" + user + ", role=" + role + "]";
	}

	
}
