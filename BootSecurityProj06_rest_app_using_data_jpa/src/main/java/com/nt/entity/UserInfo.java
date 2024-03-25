package com.nt.entity;

import java.util.Set;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity(name = "rest_security_jpa_user_info")
@Data
@NoArgsConstructor

public class UserInfo {

	@Id
	@GeneratedValue
	private Integer userno;
	@Column(length = 30, unique = true, nullable = false)
	private String username;
	@Column(length = 70)
	@NonNull
	private String password;
	@Column(length = 30)
	private String address;
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "rest_security_jpa_user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "userno"))
	@Column(name = "role")
	private Set<String> roles;
}
