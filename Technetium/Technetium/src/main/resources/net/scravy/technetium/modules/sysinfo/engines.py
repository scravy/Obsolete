from javax.script import ScriptEngineManager, Compilable, Invocable
from java.util import HashSet

from org.codehaus.groovy.jsr223 import GroovyScriptEngineFactory
from org.jruby.embed.jsr223 import JRubyEngineFactory
from org.python.jsr223 import PyScriptEngineFactory
from clojure.contrib.jsr223 import ClojureScriptEngineFactory

response.template = "sysinfo-engines"
root = response.createElement("engines")

m = ScriptEngineManager()

engines = HashSet([GroovyScriptEngineFactory,
                   JRubyEngineFactory,
                   PyScriptEngineFactory,
                   ClojureScriptEngineFactory])

def infoAboutEngine(f):
    e = response.createElement(root, "engine")
    e.setAttribute("name", f.engineName)
    e.setAttribute("version", f.engineVersion)
    e.setAttribute("language", f.languageName)
    e.setAttribute("languageVersion", f.languageVersion)
    e.setAttribute("threading", f.getParameter("THREADING"))
    e.setAttribute("factory", f.getClass().canonicalName)
    
    if Compilable.isAssignableFrom(f.scriptEngine.getClass()):
        e.setAttribute("compilable", "yes")
    else:
        e.setAttribute("compilable", "no")
    if Invocable.isAssignableFrom(f.scriptEngine.getClass()):
        e.setAttribute("invocable", "yes")
    else:
        e.setAttribute("invocable", "no")
    
    for n in f.names:
        response.createElement(e, "name", n)
    for m in f.mimeTypes:
        response.createElement(e, "mimeType", m)
    for ext in f.extensions:
        response.createElement(e, "ext", ext)

for f in m.engineFactories:
    engines.remove(f.getClass())
    infoAboutEngine(f)
    
for f in engines:
    infoAboutEngine(f())

response.append(root)
