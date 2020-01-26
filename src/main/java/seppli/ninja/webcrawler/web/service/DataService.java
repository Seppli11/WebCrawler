package seppli.ninja.webcrawler.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import seppli.ninja.webcrawler.db.model.Data;
import seppli.ninja.webcrawler.db.repository.DataRepository;

/**
 * the data column db service
 * @author sebi
 * @version 1.0
 *
 */
@Service
public class DataService extends AbstractService<Data, Long, DataRepository>{
	/**
	 * Constructor
	 * @param repo the db repo
	 */
	@Autowired
	public DataService(DataRepository repo) {
		super(repo);
	}

}
