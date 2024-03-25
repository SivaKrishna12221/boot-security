package com.nt.repo;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.nt.model.UserDetailsInfo;

public interface CustomBankUserDetailsRepo extends CrudRepository<UserDetailsInfo, Integer> {

	public Optional<UserDetailsInfo> findByUname(String username);
}
