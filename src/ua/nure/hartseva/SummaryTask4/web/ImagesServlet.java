package ua.nure.hartseva.SummaryTask4.web;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.nure.hartseva.SummaryTask4.Attributes;
import ua.nure.hartseva.SummaryTask4.Parameters;
import ua.nure.hartseva.SummaryTask4.service.impl.ImagesService;

public class ImagesServlet extends HttpServlet {

	private static final long serialVersionUID = 6070228519365325995L;
	private static final String NO_IMAGE_FILE_NAME = "no-image";
	
	private ImagesService imagesService;
	
	@Override
	public void init() throws ServletException {
		imagesService = (ImagesService) getServletContext().getAttribute(Attributes.IMAGES_SERVICE);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		OutputStream out = resp.getOutputStream();
		File imageFile;
		
		String identifier = req.getParameter(Parameters.IMAGE_IDENTIFIER);
		if (identifier != null && !identifier.isEmpty()) {
			imageFile = imagesService.getByIdentifier(identifier);
			if (imageFile.exists()) {
				Files.copy(imageFile.toPath(), out);
				return;
			}
		}
		imageFile = imagesService.getByIdentifier(NO_IMAGE_FILE_NAME);
		Files.copy(imageFile.toPath(), out);
	}
}
