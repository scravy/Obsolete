package net.scravy.technetium.css;

import java.io.InputStream;

import net.scravy.weblet.Handler;
import net.scravy.weblet.ReaderWriter;
import net.scravy.weblet.Request;
import net.scravy.weblet.Response;
import net.scravy.weblet.Weblet;
import net.scravy.weblet.Response.Status;

public class CSS implements Handler {

	@Override
	public void handle(Weblet servlet, Request request, Response response) {
		String name = request.getPath()[0];

		if (name.equals("style.css")) {
			InputStream in1 = CSS.class.getResourceAsStream("bootstrap.css");
			InputStream in2 = CSS.class
					.getResourceAsStream("bootstrap-responsive.css");

			response.setOutputWriter(new ReaderWriter(in1, in2));
			response.setContentType("text/css");
		} else {
			InputStream inputStream = CSS.class.getResourceAsStream(name);

			if (inputStream == null) {
				response.setStatus(Status.NOT_FOUND);
			} else {
				response.setOutputWriter(new ReaderWriter(inputStream));
				response.setContentType("text/css");
			}
		}
	}

}
