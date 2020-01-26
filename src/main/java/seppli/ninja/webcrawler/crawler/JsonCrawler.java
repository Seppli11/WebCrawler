package seppli.ninja.webcrawler.crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jayway.jsonpath.JsonPath;

import seppli.ninja.webcrawler.crawler.settings.model.HtmlMethod;
import seppli.ninja.webcrawler.crawler.settings.model.JsonMethod;
import seppli.ninja.webcrawler.crawler.settings.model.Selector;
import seppli.ninja.webcrawler.exception.CrawlerException;

/**
 * The json crawler gets the content of a given url and extracts bits using json
 * paths
 *
 * @author sebi
 * @version 1.0
 *
 */
public class JsonCrawler implements Crawler<JsonMethod> {
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 *
	 * Extracts all selectors in the {@link HtmlMethod} from the content of the
	 * given url
	 *
	 * @param url the url
	 * @param m   the html method
	 * @returns the result
	 */
	@Override
	public Map<String, String> crawl(String url, JsonMethod m) throws CrawlerException {
		Map<String, String> map = new HashMap<>();
		if (m.getSelectors().isEmpty()) {
			logger.info("No selectors defined for page \"{}\"", url);
		}

		try {
			URL urlObj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
			con.setRequestMethod(m.getRequestMethod().name());
			con.connect();

			BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String jsonText = reader.lines().collect(Collectors.joining("\n"));

			for (Selector selector : m.getSelectors()) {
				String value = JsonPath.read(jsonText, selector.getSelector()).toString();
				map.put(selector.getName(), value);
			}
			return map;
		} catch (IOException e) {
			throw new CrawlerException("an io exception occured: ", e);
		}
	}

	@Override
	public Class<JsonMethod> getMethodClass() {
		return JsonMethod.class;
	}

}
