package helio.builder.siot.methods;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import helio.blueprints.DataHandler;

public class HandlerWrapper {

	public static Logger logger = LoggerFactory.getLogger(DataHandler.class);

	private DataHandler handler;

	public HandlerWrapper(DataHandler handler) {
		this.handler = handler;
	}

	public Object filter(String filter, String chunk) {
		try {
			List<String> filtered = handler.filter(filter, chunk);
			if (filtered.size() == 1)
				return filtered.get(0);
			return filtered;
		} catch (Exception e) {
			logger.error(e.toString());
			System.out.println(chunk);
		}
		return null;
	}
}
