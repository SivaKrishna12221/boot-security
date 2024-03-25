package com.nt.model;

import java.util.Set;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "security_user_details")
@Data
public class UserDetailsInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer usid;
	@Column(length = 20, unique = true, nullable = false)
	private String uname;
	@Column(length = 100, nullable = false)
	private String pword;
	@Column(length = 50, nullable = false)
	private String email;
	private Boolean status = true;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "security_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "usid"))
	@Column(name = "role")
	private Set<String> roles;

}
