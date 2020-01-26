package seppli.ninja.webcrawler.crawler.settings.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * the root settings class
 *
 * @author sebi
 * @version 1.0
 *
 */
@XmlRootElement(name = "settings")
@XmlAccessorType(XmlAccessType.FIELD)
public class Settings {
	/**
	 * the sites
	 */
	@XmlElementWrapper(name = "sites")
	@XmlElement(name = "site")
	private List<Site> sites = new ArrayList<>();

	/**
	 * Constructor
	 */
	public Settings() {
	}

	/**
	 * @return returns all sites
	 */
	public List<Site> getSites() {
		return sites;
	}

	/**
	 * Finds an site with the given id
	 * @param id the id
	 * @return the site optional
	 */
	public Optional<Site> getSiteById(long id) {
		return sites.stream().filter(s -> s.getId() == id).findAny();
	}

	/**
	 * sets all sites
	 * @param sites the sites
	 */
	public void setSites(List<Site> sites) {
		this.sites = sites;
	}

	/**
	 * Generates ids for the sites which dont' have yet
	 * @return this object to chain
	 */
	public Settings inflateIds() {
		List<Long> existingIds = new ArrayList<>();
		List<Site> sitesWithoutIds = new ArrayList<>();
		for(Site s : sites) {
			if(s.isIdDefinied()) {
				existingIds.add(s.getId());
			} else {
				sitesWithoutIds.add(s);
			}
		}
		long nextId = 0;
		for(Site s : sitesWithoutIds) {
			//finds a id which isn't set
			do {
				nextId++;
			} while(existingIds.contains(nextId) && nextId < Long.MAX_VALUE);
			if(nextId == Long.MAX_VALUE) {
				throw new IllegalStateException("Too many sites where defined and no more ids are left to set");
			}
			s.setId(nextId);
		}
		return this;
	}

	@Override
	public String toString() {
		return "Settings { " + getSites().stream().map(Object::toString).collect(Collectors.joining(", ")) + "  }";
	}
}
