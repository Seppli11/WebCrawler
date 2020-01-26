package seppli.ninja.webcrawler.exception;

import seppli.ninja.webcrawler.crawler.CrawlerManager;

/**
 * When the method class is already registered in the {@link CrawlerManager}
 * @author sebi
 * @version 1.0
 *
 */
public class MethodClassAlreadyExistsException extends Exception {

	/**
	 * Constructor
	 * @param message the message
	 * @param cause the cause
	 */
	public MethodClassAlreadyExistsException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor the constructor
	 * @param message the message
	 */
	public MethodClassAlreadyExistsException(String message) {
		super(message);
	}



}
