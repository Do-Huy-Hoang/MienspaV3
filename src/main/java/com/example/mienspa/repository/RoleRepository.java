package com.example.mienspa.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.mienspa.models.Role;


@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{
	@Query(value = "SELECT * FROM role WHERE ro_Name = ?", nativeQuery = true)
	Role getByRoleName(String role);
}
