package net.scravy.technetium.modules.admin;

import net.scravy.technetium.util.BCrypt;
import net.scravy.weblet.Weblet;
import net.scravy.weblet.forms.Converter;
import net.scravy.weblet.forms.Form;

public class PasswordConverter implements Converter<String> {

	@Override
	public String convert(Weblet weblet, Form form, String password) {
		return BCrypt.hashpw(password, BCrypt.gensalt());
	}

}
