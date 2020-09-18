package com.dharamsot.tasktracker.exceptionhandling;




public class UserAlreadyExistException extends Exception {

	
	public UserAlreadyExistException(String message) {
        super(message);
    }
}
