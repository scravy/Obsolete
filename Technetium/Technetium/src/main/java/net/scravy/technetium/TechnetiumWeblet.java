package net.scravy.technetium;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import net.scravy.weblet.forms.FormsWeblet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TechnetiumWeblet extends FormsWeblet {

	private final long _created = System.currentTimeMillis();

	private static final long serialVersionUID = -5945258180699606507L;	
	private final Logger logger = LoggerFactory.getLogger(getClass());

	public TechnetiumWeblet() {
		super("technetium", "technetium");
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("Took %d ms to create Weblet object", System.currentTimeMillis() - _created));
		}
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		long start = System.currentTimeMillis();
		
		super.init(config);

		logger.info(String.format("Technetium started up (%d ms).", System.currentTimeMillis() - start));
	}

}
