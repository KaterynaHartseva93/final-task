package ua.nure.hartseva.SummaryTask4.web.command;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public class CommandMap {

	private static final Logger LOG = Logger.getLogger(CommandMap.class);
	
	private static final String DELIMITER = "::";
	private static final String SLASH = "/";
	private static final String PATH_PREFIX = "/controller";

	private Map<String, Command> map = new HashMap<>();

	public CommandMap put(String method, String path, Command command) {
		// Example of key: GET::/market/controller/clothes
		String key = getKey(method, path);
		
		if (map.containsKey(key)) {
			throw new IllegalArgumentException("Such key " + key + " already exists;");
		}
		map.put(key, command);
		LOG.info(key + " --> " + command.toString());
		return this;
	}
	
	public int size() {
		return map.size();
	}
	
	public boolean containsKey(String key) {
		return map.containsKey(key);
	}
	
	public Command getByKey(String key) {
		return map.get(key);
	}
	
	private String getKey(String method, String path) {
		String key = method + DELIMITER + PATH_PREFIX;
		if (path != null && !path.isEmpty()) {
			key = key + SLASH;
		}
		return key + path;
	}
}
