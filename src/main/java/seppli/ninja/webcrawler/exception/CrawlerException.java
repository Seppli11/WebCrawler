package seppli.ninja.webcrawler.exception;

/**
 * An exception which is thrown from crawlers
 * @author sebi
 * @version 1.0
 *
 */
public class CrawlerException extends Exception {

	/**
	 * the serial version uid
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * @param message the message
	 * @param cause the cause
	 */
	public CrawlerException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor
	 * @param message the message
	 */
	public CrawlerException(String message) {
		super(message);
	}


}
