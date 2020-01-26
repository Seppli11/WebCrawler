package seppli.ninja.webcrawler.crawler;

import java.util.Map;

import seppli.ninja.webcrawler.crawler.settings.model.Method;
import seppli.ninja.webcrawler.exception.CrawlerException;

/**
 * the crawler interface
 * @author sebi
 * @version 1.0
 *
 * @param <T> the method type
 */
public interface Crawler<T extends Method> {
	/**
	 * Crawls the given url with the given method
	 * @param url the url
	 * @param m the method
	 * @return the map wit the data
	 */
	Map<String, String> crawl(String url, T m) throws CrawlerException;

	/**
	 * Returns the class of T. Has to be returned via method because java ereases the generics
	 * @return the class
	 */
	Class<T> getMethodClass();
}
