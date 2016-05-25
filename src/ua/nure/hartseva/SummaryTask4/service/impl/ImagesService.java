package ua.nure.hartseva.SummaryTask4.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.Part;

import org.apache.log4j.Logger;

import ua.nure.hartseva.SummaryTask4.service.IFileService;

public class ImagesService implements IFileService {

	public static final String EXTENSION = "png";

	private static final Logger LOG = Logger.getLogger(ImagesService.class);
	private final String filePath;

	public ImagesService(String filePath) {
		this.filePath = filePath;
		createFileIfDoesNotExist(filePath);
	}

	@Override
	public String saveFile(Part part) {
		String identifier = UUID.randomUUID().toString();
		String fullPath = filePath + File.separator + identifier + "." + EXTENSION;

		try {
			part.write(fullPath);
		} catch (IOException e) {
			LOG.error(e.getMessage());
			return null;
		}
		return identifier;
	}

	@Override
	public File getByIdentifier(String identifier) {
		String fullPath = filePath + File.separator + identifier + "." + EXTENSION;
		return new File(fullPath);
	}

	private void createFileIfDoesNotExist(String filePath) {
		File file = new File(filePath);
		if (!file.exists() && file.mkdir()) {
			LOG.info("Directory has been created.");
		}
	}
}
