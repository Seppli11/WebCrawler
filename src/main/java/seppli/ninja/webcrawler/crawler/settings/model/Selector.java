package seppli.ninja.webcrawler.crawler.settings.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

/**
 * a seletor
 * @author sebi
 * @version 1.0
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Selector {
	/**
	 * the selector name
	 */
	@XmlAttribute(required = true)
	private String name;

	/**
	 * the acutal selector
	 */
	@XmlValue
	private String selector;

	/**
	 * Constructor
	 */
	public Selector() {
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the selector
	 */
	public String getSelector() {
		return selector;
	}

	/**
	 * @param selector the selector to set
	 */
	public void setSelector(String selector) {
		this.selector = selector;
	}

	@Override
	public String toString() {
		return "Selector [name=" + name + ", selector=" + selector + "]";
	}


}
