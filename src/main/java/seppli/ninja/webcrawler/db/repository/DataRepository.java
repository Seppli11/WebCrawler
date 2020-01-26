package seppli.ninja.webcrawler.db.repository;

import org.springframework.data.repository.CrudRepository;

import seppli.ninja.webcrawler.db.model.Data;

/**
 * The repo for the {@link DataColumn}
 *
 * @author sebi
 * @version 1.0
 *
 */
public interface DataRepository extends CrudRepository<Data, Long> {
}
