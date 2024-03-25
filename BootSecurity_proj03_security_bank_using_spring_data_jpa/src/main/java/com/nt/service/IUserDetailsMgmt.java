package com.nt.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.nt.model.UserDetailsInfo;

public interface IUserDetailsMgmt extends UserDetailsService {

	public String saveUser(UserDetailsInfo user);

}
