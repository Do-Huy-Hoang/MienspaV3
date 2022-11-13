package com.example.mienspa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.mienspa.models.ShopInfo;


@Repository
public interface ShopInfoRepository extends JpaRepository<ShopInfo, String>{

}
