package seppli.ninja.webcrawler.db.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import seppli.ninja.webcrawler.crawler.settings.model.Site;

/**
 * The db class for representing an {@link Site} object in the db. It uses the
 * config id for that
 * @author sebi
 * @version 1.0
 *
 */
@Entity
@Table(name = "Site")
public class SiteTable {
	/**
	 * the site table id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	/**
	 * the records
	 */
	@OneToMany(mappedBy = "site", cascade = CascadeType.REMOVE)
	private List<Record> recordList;

	/**
	 * the config id
	 */
	private long configId;


	public SiteTable() {
	}

	/**
	 * Constructor
	 * @param configId the config id
	 */
	public SiteTable(long configId) {
		this.configId = configId;
	}



	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the recordList
	 */
	public List<Record> getRecordList() {
		return recordList;
	}

	/**
	 * @param recordList the recordList to set
	 */
	public void setRecordList(List<Record> recordList) {
		this.recordList = recordList;
	}

	/**
	 * @return the configId
	 */
	public long getConfigId() {
		return configId;
	}

	/**
	 * @param configId the configId to set
	 */
	public void setConfigId(long configId) {
		this.configId = configId;
	}

}
