package net.scravy.technetium.modules.admin;

import net.scravy.weblet.Handler;
import net.scravy.weblet.Request;
import net.scravy.weblet.Response;
import net.scravy.weblet.Weblet;

public class Index implements Handler {

	@Override
	public void handle(Weblet servlet, Request request, Response response) {
		if (request.isLoggedIn()) {
			response.setTemplate("admin");
		}
	}

}
