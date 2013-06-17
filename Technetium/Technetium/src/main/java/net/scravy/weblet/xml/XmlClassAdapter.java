package net.scravy.weblet.xml;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class XmlClassAdapter extends XmlAdapter<String, Class<?>> {

	@Override
	public String marshal(Class<?> clazz) throws Exception {
		if (clazz == null) {
			return "";
		}
		return clazz.getName();
	}

	@Override
	public Class<?> unmarshal(String value) throws Exception {
		if (value.indexOf('.') < 0) {
			if ("Int".equalsIgnoreCase(value)
					|| "Integer".equalsIgnoreCase(value)) {
				return Integer.class;
			}
			if ("Long".equalsIgnoreCase(value)) {
				return Long.class;
			}
			if ("Float".equalsIgnoreCase(value)) {
				return Float.class;
			}
			if ("Double".equalsIgnoreCase(value)) {
				return Double.class;
			}
			if ("Byte".equalsIgnoreCase(value)) {
				return Byte.class;
			}
			if ("Short".equalsIgnoreCase(value)) {
				return Short.class;
			}
			if ("Char".equalsIgnoreCase(value)
					|| "Character".equalsIgnoreCase(value)) {
				return Character.class;
			}
			if ("Boolean".equalsIgnoreCase(value)
					|| "Bool".equalsIgnoreCase(value)) {
				return boolean.class;
			}
			if ("String".equalsIgnoreCase(value)) {
				return String.class;
			}
			if ("BigInteger".equalsIgnoreCase(value)) {
				return BigInteger.class;
			}
			if ("BigDecimal".equalsIgnoreCase(value)) {
				return BigDecimal.class;
			}
			if ("Date".equalsIgnoreCase(value)) {
				return Date.class;
			}
		}
		try {
			return Class.forName(value);
		} catch (Exception exc) {
			return null;
		}
	}

}