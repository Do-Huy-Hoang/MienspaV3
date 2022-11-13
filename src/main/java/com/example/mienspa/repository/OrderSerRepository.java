package com.example.mienspa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.mienspa.models.OrdersSer;


@Repository
public interface OrderSerRepository extends JpaRepository<OrdersSer, String>{
	@Query(value = "SELECT * FROM ordersser WHERE orSer_UserId  = ?", nativeQuery = true)
	List<OrdersSer> findAllByUserId(String id);
}
