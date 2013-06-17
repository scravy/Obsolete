response.template = "sysinfo-handlers"

root = response.createElement("handlers")

for e in parent.handlers.entrySet():
    h = response.createElement(root, "handler", e.value.getClass().canonicalName)
    h.setAttribute("path", "/".join(e.key))
    if e.value.ENGINE:
        h.setAttribute("engine", e.value.ENGINE)
        h.setAttribute("engineVersion", e.value.ENGINE_VERSION)
        h.setAttribute("script", e.value.SCRIPT)

response.append(root)
