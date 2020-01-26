package seppli.ninja.webcrawler;

import java.io.File;

import javax.xml.bind.JAXBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.context.annotation.ApplicationScope;

import seppli.ninja.webcrawler.crawler.CrawlerManager;
import seppli.ninja.webcrawler.crawler.HtmlCrawler;
import seppli.ninja.webcrawler.crawler.JsonCrawler;
import seppli.ninja.webcrawler.crawler.settings.io.SettingsReader;
import seppli.ninja.webcrawler.crawler.settings.io.SettingsWriter;
import seppli.ninja.webcrawler.crawler.settings.model.Settings;
import seppli.ninja.webcrawler.exception.MethodClassAlreadyExistsException;

/**
 * a configuration class to define beans
 * @author sebi
 * @version 1.0
 *
 */
@Configuration
public class AppConfiguration {
	/**
	 * logger
	 */
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * @return the inflated settings id (with ids)
	 */
	@SuppressWarnings("restriction")
	@Bean
	@ApplicationScope
	public Settings settingsBean() {
		try {
			File f = new File(SettingsWriter.defaultSettingsPath);
			Settings settigns = new SettingsReader(f).read();
			// inflates the settings (generates the ids)
			settigns.inflateIds();
			// writes the file back to preserve the generated ids
			new SettingsWriter(f).write(settigns);
			return settigns;
		} catch (com.sun.xml.internal.bind.v2.runtime.IllegalAnnotationsException e) {
			// prints jaxb errors
			logger.warn("Couldn't read settings because of", e);
			e.getErrors().forEach(Exception::printStackTrace);
		} catch (JAXBException e) {
			logger.warn("Couldn't read settings", e);
		}
		// default, in case an exception occured
		return new Settings();
	}

	/**
	 * @return the crawler manager wiht all supported crawlers added to it
	 * @throws MethodClassAlreadyExistsException
	 */
	@Bean
	@ApplicationScope
	public CrawlerManager crawlerManager() throws MethodClassAlreadyExistsException {
		CrawlerManager manager = new CrawlerManager();
		manager.addCrawler(new HtmlCrawler());
		manager.addCrawler(new JsonCrawler());
		return manager;
	}

	/**
	 * A task executor to solve the multi threading problems
	 * @return the executor
	 */
	@Bean
	public TaskExecutor threadPoolTaskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(2);
		executor.setMaxPoolSize(2);
		executor.initialize();
		return executor;
	}
}
