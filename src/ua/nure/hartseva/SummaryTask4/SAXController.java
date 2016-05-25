package ua.nure.hartseva.SummaryTask4;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import ua.nure.hartseva.SummaryTask4.bean.PageCategories;
import ua.nure.hartseva.SummaryTask4.bean.PageCategory;
import ua.nure.hartseva.SummaryTask4.bean.PageView;
import ua.nure.hartseva.SummaryTask4.bean.Presentation;
import ua.nure.hartseva.SummaryTask4.bean.XML;
import ua.nure.hartseva.SummaryTask4.entity.Role;

public class SAXController extends DefaultHandler {

	private String currentElement;

	private Presentation presentation;

	private PageView pageView;

	private PageCategories pageCategories;

	private PageCategory pageCategory;

	public void parse(InputStream inputStream, boolean validate)
			throws ParserConfigurationException, SAXException, IOException {

		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setNamespaceAware(true);
		SAXParser parser = factory.newSAXParser();
		parser.parse(inputStream, this);
	}

	@Override
	public void error(org.xml.sax.SAXParseException e) throws SAXException {
		throw e;
	};

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

		currentElement = localName;

		if (XML.PRESENTATION.equalsTo(currentElement)) {
			presentation = new Presentation();
			return;
		}

		if (XML.PAGE_VIEW.equalsTo(currentElement)) {
			pageView = new PageView();
			return;
		}

		if (XML.PAGE_CATEGORIES.equalsTo(currentElement)) {
			pageCategories = new PageCategories();
			return;
		}

		if (XML.PAGE_CATEGORY.equalsTo(currentElement)) {
			pageCategory = new PageCategory();
			return;
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {

		String elementText = new String(ch, start, length).trim();

		if (elementText.isEmpty()) {
			return;
		}

		switch (XML.fromValue(currentElement)) {
		case HOME_PAGE:
			pageView.setHomePage(elementText);
			break;
		case PAGE_CATEGORY_KEY:
			pageCategory.setPageCategoryKey(elementText);
			break;
		case PAGE_CATEGORY_PATH:
			pageCategory.setPageCategoryPath(elementText);
			break;
		case ROLE:
			pageView.setRole(Role.fromValue(elementText));
			break;
		default:
			break;

		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {

		if (XML.PRESENTATION.equalsTo(localName)) {
			return;
		}
		if (XML.PAGE_VIEW.equalsTo(localName)) {
			presentation.getPageView().add(pageView);
			return;
		}

		if (XML.PAGE_CATEGORIES.equalsTo(localName)) {
			pageView.setPageCategories(pageCategories);
			return;
		}

		if (XML.PAGE_CATEGORY.equalsTo(localName)) {
			pageCategories.getPageCategories().add(pageCategory);
			return;
		}
	}

	public Presentation getPresentation() {
		return presentation;
	}
}
