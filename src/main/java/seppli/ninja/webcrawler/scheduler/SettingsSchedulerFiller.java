package seppli.ninja.webcrawler.scheduler;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import seppli.ninja.webcrawler.crawler.settings.model.Settings;
import seppli.ninja.webcrawler.crawler.settings.model.Site;
import seppli.ninja.webcrawler.crawler.settings.model.Time;
import seppli.ninja.webcrawler.scheduler.generator.TimeScheduleGenerator;

/**
 * A class which sets up the recurring schedules from the settings
 *
 * @author sebi
 * @version 1.0
 *
 */
@Service
public class SettingsSchedulerFiller {
	/**
	 * logger
	 */
	private Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * the settings
	 */
	private Settings settings;

	/**
	 * the scheduler
	 */
	private Scheduler scheduler;

	/**
	 * Constructor
	 *
	 * @param settings  the settings
	 * @param scheduler the scheduler
	 */
	@Autowired
	public SettingsSchedulerFiller(Settings settings, Scheduler scheduler) {
		this.settings = settings;
		this.scheduler = scheduler;
	}

	/**
	 * Filles the scheduler with the sites from the settings
	 */
	@PostConstruct
	private void postConstruct() {
		logger.info("add sites to scheduler");
		for (Site site : settings.getSites()) {
			logger.info("add site \"{}\" (method: {})", site.getUrl(), site.getMethod().getClass().getName());
			for (Time t : site.getCheckAt()) {
				ScheduleGenerator gen = new TimeScheduleGenerator(t);
				scheduler.addReocurring(gen.nextRun(0), site, gen);
			}
		}
	}
}
