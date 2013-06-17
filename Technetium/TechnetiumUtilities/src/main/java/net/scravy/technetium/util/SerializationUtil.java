package net.scravy.technetium.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * EXPERIMENTAL, not public.
 * 
 * @author Julian Fleischer
 * @since 1.0
 * @experimental
 */
class SerializationUtil {

	/**
	 * Forbid instantiation of this class. The constructor is package-private so
	 * no warnings about an unused constructor will show up.
	 */
	SerializationUtil() {
	}

	public static String serialize(final Object... object) {
		StringBuilder b = new StringBuilder();

		serialize(b, object);

		return b.toString();
	}

	public static String serialize(final Object object) {
		StringBuilder b = new StringBuilder();

		serialize(b, object);

		return b.toString();
	}

	private static String readString(final String string) {
		StringBuilder b = new StringBuilder();

		for (int i = 0; i < string.length(); i++) {
			char c = string.charAt(i);
			if (c == '\\') {
				c = (char) Integer.parseInt(string.substring(i + 2, i + 6), 16);
				i = i + 5;
			}
			b.append(c);
		}

		return b.toString();
	}

	private static void serialize(final StringBuilder b, final Object object) {

		if (object == null) {
			b.append("null");
		} else if (object instanceof Boolean) {
			b.append((Boolean) object ? "true" : "false");
		} else if (object instanceof Byte) {
			b.append('b');
			b.append(object.toString());
		} else if (object instanceof Short) {
			b.append('s');
			b.append(object.toString());
		} else if (object instanceof Integer) {
			b.append('i');
			b.append(object.toString());
		} else if (object instanceof Long) {
			b.append('l');
			b.append(object.toString());
		} else if (object instanceof Character) {
			b.append('#');
			b.append(Integer.toHexString(((Character) object).charValue()));
		} else if (object instanceof Float) {
			b.append("f");
			b.append(object.toString());
		} else if (object instanceof Double) {
			b.append("d");
			b.append(object.toString());
		} else if (object instanceof String) {
			String string = (String) object;
			b.append('"');
			for (int i = 0; i < string.length(); i++) {
				int c = string.charAt(i);
				if (((c >= 'a') && (c <= 'z')) || ((c >= 'A') && (c <= 'Z'))
						|| ((c >= '0') && (c <= '9'))) {
					b.append((char) c);
				} else {
					b.append(String.format("\\u%04x", c));
				}
			}
			b.append('"');
		} else if (object instanceof Date) {
			b.append("D");
			b.append(Long.toString(((Date) object).getTime(), 36));
		} else if (object instanceof List) {
			b.append("L");
			b.append(((List<?>) object).size());
			b.append(';');
			for (Object e : (List<?>) object) {
				serialize(b, e);
				b.append(',');
			}
		} else if (object instanceof Set) {
			b.append("S");
			b.append(((Set<?>) object).size());
			b.append(';');
			for (Object e : (Set<?>) object) {
				serialize(b, e);
				b.append(',');
			}
		} else if (object.getClass().isArray()) {
			int length = Array.getLength(object);
			b.append("A");
			b.append(length);
			b.append(';');
			b.append(object.getClass().getComponentType().getCanonicalName());
			b.append(';');
			for (int i = 0; i < length; i++) {
				serialize(b, Array.get(object, i));
				b.append(',');
			}
		}
	}

	public static Object unserialize(final String string) {
		Object result = null;

		switch (string.charAt(0)) {
		case 'b':
			return (byte) Byte.parseByte(string.substring(1));
		case 's':
			return (short) Short.parseShort(string.substring(1));
		case 'i':
			return Integer.parseInt(string.substring(1));
		case 'l':
			return Long.parseLong(string.substring(1));
		case 'f':
			return Float.parseFloat(string.substring(1));
		case 'd':
			return Double.parseDouble(string.substring(1));
		case '#':
			return (char) Integer.parseInt(string.substring(1), 16);
		case '"':
			return readString(string.substring(1, string.indexOf('"', 1)));
		case 'D': {
			return new Date(Long.parseLong(string.substring(1), 36));
		}
		case 'L': {
			int offset = string.indexOf(';', 1);
			int length = Integer.parseInt(string.substring(1, offset));
			return unserializeList(string, length, offset + 1);
		}
		case 'S': {
			int offset = string.indexOf(';', 1);
			int length = Integer.parseInt(string.substring(1, offset));
			return unserializeSet(string, length, offset + 1);
		}
		case 'A': {
			int offset = string.indexOf(';', 1);
			int length = Integer.parseInt(string.substring(1, offset));
			return unserializeArray(string, length, offset + 1);
		}
		}

		return result;
	}

	public static List<Object> unserializeList(final String string,
			final int length, final int offset) {
		List<Object> list = new ArrayList<Object>(length);

		int from = offset;
		int to;
		for (int j = 0; j < length; j++) {
			switch (string.charAt(from)) {
			case 'b': {
				to = string.indexOf(',', from);
				byte b = Byte.parseByte(string.substring(from + 1, to));
				from = to + 1;
				list.add(b);
				break;
			}
			case 's': {
				to = string.indexOf(',', from);
				short s = Short.parseShort(string.substring(from + 1, to));
				from = to + 1;
				list.add(s);
				break;
			}
			case 'i': {
				to = string.indexOf(',', from);
				int i = Integer.parseInt(string.substring(from + 1, to));
				from = to + 1;
				list.add(i);
				break;
			}
			case 'l': {
				to = string.indexOf(',', from);
				long l = Long.parseLong(string.substring(from + 1, to));
				from = to + 1;
				list.add(l);
				break;
			}
			case 'f': {
				to = string.indexOf(',', from);
				float f = Float.parseFloat(string.substring(from + 1, to));
				from = to + 1;
				list.add(f);
				break;
			}
			case 'd': {
				to = string.indexOf(',', from);
				double d = Double.parseDouble(string.substring(from + 1, to));
				from = to + 1;
				list.add(d);
				break;
			}
			case '#': {
				to = string.indexOf(',', from);
				char c = (char) Integer.parseInt(
						string.substring(from + 1, to), 16);
				from = to + 1;
				list.add(c);
				break;
			}
			case '"': {
				to = string.indexOf('"', from);
				String s = readString(string.substring(from + 1, to));
				from = to + 1;
				list.add(s);
				break;
			}
			case 'L':
				break;
			case 'S':
				break;
			case 'A':
				break;
			}
		}

		return list;
	}

