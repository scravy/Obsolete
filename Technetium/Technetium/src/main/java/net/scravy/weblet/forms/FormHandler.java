package net.scravy.weblet.forms;

import net.scravy.weblet.Weblet;

/**
 * Handles a successfully validated form.
 * 
 * @author Julian Fleischer
 * @since 1.0
 */
public interface FormHandler {

	void handle(Weblet servlet, Form form);

}
