package com.demo.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.domain.SupportItem;
import com.demo.repository.SupportItemRepository;

@Service
@Transactional
public class SupportItemService {
	@Autowired
	SupportItemRepository supportItemRepository;

	public void saveTest(SupportItem test) {
		supportItemRepository.save(test);
	}
}