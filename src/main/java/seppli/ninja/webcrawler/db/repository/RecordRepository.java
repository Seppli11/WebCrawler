package seppli.ninja.webcrawler.db.repository;

import org.springframework.data.repository.CrudRepository;

import seppli.ninja.webcrawler.db.model.Record;

/**
 * the record crud repo
 * @author sebi
 * @version 1.0
 *
 */
public interface RecordRepository extends CrudRepository<Record, Long>{

}
