package seppli.ninja.webcrawler.crawler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.select.Selector.SelectorParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import seppli.ninja.webcrawler.crawler.settings.model.HtmlMethod;
import seppli.ninja.webcrawler.crawler.settings.model.Selector;
import seppli.ninja.webcrawler.exception.CrawlerException;

/**
 * The html crawler gets the content of a given url and extracts bits using jsoup and css queries
 *
 * @author sebi
 * @version 1.0
 *
 */
public class HtmlCrawler implements Crawler<HtmlMethod> {
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 *
	 * Extracts all selectors in the {@link HtmlMethod} from the content of the given url
	 * @param url the url
	 * @param m the html method
	 * @returns the result
	 */
	@Override
	public Map<String, String> crawl(String url, HtmlMethod m) throws CrawlerException {
		Document doc;
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			throw new CrawlerException("Couldn't get content", e);
		}
		Map<String, String> map = new HashMap<>();
		if (m.getSelectors().isEmpty()) {
			logger.info("No selectors defined for page \"{}\"", url);
		}
		for (Selector selector : m.getSelectors()) {
			try {
				Elements values = doc.select(selector.getSelector());
				if (!values.isEmpty()) {
					String value = values.eachText().stream().collect(Collectors.joining(", "));
					map.put(selector.getName(), value);
					logger.info("Found \"{}\" -> \"{}\"", selector.getName(), value);
				} else {
					logger.warn("No content matched css query \"{}\" on page \"{}\" (selector name: \"{}\")",
							selector.getSelector(), url, selector.getName());
				}
			} catch (SelectorParseException e) {
				logger.error("Coulnd't parse selector", e);
			}
		}
		return map;
	}

	@Override
	public Class<HtmlMethod> getMethodClass() {
		return HtmlMethod.class;
	}

}
