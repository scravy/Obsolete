package net.scravy.technetium.modules.auth;

import net.scravy.weblet.Handler;
import net.scravy.weblet.Request;
import net.scravy.weblet.Response;
import net.scravy.weblet.Weblet;

public class Welcome implements Handler {

	@Override
	public void handle(Weblet servlet, Request request, Response response) {
		response.setTemplate("auth-start");
	}
}