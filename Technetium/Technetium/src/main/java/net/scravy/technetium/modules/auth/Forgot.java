package net.scravy.technetium.modules.auth;

import net.scravy.weblet.Handler;
import net.scravy.weblet.Request;
import net.scravy.weblet.Response;
import net.scravy.weblet.Weblet;

public class Forgot implements Handler {

	@Override
	public void handle(Weblet servlet, Request request, Response response) {
		response.setTemplate("auth/forgot");
		response.set("userName", request.getString("forgot.username"));
	}
}