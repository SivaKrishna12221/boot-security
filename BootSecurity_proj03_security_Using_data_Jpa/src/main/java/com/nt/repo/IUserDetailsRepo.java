package com.nt.repo;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.nt.model.UserDetailsInfo;

public interface IUserDetailsRepo extends CrudRepository<UserDetailsInfo, Integer> {

	public Optional<UserDetailsInfo> findByUsername(String username);
}
