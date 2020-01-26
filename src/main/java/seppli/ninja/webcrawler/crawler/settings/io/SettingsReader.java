package seppli.ninja.webcrawler.crawler.settings.io;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import seppli.ninja.webcrawler.crawler.settings.model.Settings;

/**
 * Reads a settings xml with jaxb and the settings model
 *
 * @author sebi
 * @version 1.0
 *
 */
public class SettingsReader {
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
	public SettingsReader(File file) {
		this.file = file;
	}

	/**
	 * Reads the file
	 * @throws JAXBException
	 */
	public Settings read() throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(Settings.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		return (Settings) unmarshaller.unmarshal(getFile());
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
