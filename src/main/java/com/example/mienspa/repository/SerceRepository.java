package com.example.mienspa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.mienspa.models.Serce;



@Repository
public interface SerceRepository extends JpaRepository<Serce, String>{

}
