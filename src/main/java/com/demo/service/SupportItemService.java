package com.demo.service;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.demo.domain.SupportItem;
import com.demo.repository.SupportItemRepository;

@Service
@Transactional
public class SupportItemService {
	@Autowired
	SupportItemRepository supportItemRepository;

	public void save(SupportItem test) {
		supportItemRepository.save(test);
	}
	
	public List<SupportItem> findAll() {
		return supportItemRepository.findAll();
	}
	
	public Page<SupportItem> findByItemNameLike( String itemName, Pageable page) {
		return supportItemRepository.findByItemNameLikeOrderByCreatedDtDesc( itemName, page);
	}
	
	public void delete(SupportItem item) {
		supportItemRepository.delete(item);
	}
}