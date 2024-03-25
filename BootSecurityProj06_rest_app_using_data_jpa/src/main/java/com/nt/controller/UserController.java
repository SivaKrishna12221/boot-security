package com.nt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nt.entity.UserInfo;
import com.nt.service.IUserInfoMgmt;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private IUserInfoMgmt userService;

	@GetMapping("/register")
	public String showRegister() {
		return "registeration form";
	}

	@PostMapping("/save")
	public String registerUser(@RequestBody UserInfo uinfo)// here the request body also acts the serialization
	{
		System.out.println("UserController.registerUser()");

		String registerUserInfo = userService.registerUserInfo(uinfo);
		return registerUserInfo;
	}
}
