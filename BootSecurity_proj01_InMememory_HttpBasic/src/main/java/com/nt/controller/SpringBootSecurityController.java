package com.nt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/bank")
public class SpringBootSecurityController {
	@GetMapping("/home")
	public String showHomePage() {
		return "bank_home";
	}

	@GetMapping("/balance")
	public String balancyEnquiry() {
		return "balance_check";
	}

	@GetMapping("/offers")
	public String displayOffers() {
		return "loan_offers";
	}

	@GetMapping("/approve_loan")
	public String approveLoan() {
		return "loan_approval";
	}

	@GetMapping("/denied")
	public String deniedPage() {
		return "denied";
	}

}
