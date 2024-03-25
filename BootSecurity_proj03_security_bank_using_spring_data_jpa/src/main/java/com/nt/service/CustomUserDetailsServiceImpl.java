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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.nt.model.UserDetailsInfo;
import com.nt.repo.CustomBankUserDetailsRepo;

@Service("service")
public class CustomUserDetailsServiceImpl implements IUserDetailsMgmt {

	@Autowired
	private CustomBankUserDetailsRepo bankRepo;

	@Autowired
	private BCryptPasswordEncoder encoder;

	@Override
	public String saveUser(UserDetailsInfo user) {
		user.setPword(encoder.encode(user.getPword()));
		String message = bankRepo.save(user).getUsid() + " id User is saved successfully";
		return message;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserDetailsInfo> opt = bankRepo.findByUname(username);
		UserDetailsInfo details = opt.get();
		Set<GrantedAuthority> gaRoles = new HashSet();// we should pass the set of granted authorities to page object
		if (opt.isEmpty()) {
			throw new IllegalArgumentException("user details not found");
		} else {
			for (String role : details.getRoles())// iterate one by one role
			{
				SimpleGrantedAuthority sgaRole = new SimpleGrantedAuthority(role);
				gaRoles.add(sgaRole);
			}
			User user = new User(details.getUname(), details.getPword(), gaRoles);
			return user;
		}
	}

}
