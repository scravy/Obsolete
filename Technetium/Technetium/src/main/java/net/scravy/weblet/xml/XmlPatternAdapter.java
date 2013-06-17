package net.scravy.weblet.xml;

import java.util.regex.Pattern;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * An {@link XmlAdapter} for {@link Pattern}.
 * 
 * @author Julian Fleischer
 * @since 1.0
 */
public class XmlPatternAdapter extends XmlAdapter<String, Pattern> {

	@Override
	public String marshal(Pattern v) throws Exception {
		if (v == null) {
			return "";
		}
		return v.toString();
	}

	@Override
	public Pattern unmarshal(String v) throws Exception {
		if (v == null || v.isEmpty()) {
			return null;
		}
		return Pattern.compile(v);
	}

}
