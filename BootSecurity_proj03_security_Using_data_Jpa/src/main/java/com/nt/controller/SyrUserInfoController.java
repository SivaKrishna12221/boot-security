package com.nt.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nt.model.UserDetailsInfo;
import com.nt.service.IUserDetailsServiceMgmt;

@Controller
@RequestMapping("/user")
public class SyrUserInfoController {

	@Autowired
	private IUserDetailsServiceMgmt service;
	@Autowired
	private BCryptPasswordEncoder encoder;

	@GetMapping("/register")
	public String ShowUserRegisterPage(@ModelAttribute("user") UserDetailsInfo info) {
		return "syr_user_register";
	}

	@PostMapping("/register")
	public String registerUserDetails(@ModelAttribute("user") UserDetailsInfo info, Map<String, Object> map) {
		info.setPassword(encoder.encode(info.getPassword()));
		String message = service.registerUserDetails(info);
		map.put("message", message);
		return "user_registered_success";
	}

	@GetMapping("/show_login")
	public String showLoginPage() {
		return "user_login_page";
	}
}
