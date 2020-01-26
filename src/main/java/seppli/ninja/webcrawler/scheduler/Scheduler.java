package seppli.ninja.webcrawler.scheduler;

import java.util.Date;
import java.util.Optional;
import java.util.PriorityQueue;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import seppli.ninja.webcrawler.crawler.CrawlerExecutor;
import seppli.ninja.webcrawler.crawler.settings.model.Site;

/**
 * The class which schedules the crawlers. It is thread save and can be called
 * from anywhere (regarding the thread)
 *
 * @author sebi
 * @version 1.0
 *
 */
@Service
public class Scheduler {

	/**
	 * logger
	 */
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * the timer thread which notifies
	 */
	private Thread timer = new Thread();

	/**
	 * the executor
	 */
	private TaskExecutor taskExecutor;

	/**
	 * if it is still running
	 */
	private boolean running;

	/**
	 * the scheduled runs
	 */
	private PriorityQueue<SchedulePair> schedule = new PriorityQueue<>();

	/**
	 * the notifier object to notify the main thread
	 */
	private Object notifier = new Object();

	/**
	 * Crawler executor
	 */
	private CrawlerExecutor executor;

	/**
	 * Constructor
	 *
	 * @param executor the executor to use
	 */
	@Autowired
	public Scheduler(CrawlerExecutor executor, TaskExecutor taskExecutor) {
		this.executor = executor;
		this.taskExecutor = taskExecutor;
	}

	/**
	 * Called by spring after the object was created.<br>
	 * Starts the main thread
	 */
	@PostConstruct
	private void postConstruct() {
		start();
	}

	/**
	 * Adds a method to be executed once
	 *
	 * @param time the timestamp when it should be executed
	 * @param site the site to execute
	 */
	public void addOneShot(long time, Site site) {
		addReocurring(time, site, null);
	}

	/**
	 * Adds a recurring schedule
	 *
	 * @param first     the first invocation
	 * @param site      the site for the crawler
	 * @param generator the generator which generates the next scheduled time
	 */
	public void addReocurring(long first, Site site, ScheduleGenerator generator) {
		synchronized (schedule) {
			schedule.add(new SchedulePair(site, generator, first));
			findNextExecutionOf(site).ifPresent(
					date -> logger.info("Next execution of site \"{}\" is at {}", site.getUrl(), date));
			long timeToNext = getTimeToNext();
			// stops timer in case the added schedule is the first element
			timer.interrupt();
			// and restarts it
			notifyIn(timeToNext);
		}
	}

	/**
	 * Returns the time delta between now and the next schedule
	 *
	 * @return the time delta
	 */
	private long getTimeToNext() {
		synchronized (schedule) {
			if (schedule.isEmpty()) {
				throw new IllegalStateException("getTimeToNext() was called, but no schedule is empty");
			}
			return schedule.peek().nextRun - System.currentTimeMillis();
		}
	}

	/**
	 * Start the main thread. If it is already running then the function just
	 * returns
	 */
	public void start() {
		if (running) {
			logger.warn("Schedler is already running");
			return;
		}

		taskExecutor.execute(this::run);
		running = true;
	}

	/**
	 * main thread entry method
	 */
	private void run() {
		while (running) {
			// check if queue is empty synchronized
			boolean empty = false;
			synchronized (schedule) {
				empty = schedule.isEmpty();
			}
			// if queue is empty, wait unit it is filled
			if (empty) {
				try {
					synchronized (notifier) {
						// wait for new task
						notifier.wait();
					}
				} catch (InterruptedException e) {
					logger.warn("Main thread was interupted -> stop thread", e);
					break;
				}
			}

			long timeToNext = getTimeToNext();
			// if it is smaller then 50ms then the notifyIn might come before the wait() and
			// it won't matter for 50ms
			if (timeToNext > 50) {
				notifyIn(timeToNext);
				try {
					synchronized (notifier) {
						// wait for timer
						notifier.wait();
					}
				} catch (InterruptedException e) {
					logger.warn("Main thread was interupted -> stop thread", e);
					break;
				}
			}

			// execute the current crawler
			executeCrawler(schedule.poll());
		}
	}

	/**
	 * Executes the crawler from a {@link SchedulePair}
	 * @param pair the pair
	 */
	private void executeCrawler(SchedulePair pair) {
		// execute crawler from pair
		executor.execute(pair.site);

		//generate next time if the generator isn't null
		ScheduleGenerator generator = pair.generator;
		if (generator != null) {
			// if it is reocurring schedule, readd it to the schedule list
			long nextRun = generator.nextRun(pair.nextRun);
			// has to be done directly on the list, because else a notify would be triggered
			schedule.add(new SchedulePair(pair.site, generator, nextRun));
		}
		// print nice message, when the next execution is going to be be
		findNextExecutionOf(pair.site).ifPresent(
				date -> logger.info("Next execution of site \"{}\" is at {}", pair.site.getUrl(), date));

	}

	/**
	 * Finds the next exection of the given site
	 * @param s the site
	 * @return the optional
	 */
	public Optional<Date> findNextExecutionOf(Site s) {
		// syncronize with schedule
		synchronized (schedule) {
			return schedule.stream().filter(p -> p.site.getId() == s.getId()).map(p -> new Date(p.nextRun)).findFirst();
		}
	}

	/**
	 * Starts a timer thread with the given time
	 *
	 * @param time the time delta in ms when the main thread should be awaken
	 */
	private void notifyIn(long time) {
		if (timer.isAlive()) {
			// if timer is already running -> interupting it
			timer.interrupt();
		}
		// if time is smaller than 50 -> call main thread immediately
		if (time < 50) {
			synchronized (notifier) {
				notifier.notify();
			}
			return;
		}
		timer = new Thread(() -> {
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {
				logger.info("notifier thread was interupted");
				// don't notify the main thread, because it was already called
				return;
			}
			synchronized (notifier) {
				// notify the main thread
				notifier.notify();
			}
		});
		timer.start();

	}

	/**
	 * A internal data class (and thus not having getters or setters) for storing the genrator. The implemented
	 * {@link Comparable} compares the next run.
	 *
	 *
	 * @author sebi
	 * @version 1.0
	 *
	 */
	private class SchedulePair implements Comparable<SchedulePair> {
		private Site site;
		private ScheduleGenerator generator;
		private long nextRun;

		/**
		 * Constructor
		 *
		 * @param site      the site
		 * @param generator the generator
		 * @param nextRun   the next run
		 */
		public SchedulePair(Site site, ScheduleGenerator generator, long nextRun) {
			this.site = site;
			this.generator = generator;
			this.nextRun = nextRun;
		}

		@Override
		public int compareTo(SchedulePair o) {
			if (o == null) {
				return 1;
			}
			if (nextRun < o.nextRun) {
				return -1;
			}
			if (nextRun > o.nextRun) {
				return 1;
			}
			return 0;
		}

	}

}
