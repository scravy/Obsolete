package net.scravy.technetium.modules.showcase;

import java.io.IOException;

import net.scravy.weblet.Handler;
import net.scravy.weblet.Request;
import net.scravy.weblet.Response;
import net.scravy.weblet.Weblet;

public class NestedExceptions implements Handler {

	@Override
	public void handle(Weblet weblet, Request request, Response response)
			throws Exception {

		throw new RuntimeException("This is a nice message.", new IOException(
				"This is an IOException caused by another exception.",
				new NullPointerException()));

	}

}
