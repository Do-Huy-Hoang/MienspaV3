package com.example.mienspa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.mienspa.models.RefreshToken;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {
	@Query(value = "SELECT * FROM refreshtoken WHERE JwtId  = ?", nativeQuery = true)
	RefreshToken findByRefreshToken(String JwtId);
}
