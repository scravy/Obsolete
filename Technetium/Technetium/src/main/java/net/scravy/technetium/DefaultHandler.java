package net.scravy.technetium;

import net.scravy.weblet.Handler;
import net.scravy.weblet.Request;
import net.scravy.weblet.Response;
import net.scravy.weblet.Response.Status;
import net.scravy.weblet.Weblet;

public class DefaultHandler implements Handler {

	@Override
	public void handle(Weblet servlet, Request request, Response response) {
		response.setStatus(Status.NOT_FOUND);
		response.setTemplate("error404");
	}

}
