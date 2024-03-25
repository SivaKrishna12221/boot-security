package com.nt.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.nt.model.UserDetailsInfo;

public interface IUserDetailsServiceMgmt extends UserDetailsService {

	public String registerUserDetails(UserDetailsInfo uinfo);
}
