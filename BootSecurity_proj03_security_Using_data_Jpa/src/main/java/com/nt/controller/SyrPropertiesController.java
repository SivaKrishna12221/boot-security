package com.nt.controller;

import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/syr")
public class SyrPropertiesController {

	@GetMapping("/home")
	public String showSyrHomePage() {
		return "syr_home";
	}

	@GetMapping("/managing_director")
	public String showManagingDirectorRoles() {
		return "syr_managing_director";
	}

	@GetMapping("/vise_president")
	public String showVicePresidentRoles() {
		return "syr_vice_president";
	}

	@GetMapping("/marketing_manager")
	public String showMarketingManager() {
		return "syr_marketing_manager";
	}

	@GetMapping("/customers")
	public String showCustomerPage(Map<String, Object> map) {
		map.put("price", new Random().nextInt(100000));
		map.put("price2", new Random().nextInt(100000));
		return "syr_customer";
	}

}
