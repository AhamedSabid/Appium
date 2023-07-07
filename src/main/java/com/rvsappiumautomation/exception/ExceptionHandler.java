package com.rvsappiumautomation.exception;

/**
 * class to handle the exceptions
 * 
 * @author rahul.raman
 *
 */
@SuppressWarnings("serial")
public class ExceptionHandler extends Exception {

	public ExceptionHandler(String message) {
		super(message);
	}

	public ExceptionHandler(String message, Throwable cause) {
		super(message, cause);
	}

	public ExceptionHandler(Throwable cause) {
		super(cause);
	}
}
