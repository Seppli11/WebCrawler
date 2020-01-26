package seppli.ninja.webcrawler.web.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

/**
 * An abstract service which implements the basic get all get by id store and delete operations
 *
 * @author sebi
 * @version 1.0
 *
 * @param <T> the type of the service object
 * @param <ID> the type of the id
 * @param <REPO> the type of the crud repo
 */
public abstract class AbstractService<T, ID, REPO extends CrudRepository<T, ID>> {
	/**
	 * the db service
	 */
	private REPO repo;

	/**
	 * Constructor
	 * @param repo the db repo
	 */
	public AbstractService(REPO repo) {
		this.repo = repo;
	}

	/**
	 * Returns all objects found
	 * @return all objects
	 */
	public List<T> getAll() {
		List<T> records = new ArrayList<>();
		repo.findAll().forEach(records::add);
		return records;
	}

	/**
	 * Returns the object
	 * @param id the id
	 * @return the object
	 */
	public Optional<T> get(ID id) {
		return repo.findById(id);
	}

	/**
	 * Stores the given object in the db or updates it
	 * @param obj the object
	 * @return the object which should be used from now on
	 */
	public T save(T obj) {
		return repo.save(obj);
	}

	/**
	 * Deletes the given object
	 * @param obj the object
	 */
	public void delete(T obj) {
		repo.delete(obj);
	}

	/**
	 * Returns the crud repository
	 * @return the repostiory
	 */
	protected REPO getRepo() {
		return repo;
	}
}
