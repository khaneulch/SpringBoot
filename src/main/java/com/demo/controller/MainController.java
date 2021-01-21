package com.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.demo.domain.SupportItem;
import com.demo.service.SupportItemService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class MainController {
	
	@Autowired
	SupportItemService supportItemService;

	@RequestMapping("/jpa")
	public void jpaTest( /*@RequestParam Map<String, Object> params*/SupportItem supportItem) {

//		SupportItem supportItem = new SupportItem()
//				.builder()
//				.itemImg( params.get("itemImg") + "")
//				.itemName( params.get("itemName") + "")
//				.itemPrice( params.get("itemPrice") + "")
//				.build();
		
		supportItemService.save(supportItem);
	}
	
	@GetMapping("/{path}")
	public String mainPage( @PathVariable(required=false, name="path") String path) {
		return "/main/" + path;
	}
	
	@PostMapping("/findAll")
	public ResponseEntity<Object> findAll( @RequestBody(required=false) String params) {
		
		List<SupportItem> list = supportItemService.findAll();
		
		ResponseEntity<Object> response = new ResponseEntity<>(list, HttpStatus.OK);
		
		return response;
	}
	
	@PostMapping("/findAllPage")
	public ResponseEntity<Object> findAllPage( @RequestBody(required=false) String params) {
		
		try {
			Map<String, String> m = new HashMap<String, String>();
			if( params != null && !params.equals("")) {
				ObjectMapper mapper = new ObjectMapper();
				m = mapper.readValue(params, Map.class);
			}
			
			Pageable page = PageRequest.of( Integer.parseInt(m.getOrDefault("page", "0") + ""), 10);
			
			Page<SupportItem> list = supportItemService.findByItemNameLike(m.getOrDefault("search", "%"), page);
			
			return new ResponseEntity<>(list, HttpStatus.OK);
			
		} catch( Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/deleteItem")
	public ResponseEntity<Object> deleteItem( @RequestBody(required=true) SupportItem supportItem) {
		supportItemService.delete(supportItem);
		return new ResponseEntity<>( supportItem.getItemName(), HttpStatus.OK);
	}
	
	@PostMapping("/saveItem")
	public ResponseEntity<Object> saveItem( @RequestBody(required=true) SupportItem supportItem) {
		supportItemService.save(supportItem);
		return new ResponseEntity<>( supportItem, HttpStatus.OK);
	}
}
