package com.nt.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nt.model.UserDetailsInfo;
import com.nt.service.IUserDetailsMgmt;

@Controller
@RequestMapping("/user")
public class UserDetailsController {

	@Autowired
	private IUserDetailsMgmt service;

	@GetMapping("/register")
	public String showLoginPage(@ModelAttribute("info") UserDetailsInfo info) {
		return "bank_user_registration";
	}

	@PostMapping("/register")
	public String registerUserInfo(@ModelAttribute("info") UserDetailsInfo info, Map<String, Object> map) {
		String resultmessage = service.saveUser(info);
		map.put("message", resultmessage);
		return "user_registered_success";
	}

	@GetMapping("/show_login")
	public String showLoginPage() {
		return "bank_login";
	}

}
