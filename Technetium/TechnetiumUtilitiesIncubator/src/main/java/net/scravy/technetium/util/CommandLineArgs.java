package net.scravy.technetium.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Easy parsing of CommandLineArgs.
 * 
 * @author Julian Fleischer
 * @since 1.0
 */
public class CommandLineArgs {

	private final ArrayList<String> arguments = new ArrayList<String>();
	private final ArrayList<String[]> aliases = new ArrayList<String[]>();
	private final Map<String, Object[]> options = new HashMap<String, Object[]>();
	private final Map<String, Class<?>[]> optionsConfiguration = new HashMap<String, Class<?>[]>();

	private String validOptionName = "\\-\\-?[a-zA-Z0-9\\.]+";

	/**
	 * Converts the specified String to the specified defaultValues type.
	 * 
	 * @param string
	 *            The String.
	 * @param defaultValue
	 *            The default value which is used if the String can not be
	 *            converted to the given type.
	 * @return The string converted to the defaultValues type or the
	 *         defaultValue.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T convert(String string, T defaultValue) {
		if (string == null) {
			return defaultValue;
		}
		if (defaultValue instanceof String) {
			return (T) string;
		}
		if (defaultValue instanceof Number) {
			try {
				if (defaultValue instanceof Integer) {
					return (T) Integer.valueOf(string);
				} else if (defaultValue instanceof Long) {
					return (T) Long.valueOf(string);
				} else if (defaultValue instanceof Double) {
					return (T) Double.valueOf(string);
				} else if (defaultValue instanceof Float) {
					return (T) Float.valueOf(string);
				} else if (defaultValue instanceof BigInteger) {
					return (T) new BigInteger(string);
				} else if (defaultValue instanceof BigDecimal) {
					return (T) new BigDecimal(string);
				} else if (defaultValue instanceof Short) {
					return (T) Float.valueOf(string);
				} else if (defaultValue instanceof Byte) {
					return (T) Float.valueOf(string);
				}
			} catch (Exception exc) {
				return defaultValue;
			}
		}
		if (defaultValue instanceof Character) {
			if (string.length() == 1) {
				return (T) Character.valueOf(string.charAt(0));
			}
		}
		return defaultValue;
	}

	/**
	 * 
	 */
	public CommandLineArgs() {

	}

	/**
	 * @param args
	 * @return
	 */
	public static CommandLineArgs parse(String[] args) {
		CommandLineArgs cliArgs = new CommandLineArgs();
		cliArgs.load(args);
		return cliArgs;
	}

	/**
	 * @param configuration
	 */
	public void configureArg(Object... configuration) {
		ArrayList<String> aliases = new ArrayList<String>(
				configuration.length);
		ArrayList<Class<?>> arguments = new ArrayList<Class<?>>(
				configuration.length);

		for (int i = 0; i < configuration.length; i++) {
			if (configuration[i] instanceof Class) {
				arguments.add((Class<?>) configuration[i]);
			} else if (configuration[i] instanceof String) {
				String key = (String) configuration[i];
				if (key.matches(validOptionName)) {
					aliases.add(key);
				} else {
					throw new IllegalArgumentException(
							String.format(
									"Argument %d specified an alias, but does not match %s",
									i, validOptionName));
				}
			}
		}
		if (aliases.size() < 1) {
			throw new IllegalArgumentException(
					"Argument configuration does not specify any names for these options.");
		}
		Class<?>[] argumentsArray = arguments
				.toArray(new Class<?>[arguments.size()]);
		for (String alias : aliases) {
			optionsConfiguration.put(alias, argumentsArray);
		}
		String[] aliasArray = aliases.toArray(new String[aliases.size()]);
		Arrays.sort(aliasArray);
		this.aliases.add(aliasArray);
	}

	private ArrayList<Exception> exceptions = new ArrayList<Exception>();

