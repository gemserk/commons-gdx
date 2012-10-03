package com.gemserk.commons.gdx.resources.exceptions;

public class OpenGLOutOfMemoryException extends RuntimeException {

	private static final long serialVersionUID = -2104182067007231578L;
	
	public OpenGLOutOfMemoryException(String message) {
		super(message);
	}

	public OpenGLOutOfMemoryException(Throwable throwable) {
		super(throwable);
	}

	public OpenGLOutOfMemoryException(String message, Throwable throwable) {
		super(message, throwable);
	}

}
