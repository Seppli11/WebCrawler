package seppli.ninja.webcrawler.scheduler.generator;

import java.time.ZonedDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cronutils.model.Cron;
import com.cronutils.model.CronType;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.model.time.ExecutionTime;
import com.cronutils.parser.CronParser;

import seppli.ninja.webcrawler.crawler.settings.model.Time;
import seppli.ninja.webcrawler.scheduler.ScheduleGenerator;

/**
 * An implementation of the {@link ScheduleGenerator} for the time format HH:MM:SS or HH:MM (or just the hour)
 * @author sebi
 * @version 1.0
 *
 */
public class TimeScheduleGenerator implements ScheduleGenerator {
	/**
	 * The delimiter used for splitting the time
	 */
	private static final String DELIMITER = ":";

	/**
	 * logger
	 */
	private Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * the time object
	 */
	private Time time;

	/**
	 * the cron parser
	 */
	private CronParser cronParser;

	/**
	 * the cron instance
	 */
	private Cron cron;

	/**
	 * Constructor
	 *
	 * @param time
	 */
	public TimeScheduleGenerator(Time time) {
		this.time = time;
		this.cronParser = new CronParser(CronDefinitionBuilder.instanceDefinitionFor(CronType.SPRING));
		this.cron = this.cronParser.parse(time.getTime());
	}

	@Override
	public long nextRun(long lastRun) {
		ZonedDateTime time = ExecutionTime.forCron(cron).nextExecution(ZonedDateTime.now()).orElse(null);
		if(time == null) {
			return Long.MAX_VALUE;
		}
		return time.toEpochSecond() * 1000;
	}

	/**
	 * @return the time
	 */
	public Time getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(Time time) {
		this.time = time;
	}

}
