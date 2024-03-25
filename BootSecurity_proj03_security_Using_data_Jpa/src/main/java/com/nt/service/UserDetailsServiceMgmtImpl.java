package com.nt.service;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nt.model.UserDetailsInfo;
import com.nt.repo.IUserDetailsRepo;

@Service("service")
public class UserDetailsServiceMgmtImpl implements IUserDetailsServiceMgmt {

	@Autowired
	private IUserDetailsRepo userRepo;

	@Override
	public String registerUserDetails(UserDetailsInfo uinfo) {

		Integer id = userRepo.save(uinfo).getUserid();
		return "user registered with id:" + id;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserDetailsInfo> opt = userRepo.findByUsername(username);

		if (opt.isEmpty()) {
			throw new IllegalArgumentException("User not found exception");
		} else {
			/*Set<GrantedAuthority> roles = new HashSet();
			UserDetailsInfo uinfo = opt.get();
			for (String role : uinfo.getRoles()) {
				SimpleGrantedAuthority sga = new SimpleGrantedAuthority(role);
				roles.add(sga);
			}
			User user = new User(uinfo.getUsername(), uinfo.getPassword(), roles);
			*/
			UserDetailsInfo uinfo = opt.get();

			User user = new User(uinfo.getUsername(), uinfo.getPassword(), uinfo.getRoles().stream()
					.map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toSet()));
			return user;

		}
	}
}
