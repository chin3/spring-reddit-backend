package com.example.springredditclone.exceptions;

@SuppressWarnings("serial")
public class SubredditNotFoundException extends RuntimeException{
	public SubredditNotFoundException(String exMessage)  {
		super(exMessage);
	}
}
