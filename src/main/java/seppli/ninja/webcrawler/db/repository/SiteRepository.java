package seppli.ninja.webcrawler.db.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import seppli.ninja.webcrawler.db.model.SiteTable;

/**
 * the site crud repo
 * @author sebi
 * @version 1.0
 *
 */
public interface SiteRepository extends CrudRepository<SiteTable, Long>{
	/**
	 * Returns site reposittroy with the given config id
	 * @param configId the config id
	 * @return the site table optional
	 */
	Optional<SiteTable> findByConfigId(Long configId);
}
