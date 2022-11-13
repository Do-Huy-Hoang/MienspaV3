package com.example.mienspa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.mienspa.models.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {

}
