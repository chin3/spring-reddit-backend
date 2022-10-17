package com.example.springredditclone.exceptions;
@SuppressWarnings("serial")
public class PostNotFoundException extends RuntimeException {
	public PostNotFoundException(String exMessage){
		super(exMessage);
	}
}
