package com.questk2.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.questk2.dto.UserRoleRequest;
import com.questk2.entity.User;
import com.questk2.entity.UserRole;
import com.questk2.repository.UserRepository;
import com.questk2.repository.UserRoleRepository;
@Transactional
@Service
public class UserOperationService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserRoleRepository userRoleRepository;

    /**
     * Retrieves a list of all users.
     * 
     * @return A list of all users from the user repository.
     */
    public List<User> getAllUsers() {
    	
        return userRepository.findAll();
    }
    
    /**
     * Creates a new user with a role and saves the user to the repository.
     * 
     * @param userRoleRequest A DTO containing the details required to create a user, including username, password, email, phone number, department, and role.
     * @return The created User entity, including the assigned roles, after being saved to the repository.
     * @throws RuntimeException if the role specified in the request is not found in the user role repository.
     */
    @Transactional
    public User createUserWithRole(UserRoleRequest userRoleRequest) {
        
        User user = new User();
        user.setUserName(userRoleRequest.getUserName());
        user.setPassword(userRoleRequest.getPassword());
        user.setName(userRoleRequest.getName());
        user.setEmail(userRoleRequest.getEmail());
        user.setPhoneNumber(userRoleRequest.getPhoneNumber());
        user.setDepartment(userRoleRequest.getDepartment());

        user = userRepository.save(user);

        UserRole userRole = userRoleRepository.findById(userRoleRequest.getRole())
                .orElseThrow(() -> new RuntimeException("Role not found: " + userRoleRequest.getRole()));

        UserRole newUserRole = new UserRole();
        newUserRole.setRoleName(userRole.getRoleName());
        newUserRole.setUser(user);

        userRoleRepository.save(newUserRole);

        List<UserRole> roles = new ArrayList<>();
        roles.add(newUserRole);
        user.setRoles(roles);

        return userRepository.save(user);
    }
    
    /**
     * Updates an existing user with new data and assigns a role to the user.
     * 
     * @param id The ID of the user to be updated.
     * @param userRoleRequest A DTO containing the new data for the user and the role to be assigned.
     * @return The updated User entity, including the new role after being saved to the repository.
     * @throws RuntimeException if the user with the specified ID is not found or if the role specified in the request is not found.
     */
    public User updateUser(Long id, UserRoleRequest userRoleRequest) {
        return userRepository.findById(id).map(user -> {
            user.setDepartment(userRoleRequest.getDepartment());
            user.setEmail(userRoleRequest.getEmail());
            user.setUserName(userRoleRequest.getUserName());
            user.setPassword(userRoleRequest.getPassword());
            user.setPhoneNumber(userRoleRequest.getPhoneNumber());
            user.setName(userRoleRequest.getName());
            
            user.getRoles().clear();
            
            UserRole userRole = userRoleRepository.findById(userRoleRequest.getRole())
                .orElseThrow(() -> new RuntimeException("Role not found : " + userRoleRequest.getRole()));
            userRole.setUser(user);
            user.getRoles().add(userRole);
            
            return userRepository.save(user); 
        }).orElseThrow(() -> new RuntimeException("User not found : " + id));
    }

}
