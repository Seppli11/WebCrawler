package seppli.ninja.webcrawler.web;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import seppli.ninja.webcrawler.crawler.settings.model.Selector;
import seppli.ninja.webcrawler.crawler.settings.model.Settings;
import seppli.ninja.webcrawler.crawler.settings.model.Site;
import seppli.ninja.webcrawler.db.model.Record;
import seppli.ninja.webcrawler.db.model.SiteTable;
import seppli.ninja.webcrawler.web.service.RecordService;
import seppli.ninja.webcrawler.web.service.SiteService;

/**
 * The record controller
 *
 * @author sebi
 * @version 1.0
 *
 */
@Controller
public class RecordController {
	/**
	 * logger
	 */
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * the record service
	 */
	private RecordService recordService;

	/**
	 * the site service
	 */
	private SiteService siteService;
	/**
	 * the settings
	 */
	private Settings settings;

	/**
	 * Constructor
	 *
	 * @param recordService the record service
	 * @param siteService   the site service
	 * @param settings      the settings
	 */
	@Autowired
	public RecordController(RecordService recordService, SiteService siteService, Settings settings) {
		this.recordService = recordService;
		this.siteService = siteService;
		this.settings = settings;
	}

	/**
	 * Shows all records of the site with the given id
	 *
	 * @param siteId the site id
	 * @param model  the model
	 * @return the template name
	 */
	@GetMapping("/records")
	public String getIndex(@RequestParam(name = "siteId") long siteId, Model model) {
		SiteTable siteTable = siteService.getByConfigId(siteId).orElse(new SiteTable(siteId));
		Site site = settings.getSiteById(siteId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid site id: " + siteId));

		List<Record> records = siteTable.getRecordList().stream()
				.sorted((r1, r2) -> Long.compare(r1.getDate().getTime(), r2.getDate().getTime()))
				.collect(Collectors.toList());
		model.addAttribute("site", site);
		model.addAttribute("titles",
				site.getMethod().getSelectors().stream().map(Selector::getName).collect(Collectors.toList()));
		model.addAttribute("records", records);
		model.addAttribute("controller", this);
		return "showRecords";
	}

	/**
	 * Deletes a record and redirects to the records page
	 * @param recordId the record to delete
	 * @param siteId the site id to redirect to the records page
	 * @return the redirect string
	 */
	@PostMapping("/deleteRecord")
	public String deleteRecord(@RequestParam(name = "recordId") long recordId, @RequestParam(name = "siteId") long siteId) {
		// delete if present
		recordService.get(recordId).ifPresent(recordService::delete);
		return "redirect: /records?siteId=" + siteId;
	}

}
