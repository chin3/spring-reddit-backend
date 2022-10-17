package com.example.springredditclone.exceptions;

@SuppressWarnings("serial")
public class UsernameNotFoundException extends RuntimeException {

	public UsernameNotFoundException(String exMessage)  {
		super(exMessage);
	}

}
