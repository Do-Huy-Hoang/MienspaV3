package com.example.mienspa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.mienspa.models.OrdersPro;


@Repository
public interface OrderProRepository extends JpaRepository<OrdersPro, String>{
	@Query(value = "SELECT * FROM orderspro WHERE orPro_UserId  = ?", nativeQuery = true)
	List<OrdersPro> findAllByUserId(String id);
}
