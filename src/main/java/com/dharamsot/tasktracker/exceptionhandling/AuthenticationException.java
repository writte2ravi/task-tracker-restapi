package com.dharamsot.tasktracker.exceptionhandling;

public class AuthenticationException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 5978387939943664344L;

	public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}

