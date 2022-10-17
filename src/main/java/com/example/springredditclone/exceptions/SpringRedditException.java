package com.example.springredditclone.exceptions;
@SuppressWarnings("serial")
public class SpringRedditException extends RuntimeException {
	public SpringRedditException(String exMessage) {
		super(exMessage);
	}
}
