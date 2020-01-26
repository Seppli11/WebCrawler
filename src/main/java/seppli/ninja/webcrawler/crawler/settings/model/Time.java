package seppli.ninja.webcrawler.crawler.settings.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

/**
 * the time settings object
 * @author sebi
 * @version 1.0
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Time {
	@XmlValue
	private String time;

	/**
	 * Empty Constructor for jaxb
	 */
	public Time() {
	}

	/**
	 * Constructor
	 * @param time the time when it should be executed
	 */
	public Time(String time) {
		this.time = time;
	}

	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}

}
