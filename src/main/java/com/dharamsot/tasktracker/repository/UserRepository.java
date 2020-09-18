package com.dharamsot.tasktracker.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.dharamsot.tasktracker.entity.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
	
	Users findByUsername(String username);

	
}
