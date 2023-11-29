package com.task.buddy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.task.buddy.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);

	@Query("UPDATE User u SET u.enabled =true WHERE u.id= ?1")
	@Modifying
	public void enable(Long id);
}
