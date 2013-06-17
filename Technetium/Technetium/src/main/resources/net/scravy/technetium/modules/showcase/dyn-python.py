response.setTemplate("showcase-dyn")

root = response.createElement("python")
response.createElement(root, "says-hello", "Hello, says Python.")

response.append(root)

result = db.mapQuery("SELECT p.loginName, p.email, p.firstName, p.lastName FROM Person p")

p = response.createElement(root, "persons")
for person in result:
    e = response.createElement(p, "person",
                               person['firstName'] + ' ' + person['lastName'])
    for key in person:
        e.setAttribute(key, person[key])
