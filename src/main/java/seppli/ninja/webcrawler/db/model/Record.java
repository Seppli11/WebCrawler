package seppli.ninja.webcrawler.db.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The record db class for storing a record
 * @author sebi
 * @version 1.0
 *
 */
@Entity
@Table(name = "Record")
public class Record {
	/**
	 * the id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	/**
	 * the site table to which this record belongs
	 */
	@ManyToOne
	private SiteTable site;

	/**
	 * All data objects which belong to this record
	 */
	@OneToMany(mappedBy = "record", cascade = CascadeType.REMOVE)
	private List<Data> dataList = new ArrayList<>();

	/**
	 * the url used
	 */
	private String url;

	/**
	 * when it was recored
	 */
	private Date date;

	/**
	 * Constructor
	 */
	public Record() {
	}

	/**
	 * Constructor
	 * @param site the site
	 * @param dataColumnList the columns
	 * @param url the url
	 * @param date the date
	 */
	public Record(SiteTable site, String url, Date date) {
		this.site = site;
		this.url = url;
		this.date = date;
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
	 * @return the site
	 */
	public SiteTable getSite() {
		return site;
	}

	/**
	 * @param site the site to set
	 */
	public void setSite(SiteTable site) {
		this.site = site;
	}

	/**
	 * @return the dataColumnList
	 */
	public List<Data> getDataList() {
		return dataList;
	}

	/**
	 * @param dataColumnList the dataList to set
	 */
	public void setDataList(List<Data> dataList) {
		this.dataList = dataList;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

}
