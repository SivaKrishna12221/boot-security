package com.nt.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nt.entity.UserInfo;
import com.nt.repo.IUserInfoRepo;

@Service
public class UserDetailsServiceImpl implements IUserInfoMgmt {

	@Autowired
	private IUserInfoRepo userRepo;

	@Autowired
	private PasswordEncoder encoder;

	@Override
	public String registerUserInfo(UserInfo uinfo) {
		uinfo.setUsername(encoder.encode(uinfo.getUsername()));
		uinfo.setPassword(encoder.encode(uinfo.getPassword()));
		Integer userno = userRepo.save(uinfo).getUserno();
		return userno + " user info is saved ";
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserInfo> opt = userRepo.findByUsername(username);
		if (opt.isPresent()) {
			UserInfo userInfo = opt.get();
			String uname = userInfo.getUsername();
			String pword = userInfo.getPassword();
			// actually it gives string roles but the user accept Simple Granted Authority
			// roles
			Set<GrantedAuthority> roles = new HashSet<>();

			for (String rol : userInfo.getRoles()) {
				SimpleGrantedAuthority sga = new SimpleGrantedAuthority(rol);
				roles.add(sga);
			}
			User user = new User(uname, pword, roles);
			return user;
		} else {
			throw new IllegalArgumentException("User not found exception");
		}
	}
}
