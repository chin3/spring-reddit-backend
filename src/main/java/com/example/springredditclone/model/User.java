package com.example.springredditclone.model;

import java.time.Instant;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
	@Id
	@GeneratedValue(strategy=IDENTITY)
	private Long userId;
	@NotBlank(message="username required")
	private String username;
	@NotBlank(message="password required")
	private String password;
	@Email
	@NotEmpty(message="Email is Required")
	private String email;
	private Instant created;
	private boolean enabled;
	

}
