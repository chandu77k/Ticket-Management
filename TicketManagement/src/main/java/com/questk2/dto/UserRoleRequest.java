package com.questk2.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Data Transfer Object (DTO) for User Role Request.
 * This class is used to transfer user data with role-related information.
 */
public class UserRoleRequest {
	
	@Schema(description = "Username for the user")
	@NotBlank(message = "Username cannot be blank")
	private String userName;
	
	@Schema(description = "Password for the user")
	@NotBlank(message = "Password cannot be blank")
	private String password;
	
	@Schema(description = "Name of the user")
	private String name;
	
	@Schema(description = "Email of the user")
	@Email(message = "Email should be valid")
	private String email;
	
	@Schema(description = "Phone number of the user")
	private Long phoneNumber;
	
	@Schema(description = "Department of the user")
	private String department;
	
	@Schema(description = "Role of the user")
	@NotNull(message = "Role cannot be blank")
	private Long roles;

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Long getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(Long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public Long getRole() {
		return roles;
	}
	public void setRole(Long roles) {
		this.roles = roles;
	}
	
}