	/**
	 * @param args
	 */
	public void load(String[] args) {
		if (args == null) {
			args = new String[0];
		}

		arguments.ensureCapacity(arguments.size() + args.length);

		boolean processOptions = true;
		for (int i = 0; i < args.length; i++) {
			if ("--".equals(args[i])) {
				processOptions = false;
				continue;
			}
			if (processOptions) {
				if (args[i].matches(validOptionName)) {
					Class<?>[] optionArgs = optionsConfiguration.get(args[i]);
					if (optionArgs == null) {
						optionArgs = new Class<?>[0];
					}
					if (i + optionArgs.length < args.length) {
						try {
							Object[] values = new Object[optionArgs.length];
							for (int j = 1; j <= optionArgs.length; j++) {
								if (optionArgs[i] == String.class) {
									values[i] = args[i + j];
								} else if (optionArgs[i] == Integer.class
										|| optionArgs[i] == int.class) {
									values[i] = Integer
											.valueOf(args[i + j]);
								} else if (optionArgs[i] == Long.class
										|| optionArgs[i] == long.class) {
									values[i] = Double.valueOf(args[i + j]);
								} else if (optionArgs[i] == Double.class
										|| optionArgs[i] == double.class) {
									values[i] = Double.valueOf(args[i + j]);
								} else if (optionArgs[i] == Float.class
										|| optionArgs[i] == float.class) {
									values[i] = Double.valueOf(args[i + j]);
								} else if (optionArgs[i] == BigInteger.class
										|| optionArgs[i] == BigInteger.class) {
									values[i] = new BigInteger(args[i + j]);
								} else if (optionArgs[i] == BigDecimal.class
										|| optionArgs[i] == BigDecimal.class) {
									values[i] = new BigDecimal(args[i + j]);
								} else if (optionArgs[i] == Short.class
										|| optionArgs[i] == short.class) {
									values[i] = Integer
											.valueOf(args[i + j]);
								} else if (optionArgs[i] == Byte.class
										|| optionArgs[i] == byte.class) {
									values[i] = Integer
											.valueOf(args[i + j]);
								} else if (optionArgs[i] == Character.class
										|| optionArgs[i] == char.class) {
									values[i] = Character.valueOf(args[i
											+ j].charAt(0));
								}
							}
							boolean unknown = true;
							for (String[] aliasList : aliases) {
								if (Arrays.binarySearch(aliasList, args[i]) >= 0) {
									for (String alias : aliasList) {
										options.put(alias, values);
									}
									unknown = false;
								}
							}
							if (unknown) {
								options.put(args[i], values);
							}
						} catch (Exception exc) {
							exceptions.add(exc);
						}
						i += optionArgs.length;
					}
				} else {
					arguments.add(args[i]);
				}
			} else {
				arguments.add(args[i]);
			}
		}
	}

	/**
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public <T> T getOption(String key, T defaultValue) {
		return getOption(key, 0, defaultValue);
	}

	/**
	 * @param key
	 * @param pos
	 * @param defaultValue
	 * @return
	 */
	public <T> T getOption(String key, int pos, T defaultValue) {
		Object[] values = options.get(key);
		if (values == null || values.length <= pos) {
			return null;
		}
		return convert(values[pos].toString(), defaultValue);
	}

	/**
	 * @param key
	 * @return
	 */
	public Object[] getOption(String key) {
		return options.get(key);
	}

	/**
	 * @param key
	 * @param pos
	 * @return
	 */
	public Object getOption(String key, int pos) {
		Object[] values = options.get(key);
		if (values == null || values.length <= pos) {
			return null;
		}
		return values[pos];
	}

	/**
	 * @param key
	 * @return
	 */
	public boolean hasOption(String key) {
		return options.containsKey(key);
	}

	/**
	 * @param pos
	 * @param defaultValue
	 * @return
	 */
	public <T> T getArg(int pos, T defaultValue) {
		if (arguments.size() <= pos) {
			return null;
		}
		return convert(arguments.get(pos), defaultValue);
	}

	/**
	 * Retrieve the argument with the given index, numbering starts at zero.
	 * 
	 * @param pos
	 *            The index of the argument.
	 * @return The string value of the argument at the specified index or null,
	 *         if there is no such argument.
	 */
	public String getArg(int pos) {
		if (pos < arguments.size()) {
			return arguments.get(pos);
		}
		return null;
	}

	/**
	 * The number of arguments parsed (not options)
	 * 
	 * @return The number of arguments.
	 */
	public int numArgs() {
		return arguments.size();
	}

	/**
	 * Exceptions that occured while parsing (like NumberFormatException etc).
	 * 
	 * @return A list of Exceptions.
	 */
	public List<Exception> getExceptions() {
		return Collections.unmodifiableList(exceptions);
	}
}