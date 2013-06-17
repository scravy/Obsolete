response.setTemplate("showcase-dyn")

root = response.createElement("javascript")
response.createElement(root, "says-hello", "Hello, says JavaScript.")

response.append(root)
