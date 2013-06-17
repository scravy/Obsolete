package net.scravy.weblet.transform;

import javax.xml.transform.Transformer;

public interface TransformerPool {

	Transformer get(String name);

}
