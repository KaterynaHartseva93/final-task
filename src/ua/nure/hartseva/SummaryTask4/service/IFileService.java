package ua.nure.hartseva.SummaryTask4.service;

import java.io.File;

import javax.servlet.http.Part;

public interface IFileService {
	
	String saveFile(Part part);
	
	File getByIdentifier(String identifier);
}
