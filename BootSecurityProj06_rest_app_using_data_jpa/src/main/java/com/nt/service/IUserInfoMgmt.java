package com.nt.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.nt.entity.UserInfo;

public interface IUserInfoMgmt extends UserDetailsService {

	public String registerUserInfo(UserInfo uinfo);

}
