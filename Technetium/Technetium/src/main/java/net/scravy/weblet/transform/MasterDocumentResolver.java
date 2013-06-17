package net.scravy.weblet.transform;

import javax.xml.bind.JAXBContext;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.URIResolver;
import javax.xml.transform.dom.DOMSource;

import net.scravy.weblet.forms.FormDefinition;
import net.scravy.weblet.forms.FormsWeblet;

import org.w3c.dom.Document;

public class MasterDocumentResolver implements URIResolver {

	private final FormsWeblet parent;

	MasterDocumentResolver(FormsWeblet parent) {
		this.parent = parent;
	}

	@Override
	public Source resolve(String href, String base)
			throws TransformerException {
		if (href.startsWith("form://")) {
			String formName = href.substring(7);
			FormDefinition form =
					parent.unwrap(FormsWeblet.class)
							.getFormDefinition(formName);
			try {
				JAXBContext c = JAXBContext
						.newInstance(FormDefinition.class);
				Document d = DocumentBuilderFactory
						.newInstance()
						.newDocumentBuilder()
						.newDocument();
				c.createMarshaller().marshal(form, d);
				return new DOMSource(d);
			} catch (Exception exc) {
				throw new RuntimeException(exc);
			}
		} else if (href.startsWith("text://")) {

		}
		return null;
	}
}
