package seppli.ninja.webcrawler.crawler;

import java.util.HashMap;
import java.util.Map;

import seppli.ninja.webcrawler.crawler.settings.model.Method;
import seppli.ninja.webcrawler.exception.MethodClassAlreadyExistsException;

/**
 * A class which manages the crawlers
 * @author sebi
 * @version 1.0
 *
 */
public class CrawlerManager {
	/**
	 * the registered crawlers
	 */
	private Map<Class<? extends Method>, Crawler<?>> crawlers = new HashMap<>();

	/**
	 * Adds an crawler to the manager
	 * @param crawler the crawler
	 * @throws MethodClassAlreadyExistsException if the class of the crawler already exists
	 */
	public void addCrawler(Crawler<?> crawler) throws MethodClassAlreadyExistsException {
		Class<? extends Method> methodClass = crawler.getMethodClass();
		if(crawlers.containsKey(methodClass)) {
			throw new MethodClassAlreadyExistsException("The class \"" + methodClass.getName() + "\" already exists");
		}

		crawlers.put(methodClass, crawler);
	}

	/**
	 * Checks if a crawler with the given class was already registered
	 * @param klass the class to check for
	 * @return if one was registered or not
	 */
	public boolean isCrawlerRegistered(Class<?> klass) {
		return crawlers.containsKey(klass);
	}

	/**
	 * Returns the crawler for its respective class or null if no crawler could be found
	 * @param <T> the type of the {@link Method} class of the crawler
	 * @param klass the class to search for
	 * @return the crawler or null if no crawler was registered
	 */
	@SuppressWarnings("unchecked")
	public <T extends Method> Crawler<T> getCrawler(Class<T> klass) {
		//is fine because the crawler can only be registered with its class
		return (Crawler<T>) crawlers.get(klass);
	}
}
