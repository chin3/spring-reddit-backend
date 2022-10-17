package com.example.springredditclone.service;



import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springredditclone.model.User;
import com.example.springredditclone.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service//add 
@AllArgsConstructor//add
public class UserDetailsServiceImpl implements UserDetailsService{
	
	private final UserRepository userRepository;
	@Override
	@Transactional(readOnly=true)
	
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> userOptional = userRepository.findByUsername(username);//retrieve user based on username
		User user = userOptional.orElseThrow(()-> 
		new UsernameNotFoundException("No User " + "Found with username :" + username ));// if not found throw exception
		return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),user.isEnabled(),true,true,true,getAuthorities("USER"));//Like a wrapper for spring, mapping user details to class, and providing an aithoritie for a simple granted authorty for a user
	}
	private Collection<? extends GrantedAuthority> getAuthorities(String role) {
		return Collections.singletonList(new SimpleGrantedAuthority(role));
	}

}
