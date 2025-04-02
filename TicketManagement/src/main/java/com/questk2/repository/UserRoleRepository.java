package com.questk2.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.questk2.entity.UserRole;
@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

	Optional<UserRole> findByRoleName(String roleName);

}
