package net.scravy.weblet;

public interface Handler {

	String ENGINE = "";
	String ENGINE_VERSION = "";
	String SCRIPT = "";
	
	void handle(Weblet weblet, Request request, Response response) throws Exception;
	
}
