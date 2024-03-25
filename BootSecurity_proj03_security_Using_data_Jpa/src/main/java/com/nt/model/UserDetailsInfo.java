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
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "security_user_details2")
@Data
public class UserDetailsInfo {
	@Id
	@SequenceGenerator(name = "gen1", sequenceName = "seq_userId", initialValue = 100, allocationSize = 1)
	@GeneratedValue(generator = "gen1", strategy = GenerationType.SEQUENCE)
	@Column(name = "user_id")
	private Integer userid;
	@Column(length = 20, unique = true, nullable = false)
	private String username;
	@Column(length = 70, nullable = false)
	private String password;
	@Column(length = 40, nullable = false)
	private String emailId;
	private Boolean status = true;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "security_user_roles", joinColumns = @JoinColumn(name = "us_id", referencedColumnName = "user_id"))
	// here us_id colletionTable second column name and user_id parent column prima
	@Column(name = "role")
	private Set<String> roles;

}
