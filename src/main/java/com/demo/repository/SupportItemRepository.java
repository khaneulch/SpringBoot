package com.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.domain.SupportItem;

public interface SupportItemRepository extends JpaRepository<SupportItem, Integer>{
	
}