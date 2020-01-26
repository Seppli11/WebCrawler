package seppli.ninja.webcrawler.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import seppli.ninja.webcrawler.crawler.settings.io.SiteWriter;
import seppli.ninja.webcrawler.crawler.settings.model.Settings;
import seppli.ninja.webcrawler.crawler.settings.model.Site;
import seppli.ninja.webcrawler.crawler.settings.model.Time;
import seppli.ninja.webcrawler.scheduler.Scheduler;

/**
 * The site controller
 * @author sebi
 * @version 1.0
 *
 */
@Controller
public class SitesController {
	/**
	 * logger
	 */
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * the settigns
	 */
	private Settings settings;
	/**
	 * the scheduler
	 */
	private Scheduler scheduler;

	/**
	 * the date format to format th enext run time
	 */
	private SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd.MM.yyyy");

	/**
	 * Constructor
	 *
	 * @param settings the settings
	 * @param scheduler the scheduler
	 */
	@Autowired
	public SitesController(Settings settings, Scheduler scheduler) {
		this.settings = settings;
		this.scheduler = scheduler;
	}

	/**
	 * Shows all sites
	 * @param model the model
	 * @return the template name
	 */
	@GetMapping("/")
	public String getIndex(Model model) {

		model.addAttribute("sites", settings.getSites());
		model.addAttribute("controller", this);
		return "showSites";
	}

	/**
	 * Exports the site config
	 * @param id the id
	 * @param response the http response
	 * @throws JAXBException
	 * @throws IOException
	 */
	@GetMapping("/exportConfig")
	public void exportConfig(@RequestParam(name = "siteId") long id, HttpServletResponse response) throws JAXBException, IOException {
		Site site = settings.getSiteById(id).orElseThrow(() -> new IllegalArgumentException("Invalid site id: " + id));
		new SiteWriter().write(site, response.getOutputStream());
		response.setContentType("application/xml");
		response.setHeader("Content-Disposition", "attachment; filename=\"siteConfig" + id + ".xml\"");
		response.flushBuffer();
	}

	/**
	 * Formats the time list<br>
	 * Used from the template
	 * @param times the times
	 * @return formated as strings
	 */
	public String formatTimeList(List<Time> times) {
		return times.stream().map(Time::getTime).collect(Collectors.joining(", "));
	}

	/**
	 * Formats the date from the next execution<br>
	 * called from the template
	 * @param site the site
	 * @return the formated date or "-" if no exection could be found
	 */
	public String getNextRunDate(Site site) {
		Date d = scheduler.findNextExecutionOf(site).orElse(null);
		if(d == null) {
			return "-";
		}
		return formatDate(d);
	}

	/**
	 * Formats the given date
	 * @param d the date to format
	 * @return the string
	 */
	public String formatDate(Date d) {
		return dateFormat.format(d);
	}
}
