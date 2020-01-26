package seppli.ninja.webcrawler.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import seppli.ninja.webcrawler.db.model.Record;
import seppli.ninja.webcrawler.db.repository.RecordRepository;

/**
 * the record service
 * @author sebi
 * @version 1.0
 *
 */
@Service
public class RecordService extends AbstractService<Record, Long, RecordRepository>{

	/**
	 * Constructor
	 * @param recordRepo the db repo
	 */
	@Autowired
	public RecordService(RecordRepository recordRepo) {
		super(recordRepo);
	}

}
