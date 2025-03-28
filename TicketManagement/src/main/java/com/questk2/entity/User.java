package com.questk2.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Entity class representing a User.
 * This class maps to the 'users' table in the database.
 */
@Entity
@Table(name="users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(description = "Id for the user")
	@NotBlank(message = "Id is auto generated")
	private Long id;
	
	@Schema(description = "Name of the user")
	@Column(name = "name")
	private String name;
	
	@Schema(description = "Username of the user")
	@NotBlank(message = "Username cannot be blank")
	@Column(name = "user_name")
	private String userName;
	
	@Schema(description = "email for the user")
	@Column(name = "user_email")
	private String email;
	
	@Schema(description = "Department of the user")
	@Column(name = "user_department")
	private String department;
	
	@Schema(description = "Phone Number of the user")
	@Column(name = "user_number")
	private Long phoneNumber;
	
	@Schema(description = "Password for the user")
	@Column(name = "user_password")
	private String password;
	
	@Schema(description = "Role of the user getting from UserRole class")
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
    private List<UserRole> roles;
	public User() {
		
	}
	public User(String name,String userName,String email,String department,Long phoneNumber,String password) {
		super();
		this.name=name;
		this.userName=userName;
		this.email=email;
		this.department=department;
		this.phoneNumber=phoneNumber;
		this.password=password;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public Long getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(Long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public List<UserRole> getRoles() {
		return roles;
	}
	public void setRoles(List<UserRole> roles) {
		this.roles = roles;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", userName=" + userName + ", email=" + email + ", department="
				+ department + ", phoneNumber=" + phoneNumber + ", password=" + password + ", roles=" + roles + "]";
	}
	
	
}
