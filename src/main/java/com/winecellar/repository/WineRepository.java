package com.winecellar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.winecellar.repository.entity.WineEntity;

@Repository
public interface WineRepository extends JpaRepository<WineEntity, Long>{
	
}