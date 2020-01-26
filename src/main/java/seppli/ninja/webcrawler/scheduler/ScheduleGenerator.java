package seppli.ninja.webcrawler.scheduler;

/**
 * An interface for generating the next time to run a task
 * @author sebi
 * @version 1.0
 *
 */
public interface ScheduleGenerator {
	/**
	 * Generates the timestamp of the next run
	 * @param lastRun the timestamp of the last run
	 * @return the timestamp of the next run
	 */
	long nextRun(long lastRun);
}
