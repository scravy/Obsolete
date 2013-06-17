response.setTemplate("showcase-dyn")

root = response.createElement("groovy")
response.createElement(root, "says-hello", "Hello from Groovy")

response.append(root)
