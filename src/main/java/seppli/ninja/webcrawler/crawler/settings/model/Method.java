package seppli.ninja.webcrawler.crawler.settings.model;

import java.util.List;

/**
 * Empty interface for crawler for json and html method
 * @author sebi
 * @version 1.0
 *
 */
public interface Method {
	/**
	 * to the string which is shown in the view
	 * @return the html string
	 */
	default String toHtmlString() {
		return getClass().getSimpleName();
	}

	/**
	 * Returns the selectors
	 * @return the selectors
	 */
	List<Selector> getSelectors();
}
