package com.questk2.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.questk2.entity.User;
@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByUserNameAndPassword(String userName, String password);
}
