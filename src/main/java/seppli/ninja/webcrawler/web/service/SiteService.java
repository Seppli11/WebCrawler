package seppli.ninja.webcrawler.web.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import seppli.ninja.webcrawler.db.model.SiteTable;
import seppli.ninja.webcrawler.db.repository.SiteRepository;

/**
 * The site service
 * @author sebi
 * @version 1.0
 *
 */
@Service
public class SiteService extends AbstractService<SiteTable, Long, SiteRepository>{

	/**
	 * Constructor
	 * @param repo the repo
	 */
	@Autowired
	public SiteService(SiteRepository repo) {
		super(repo);
	}


	/**
	 * Returns the config found by the config id
	 * @param configId the id
	 * @return the site optional
	 */
	public Optional<SiteTable> getByConfigId(long configId) {
		return getRepo().findByConfigId(configId);
	}

}
