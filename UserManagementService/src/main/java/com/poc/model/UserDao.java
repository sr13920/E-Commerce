package com.poc.model;

import lombok.*;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "user")
@Table(name = "user")
public class UserDao {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column
	@NotBlank(message = "Enter valid username")
	private String username;

	@Column
	@NotEmpty(message = "Enter valid firstname")
	private String firstname;

	@Column
	@NotEmpty(message = "Enter valid lastname")
	private String lastname;

	@Column
	@NotEmpty(message = "Enter valid password")
	private String password;

	@Column
	@Email(message = "Enter valid email")
	private String email;

	@ElementCollection(fetch = FetchType.EAGER)
	private List<String> roles;

	public UserDao(String username, String firstname, String lastname, String password, String email,
			List<String> roles) {
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.password = password;
		this.email = email;
		this.roles = roles;
	}

}