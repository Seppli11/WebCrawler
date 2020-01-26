package seppli.ninja.webcrawler.crawler.settings.io;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import seppli.ninja.webcrawler.crawler.settings.model.Settings;

/**
 * Reads a settings xml with jaxb and the settings model
 *
 * @author sebi
 * @version 1.0
 *
 */
public class SettingsWriter {
	/**
	 * the path to the default settings
	 */
	public static final String defaultSettingsPath = "./settings.xml";


	/**
	 * the file to read
	 */
	private File file;

	/**
	 * Construtor
	 *
	 * @param file the file to read
	 */
	public SettingsWriter(File file) {
		this.file = file;
	}

	/**
	 * Reads the file
	 * @throws JAXBException
	 */
	public void write(Settings s) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(Settings.class);
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		marshaller.marshal(s, file);
	}


	/**
	 * returns the file to read
	 *
	 * @return the file to read
	 */
	public File getFile() {
		return file;
	}

}
