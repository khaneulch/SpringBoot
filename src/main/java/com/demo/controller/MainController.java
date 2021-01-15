package com.demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.demo.domain.SupportItem;
import com.demo.service.SupportItemService;

@Controller
public class MainController {
	
	@Autowired
	SupportItemService supportItemService;

	@RequestMapping("/jpa")
	public String jpaTest( @RequestParam Map<String, Object> params) {

		SupportItem supportItem = new SupportItem()
				.builder()
				.item_img( params.get("itemImg") + "")
				.item_name( params.get("itemName") + "")
				.item_price( params.get("itemPrice") + "")
				.build();
		
		supportItemService.saveTest(supportItem);
		
		return "/main/main";
	}
}
