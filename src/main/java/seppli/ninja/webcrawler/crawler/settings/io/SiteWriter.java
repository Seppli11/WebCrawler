package seppli.ninja.webcrawler.crawler.settings.io;

import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import seppli.ninja.webcrawler.crawler.settings.model.Site;

/**
 * writes the site object to an outputstream
 *
 * @author sebi
 * @version 1.0
 *
 */
public class SiteWriter {


	/**
	 * Construtor
	 */
	public SiteWriter() {
	}

	/**
	 * Writes the given site to the given output stream
	 * @param s the site
	 * @param out the output stream
	 * @throws JAXBException
	 */
	public void write(Site s, OutputStream out) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(Site.class);
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		marshaller.marshal(s, out);
	}

}
