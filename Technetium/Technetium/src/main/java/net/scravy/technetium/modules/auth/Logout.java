package net.scravy.technetium.modules.auth;

import net.scravy.weblet.Handler;
import net.scravy.weblet.Request;
import net.scravy.weblet.Response;
import net.scravy.weblet.Weblet;

public class Logout implements Handler {

	@Override
	public void handle(Weblet servlet, Request request, Response response) {
		request.getSession().setUser(null);
		response.setTemplate("auth-logout");
	}
}