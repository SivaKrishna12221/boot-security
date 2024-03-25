package com.nt.controller;

import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/bank")
public class BankController {

	@GetMapping("/")
	public String showHomePage() {
		return "bank_home";
	}

	@GetMapping("/offers")
	public String showBankOffers() {
		return "bank_offers";
	}

	@GetMapping("/check_balance")
	public String displayBankBalance(Map<String, Object> map) {
		map.put("balance", new Random().nextInt(100000));
		return "bank_balance";
	}

	@GetMapping("/loan_approval")
	public String loanApproval(Map<String, Object> map) {
		map.put("loan", new Random().nextInt(100000));
		return "bank_loan";
	}

}
