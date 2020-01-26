package seppli.ninja.webcrawler.crawler.settings.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

/**
 * the json method settings
 * @author sebi
 * @version 1.0
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class JsonMethod implements Method {
	/**
	 * the selectors
	 */
	@XmlElementWrapper(name = "selectors")
	@XmlElement(name = "selector")
	private List<Selector> selectors = new ArrayList<>();
	/**
	 * the request method
	 */
	private RequestMethod requestMethod;

	/**
	 * Constructor
	 */
	public JsonMethod() {
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

	/**
	 * @return the requestMethod
	 */
	public RequestMethod getRequestMethod() {
		return requestMethod;
	}

	/**
	 * @param requestMethod the requestMethod to set
	 */
	public void setRequestMethod(RequestMethod requestMethod) {
		this.requestMethod = requestMethod;
	}


}
