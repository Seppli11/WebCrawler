package seppli.ninja.webcrawler.crawler;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import seppli.ninja.webcrawler.crawler.settings.model.Method;
import seppli.ninja.webcrawler.crawler.settings.model.Site;
import seppli.ninja.webcrawler.db.model.Data;
import seppli.ninja.webcrawler.db.model.Record;
import seppli.ninja.webcrawler.db.model.SiteTable;
import seppli.ninja.webcrawler.exception.CrawlerException;
import seppli.ninja.webcrawler.web.service.DataService;
import seppli.ninja.webcrawler.web.service.RecordService;
import seppli.ninja.webcrawler.web.service.SiteService;

/**
 * The cralwer executor executes the crawlers and stores the result in the db
 * @author sebi
 * @version 1.0
 *
 */
@Service
public class CrawlerExecutor {
	/**
	 * logger
	 */
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * the crawler manager
	 */
	private CrawlerManager manager;

	/**
	 * the site service
	 */
	private SiteService siteService;

	/**
	 * the record service
	 */
	private RecordService recordService;

	/**
	 * the datacolumn service
	 */
	private DataService dataService;

	/**
	 * Constructor
	 * @param manager the manager to use
	 * @param siteService the site service
	 * @param recordService the record service for the db
	 * @param dataColumnService  the data column service
	 */
	@Autowired
	public CrawlerExecutor(CrawlerManager manager, SiteService siteService, RecordService recordService, DataService dataColumnService) {
		this.manager = manager;
		this.siteService = siteService;
		this.recordService = recordService;
		this.dataService = dataColumnService;
	}



	/**
	 * Finds and executes the crawler for the given site and stores the result in the db
	 * @param site the site
	 */
	@SuppressWarnings("rawtypes")
	public void execute(Site site) {
		Method m = site.getMethod();
		Crawler crawler = manager.getCrawler(m.getClass());

		if (crawler == null) {
			logger.error("No crawler found for method \"{}\"", m.getClass().getName());
			return;
		}
		logger.info("Execute crawler {} for url \"{}\" with method {}", crawler.getClass().getName(), site.getUrl(),
				m.getClass().getName());
		try {
			Map<String, String> result = crawler.crawl(site.getUrl(), m);
			if (result == null) {
				logger.error("The crawler \"{}\" returned null", crawler.getClass().getName());
			}
			storeInDb(site, result);
		} catch (CrawlerException e) {
			logger.error("While crawling an error occured (url: \"{}\", crawler: {}, method: {})", site.getUrl(),
					crawler.getClass().getName(), m.getClass().getName());
		}
	}

	/**
	 * Stores the given map in the db
	 * @param site the site for which the result should be saved
	 * @param map the result them self
	 */
	private void storeInDb(Site site, Map<String, String> map) {
		// gets a site table form the site
		SiteTable siteTable = getSiteTable(site);
		Record record = new Record(siteTable, site.getUrl(), new Date());
		recordService.save(record);
		// saves every entry in the map as an Data object and adds it to the record object
		for(String key : map.keySet()) {
			// key is null -> skip
			if(key == null) {
				continue;
			}
			Data data = new Data(record, key, map.get(key));
			dataService.save(data);
			record.getDataList().add(data);
		}
	}



	/**
	 * Returns the already exiting site table or creating a new one, if it dosn't exist yet
	 * @param site the site the site of the site table
	 * @return the site object
	 */
	private SiteTable getSiteTable(Site site) {
		SiteTable siteTable = siteService.getByConfigId(site.getId()).orElse(null);
		if(siteTable == null) {
			siteTable = siteService.save(new SiteTable(site.getId()));
		}
		return siteTable;
	}
}