	public static Set<Object> unserializeSet(final String string,
			final int length, final int offset) {
		Set<Object> list = new HashSet<Object>(length);

		int from = offset;
		int to;
		for (int j = 0; j < length; j++) {
			switch (string.charAt(from)) {
			case 'b': {
				to = string.indexOf(',', from);
				byte b = Byte.parseByte(string.substring(from + 1, to));
				from = to + 1;
				list.add(b);
				break;
			}
			case 's': {
				to = string.indexOf(',', from);
				short s = Short.parseShort(string.substring(from + 1, to));
				from = to + 1;
				list.add(s);
				break;
			}
			case 'i': {
				to = string.indexOf(',', from);
				int i = Integer.parseInt(string.substring(from + 1, to));
				from = to + 1;
				list.add(i);
				break;
			}
			case 'l': {
				to = string.indexOf(',', from);
				long l = Long.parseLong(string.substring(from + 1, to));
				from = to + 1;
				list.add(l);
				break;
			}
			case 'f': {
				to = string.indexOf(',', from);
				float f = Float.parseFloat(string.substring(from + 1, to));
				from = to + 1;
				list.add(f);
				break;
			}
			case 'd': {
				to = string.indexOf(',', from);
				double d = Double.parseDouble(string.substring(from + 1, to));
				from = to + 1;
				list.add(d);
				break;
			}
			case '#': {
				to = string.indexOf(',', from);
				char c = (char) Integer.parseInt(
						string.substring(from + 1, to), 16);
				from = to + 1;
				list.add(c);
				break;
			}
			case '"': {
				to = string.indexOf('"', from);
				String s = readString(string.substring(from + 1, to));
				from = to + 1;
				list.add(s);
				break;
			}
			case 'L':
				break;
			case 'S':
				break;
			case 'A':
				break;
			}
		}

		return list;
	}

	private static Class<?> forName(final String name) {
		if (name.equals("Byte")) {
			return Byte.class;
		} else if (name.equals("Short")) {
			return Short.class;
		} else if (name.equals("Integer") || name.equals("Int")) {
			return Integer.class;
		} else if (name.equals("Long")) {
			return Long.class;
		} else if (name.equals("Double")) {
			return Double.class;
		} else if (name.equals("Float")) {
			return Float.class;
		} else if (name.equals("Boolean") || name.equals("Bool")) {
			return Boolean.class;
		} else if (name.equals("Char") || name.equals("Character")) {
			return Character.class;
		} else if (name.equals("int")) {
			return int.class;
		} else if (name.equals("long")) {
			return long.class;
		} else if (name.equals("byte")) {
			return byte.class;
		} else if (name.equals("short")) {
			return short.class;
		} else if (name.equals("boolean")) {
			return boolean.class;
		} else if (name.equals("char")) {
			return char.class;
		}
		try {
			return Class.forName(name);
		} catch (Exception exc) {
			return null;
		}
	}

	public static Object unserializeArray(final String string,
			final int length, final int offset) {
		int from = offset;
		int to = string.indexOf(';', offset);

		String componentClassName = string.substring(from, to);
		Object array = Array.newInstance(forName(componentClassName), length);
		from = to + 1;

		for (int j = 0; j < length; j++) {
			switch (string.charAt(from)) {
			case 'b': {
				to = string.indexOf(',', from);
				byte b = Byte.parseByte(string.substring(from + 1, to));
				from = to + 1;
				Array.set(array, j, b);
				break;
			}
			case 's': {
				to = string.indexOf(',', from);
				short s = Short.parseShort(string.substring(from + 1, to));
				from = to + 1;
				Array.set(array, j, s);
				break;
			}
			case 'i': {
				to = string.indexOf(',', from);
				int i = Integer.parseInt(string.substring(from + 1, to));
				from = to + 1;
				Array.set(array, j, i);
				break;
			}
			case 'l': {
				to = string.indexOf(',', from);
				long l = Long.parseLong(string.substring(from + 1, to));
				from = to + 1;
				Array.set(array, j, l);
				break;
			}
			case 'f': {
				to = string.indexOf(',', from);
				float f = Float.parseFloat(string.substring(from + 1, to));
				from = to + 1;
				Array.set(array, j, f);
				break;
			}
			case 'd': {
				to = string.indexOf(',', from);
				double d = Double.parseDouble(string.substring(from + 1, to));
				from = to + 1;
				Array.set(array, j, d);
				break;
			}
			case '#': {
				to = string.indexOf(',', from);
				char c = (char) Integer.parseInt(
						string.substring(from + 1, to), 16);
				from = to + 1;
				Array.set(array, j, c);
				break;
			}
			case '"': {
				to = string.indexOf('"', from);
				String s = readString(string.substring(from + 1, to));
				from = to + 1;
				Array.set(array, j, s);
				break;
			}
			case 'L':
				break;
			case 'S':
				break;
			case 'A':
				break;
			}
		}

		return array;
	}

}