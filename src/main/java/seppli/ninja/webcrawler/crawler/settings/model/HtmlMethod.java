package seppli.ninja.webcrawler.crawler.settings.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

/**
 * the html settings method
 *
 * @author sebi
 * @version 1.0
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class HtmlMethod implements Method {
	/**
	 * the selectors
	 */
	@XmlElementWrapper(name = "selectors")
	@XmlElement(name = "selector")
	private List<Selector> selectors = new ArrayList<>();

	/**
	 * constructor
	 */
	public HtmlMethod() {
	}

	/**
	 * @return the selectors
	 */
	@Override
	public List<Selector> getSelectors() {
		return selectors;
	}

	/**
	 * @param selectors the selectors to set
	 */
	public void setSelectors(List<Selector> selectors) {
		this.selectors = selectors;
	}

	@Override
	public String toString() {
		return "HtmlMethod [selectors=" + selectors + "]";
	}

}
