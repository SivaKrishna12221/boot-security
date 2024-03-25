package com.nt.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/emp")
public class EmployeeManagementController {

	@GetMapping("/login")
	public String loginPage() {
		return "login page";
	}

	@GetMapping("/home")
	public String showEmpMgmtHome() {

		return "Welcome to Employee Management you can use this with out security";
	}

	@GetMapping("/add_emp")
	public String addNewEmp() {
		return "Hi admin this page is not secured for you!";
	}

	@GetMapping("/view_project")
	public String viewProject() {
		return "hi Employee you can see the project details this is not secured page for you";
	}

	@GetMapping("/assign_project")
	@PreAuthorize("hasAuthority('manager')")
	public String assignProject() {
		return "Hi Manager you can assign project to employee";
	}
}
