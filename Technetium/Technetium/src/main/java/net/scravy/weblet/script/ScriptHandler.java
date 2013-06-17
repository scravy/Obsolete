package net.scravy.weblet.script;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.script.Bindings;
import javax.script.ScriptEngine;

import net.scravy.weblet.Request;
import net.scravy.weblet.Response;
import net.scravy.weblet.Weblet;

public class ScriptHandler extends AbstractScriptHandler {

	final private String script;

	public final String ENGINE;
	public final String ENGINE_VERSION;
	public final String SCRIPT;

	public ScriptHandler(ScriptEngine scriptEngine,
			String scriptResource) {
		super(scriptEngine);
		script = scriptResource;

		ENGINE = scriptEngine.getFactory().getEngineName();
		ENGINE_VERSION = scriptEngine.getFactory().getEngineVersion();
		SCRIPT = script;
	}

	@Override
	public void handle(
			Weblet weblet,
			Request request,
			Response response) throws Exception {

		Bindings bindings = createBindings(weblet, request, response);

		InputStream inputStream = getClass().getResourceAsStream(script);
		if (inputStream == null) {
			throw new FileNotFoundException(String.format(
					"The script “%s” is not a know resource.", script));
		}

		engine.eval(new InputStreamReader(inputStream), bindings);
	}

}
