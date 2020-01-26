package seppli.ninja.webcrawler.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * The java class for the data db table
 * @author sebi
 * @version 1.0
 *
 */
@Entity
@Table(name = "Data")
public class Data {
	/**
	 * the primary key
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	/**
	 * the record to which it belongs
	 */
	@ManyToOne
	private Record record;

	/**
	 * the name of the data
	 */
	private String columnName;

	/**
	 * the data
	 */
	@Column(columnDefinition = "TEXT")
	private String value;

	/**
	 * Constructor
	 */
	public Data() {
	}

	/**
	 * Constructor
	 * @param record the record to which this data belongs
	 * @param columnName the column name
	 * @param value the value of the data
	 */
	public Data(Record record, String columnName, String value) {
		this.record = record;
		this.columnName = columnName;
		this.value = value;
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
	 * @return the record
	 */
	public Record getRecord() {
		return record;
	}

	/**
	 * @param record the record to set
	 */
	public void setRecord(Record record) {
		this.record = record;
	}

	/**
	 * @return the columnName
	 */
	public String getColumnName() {
		return columnName;
	}

	/**
	 * @param columnName the columnName to set
	 */
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

}
