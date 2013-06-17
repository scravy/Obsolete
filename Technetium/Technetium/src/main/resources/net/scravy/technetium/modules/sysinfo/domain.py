from javax.persistence.metamodel import MapAttribute

response.setTemplate("sysinfo-domain")

root = response.createElement("domain")

entities = db.metamodel.entities

for entity in entities:
    e = response.createElement(root, "entity")
    e.setAttribute("name", entity.name)
    for attr in entity.attributes:
        a = response.createElement(e, "attribute")
        a.setAttribute("name", attr.name)
        a.setAttribute("type", str(attr.persistentAttributeType))
        
        if attr.isCollection():
            a.setAttribute("target", attr.elementType.javaType.simpleName)
            a.setAttribute("collection", attr.javaType.simpleName)
            if MapAttribute.isAssignableFrom(attr.getClass()):
                a.setAttribute("mapKeyType", attr.keyJavaType.simpleName)
        else:
            a.setAttribute("target", attr.javaType.simpleName)


response.append(root)