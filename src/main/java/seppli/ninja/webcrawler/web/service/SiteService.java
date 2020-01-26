package seppli.ninja.webcrawler.web.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import seppli.ninja.webcrawler.crawler.settings.model.Selector;
import seppli.ninja.webcrawler.crawler.settings.model.Site;
import seppli.ninja.webcrawler.db.model.Data;
import seppli.ninja.webcrawler.db.model.Record;
import seppli.ninja.webcrawler.db.model.SiteTable;
import seppli.ninja.webcrawler.db.repository.SiteRepository;

/**
 * The site service
 *
 * @author sebi
 * @version 1.0
 *
 */
@Service
public class SiteService extends AbstractService<SiteTable, Long, SiteRepository> {

	/**
	 * Constructor
	 *
	 * @param repo the repo
	 */
	@Autowired
	public SiteService(SiteRepository repo) {
		super(repo);
	}

	/**
	 * Returns the config found by the config id
	 *
	 * @param configId the id
	 * @return the site optional
	 */
	public Optional<SiteTable> getByConfigId(long configId) {
		return getRepo().findByConfigId(configId);
	}

	/**
	 * Converts the site table and the site obj into a csv
	 * @param table the table object
	 * @param site the site object
	 * @return the csv string
	 */
	public String getCsv(SiteTable table, Site site) {
		StringBuffer buffer = new StringBuffer();
		String[] headers = site.getMethod().getSelectors().stream().map(Selector::getName).toArray(String[]::new);
		buffer.append("\"Date\";");
		for (String header : headers) {
			buffer.append("\"" + header + "\";");
		}
		buffer.append("\n");
		for (Record record : table.getRecordList()) {
			buffer.append("\"" + record.getDate() + "\";");
			for (String header : headers) {
				String value = record.getDataList().stream()
						.filter(d -> header.equals(d.getColumnName()))
						.map(Data::getValue)
						.findAny()
						.orElse("");
				buffer.append("\"" + value + "\";");
			}
			buffer.append("\n");
		}
		return buffer.toString();
	}

}
