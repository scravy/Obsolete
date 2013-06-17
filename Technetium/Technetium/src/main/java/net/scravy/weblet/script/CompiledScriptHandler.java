package net.scravy.weblet.script;

import java.io.InputStreamReader;

import javax.script.Bindings;
import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

import net.scravy.weblet.Request;
import net.scravy.weblet.Response;
import net.scravy.weblet.Weblet;

public class CompiledScriptHandler extends AbstractScriptHandler {

	private final CompiledScript compiledScript;

	public final String ENGINE;
	public final String ENGINE_VERSION;
	public final String SCRIPT;
	
	public CompiledScriptHandler(ScriptEngine scriptEngine, String script)
			throws ScriptException {
		super(scriptEngine);
		compiledScript = ((Compilable) engine).compile(new InputStreamReader(
				getClass().getResourceAsStream(script)));

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

		compiledScript.eval(bindings);
	}
}
