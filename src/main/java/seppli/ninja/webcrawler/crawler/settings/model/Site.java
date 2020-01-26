package seppli.ninja.webcrawler.crawler.settings.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * the site
 *
 * @author sebi
 * @version 1.0
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Site {
	/**
	 * the id or null if it wasn't set
	 */
	@XmlElement(name = "id", defaultValue = "null", nillable = true)
	private Long id;

	/**
	 * the urls
	 */
	private String url;
	/**
	 * the times when the crawler should do something
	 */
	@XmlElementWrapper(name = "checkAt")
	@XmlElement(name = "time")
	private List<Time> checkAt = new ArrayList<>();
	/**
	 * if the html crawler should be used
	 */
	@XmlElement(name = "htmlMethod")
	private HtmlMethod htmlMethod;
	/**
	 * the the json crawler should be used
	 */
	@XmlElement(name = "jsonMethod")
	private JsonMethod jsonMethod;

	/**
	 * Constructor
	 */
	public Site() {
	}

	/**
	 * @return the id or if it isn't set yet, the min value
	 */
	public long getId() {
		if(id == null) {
			return Long.MIN_VALUE;
		}
		return id;
	}

	/**
	 * @param id the id to set
	 */
	protected void setId(long id) {
		this.id = id;
	}

	/**
	 * Checks if the id isn't null
	 * @return if the id is set
	 */
	public boolean isIdDefinied() {
		return this.id != null;
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
	 * @return the checkAt
	 */
	public List<Time> getCheckAt() {
		return checkAt;
	}

	/**
	 * @param checkAt the checkAt to set
	 */
	public void setCheckAt(List<Time> checkAt) {
		this.checkAt = checkAt;
	}

	/**
	 * @return the htmlMethod
	 */
	public HtmlMethod getHtmlMethod() {
		return htmlMethod;
	}

	/**
	 * @param htmlMethod the htmlMethod to set
	 */
	public void setHtmlMethod(HtmlMethod htmlMethod) {
		this.htmlMethod = htmlMethod;
	}

	/**
	 * @return the jsonMethod
	 */
	public JsonMethod getJsonMethod() {
		return jsonMethod;
	}

	/**
	 * @param jsonMethod the jsonMethod to set
	 */
	public void setJsonMethod(JsonMethod jsonMethod) {
		this.jsonMethod = jsonMethod;
	}

	/**
	 * @return returns the method which was set
	 */
	public Method getMethod() {
		if(getJsonMethod() != null) {
			return getJsonMethod();
		}
		if(getHtmlMethod() != null) {
			return getHtmlMethod();
		}
		throw new IllegalStateException("neither the json nor the html method are set");

	}

	@Override
	public String toString() {
		return "Site [url=" + url + ", checkAt=" + checkAt + ", htmlMethod=" + htmlMethod + ", jsonMethod=" + jsonMethod
				+ "]";
	}

}
