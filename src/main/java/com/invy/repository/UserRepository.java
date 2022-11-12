/**
 * 
 */
package com.invy.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.invy.entity.User;
public interface UserRepository  extends JpaRepository<User, Long>{

	Optional<User> findRoleByName(String name);


}
