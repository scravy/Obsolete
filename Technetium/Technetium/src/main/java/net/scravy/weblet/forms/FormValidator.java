package net.scravy.weblet.forms;

import java.util.List;

import net.scravy.weblet.Weblet;

public interface FormValidator {

	List<String> validate(Weblet weblet, Form form);
	
}