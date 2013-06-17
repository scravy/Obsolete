package net.scravy.weblet.script;

import javax.script.Bindings;
import javax.script.ScriptEngine;

import org.slf4j.LoggerFactory;

import net.scravy.weblet.Handler;
import net.scravy.weblet.Request;
import net.scravy.weblet.Response;
import net.scravy.weblet.Weblet;

abstract class AbstractScriptHandler implements Handler {

	protected final ScriptEngine engine;
	
	protected AbstractScriptHandler(ScriptEngine scriptEngine) {
		engine = scriptEngine;
	}
	
	protected Bindings createBindings(
			Weblet weblet,
			Request request,
			Response response) throws Exception {

		Bindings bindings = engine.createBindings();

		bindings.put("parent", weblet);
		bindings.put("db", weblet.db());
		bindings.put("request", request);
		bindings.put("response", response);
		bindings.put("out", System.out);
		bindings.put("logger", LoggerFactory.getLogger(getClass()));

		return bindings;
	}
}
