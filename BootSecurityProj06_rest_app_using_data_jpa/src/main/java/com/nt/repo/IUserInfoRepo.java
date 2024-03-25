package com.nt.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nt.entity.UserInfo;

public interface IUserInfoRepo extends JpaRepository<UserInfo, Integer> {

	public Optional<UserInfo> findByUsername(String username);
}
