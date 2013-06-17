package net.scravy.technetium.js;

import java.io.InputStream;

import net.scravy.weblet.Handler;
import net.scravy.weblet.ReaderWriter;
import net.scravy.weblet.Request;
import net.scravy.weblet.Response;
import net.scravy.weblet.Weblet;
import net.scravy.weblet.Response.Status;

public class JS implements Handler {

	@Override
	public void handle(Weblet servlet, Request request, Response response) {
		String name = request.getPath()[0];

		if (name.equals("app.js")) {
			InputStream in1 = JS.class.getResourceAsStream("jquery.js");
			InputStream in2 = JS.class.getResourceAsStream("bootstrap.js");
			
			response.setOutputWriter(new ReaderWriter(in1, in2));
			response.setContentType("text/javascript");
		} else {
			InputStream inputStream = JS.class.getResourceAsStream(name);

			if (inputStream == null) {
				response.setStatus(Status.NOT_FOUND);
			} else {
				response.setOutputWriter(new ReaderWriter(inputStream));
				response.setContentType("text/javascript");
			}
		}

	}

}
