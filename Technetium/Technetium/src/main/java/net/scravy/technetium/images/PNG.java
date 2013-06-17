package net.scravy.technetium.images;

import java.io.InputStream;

import net.scravy.weblet.Handler;
import net.scravy.weblet.ReaderWriter;
import net.scravy.weblet.Request;
import net.scravy.weblet.Response;
import net.scravy.weblet.Weblet;
import net.scravy.weblet.Response.Status;

public class PNG implements Handler {

	@Override
	public void handle(Weblet servlet, Request request, Response response) {
		String name = request.getPath()[0];

		InputStream inputStream = PNG.class.getResourceAsStream(name);

		if (inputStream == null) {
			response.setStatus(Status.NOT_FOUND);
		} else {
			response.setOutputWriter(new ReaderWriter(inputStream));
			response.setContentType("image/png");
		}
	}

}
