package com.questk2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.questk2.dto.UserRoleRequest;
import com.questk2.entity.User;
import com.questk2.entity.UserRole;
import com.questk2.repository.UserRepository;
import com.questk2.repository.UserRoleRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Controller class for handling user-related operations.
 * Provides endpoints to fetch, create, and update users.
 */
@RestController
@CrossOrigin(origins = "http://localhost:5173/")
@Tag(name="User Controller", description="APIs for controllers for getting users table, posting users data and updating values in users")
public class UserController {
	@Autowired
	UserRepository userRepository;
	@Autowired
	UserRoleRepository userRoleRepository;
	
	/**
     * Retrieves all users from the database.
     *
     * @return List of all users.
     */
	@Operation(summary = "Get mapping for users", description = "Get all details in users")
	@ApiResponse(responseCode = "200", description = "found", content = @Content(schema = @Schema(implementation = String.class)))
	@ApiResponse(responseCode = "404", description = "Method not found")
	@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = String.class)))
	@GetMapping("/users")
	public List<User> getAllUsers(){
		List<User> users = userRepository.findAll();
		return users;
	}
	
	/**
     * Creates a new user with a role.
     *
     * @param userRoleRequest The data transfer object containing user details and role.
     * @return The created user object.
     */
	@Operation(summary = "POST mapping for users", description = "Post all details in users using DTO")
	@ApiResponse(responseCode = "200", description = "found", content = @Content(schema = @Schema(implementation = String.class)))
	@ApiResponse(responseCode = "404", description = "Method not found")
    @PostMapping("/users/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public User createUserWithRole(@RequestBody UserRoleRequest userRoleRequest) {
        User user = new User();
        user.setUserName(userRoleRequest.getUserName());
        user.setPassword(userRoleRequest.getPassword());
        user.setName(userRoleRequest.getName());
        user.setEmail(userRoleRequest.getEmail());
        user.setPhoneNumber(userRoleRequest.getPhoneNumber());
        user.setDepartment(userRoleRequest.getDepartment());
        user = userRepository.save(user);
        UserRole userRole = new UserRole(user, userRoleRequest.getRole());
        userRoleRepository.save(userRole);
        return user;
    }
	
	/**
     * Updates an existing user's details.
     *
     * @param id The ID of the user to update.
     * @param userRoleRequest The updated user details including role.
     * @return The updated user object.
     */
	@Operation(summary = "Put mapping for users", description = "Update details in users using DTO")
	@ApiResponse(responseCode = "200", description = "found", content = @Content(schema = @Schema(implementation = String.class)))
	@ApiResponse(responseCode = "404", description = "Method not found")
	@PutMapping("/users/update/{id}")
	public User updateUser(@PathVariable Long id,@RequestBody UserRoleRequest userRoleRequest) {
		return userRepository.findById(id).map(user -> {
			user.setDepartment(userRoleRequest.getDepartment());
			user.setEmail(userRoleRequest.getEmail());
			user.setUserName(userRoleRequest.getUserName());
			user.setPassword(userRoleRequest.getPassword());
			user.setPhoneNumber(userRoleRequest.getPhoneNumber());
			user.setName(userRoleRequest.getName());
			User updatedUser = userRepository.save(user);
			
			UserRole userRole = userRoleRepository.findByUser(updatedUser).orElse(new UserRole(updatedUser, userRoleRequest.getRole()));
			userRole.setRole(userRoleRequest.getRole());
			userRoleRepository.save(userRole);
			return updatedUser;
		}).orElseThrow(()->new RuntimeException("User not found : "+ id));
	}
	
}
