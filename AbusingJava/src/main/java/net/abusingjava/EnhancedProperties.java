package net.abusingjava;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Map.Entry;
import java.util.Properties;


/**
 * Eine Hilfsklasse, die es erlaubt typsicher int und boolean Werte aus den Properties zu lesen.
 */
@Author("Julian Fleischer")
@Since("2011-07-17")
@Version("2011-10-18")
public class EnhancedProperties extends Properties {
	private static final long serialVersionUID = 685621343697094058L;

	/**
	 * 
	 */
	public Integer getInteger(final String $name) {
		try {
			return Integer.parseInt(getProperty($name));
		} catch (Exception $exc) {
			return null;
		}
	}
	
	/**
	 * 
	 */
	public Float getFloat(final String $name) {
		try {
			return Float.parseFloat(getProperty($name));
		} catch (Exception $exc) {
			return null;
		}
	}
	
	/**
	 * 
	 */
	public boolean getBoolean(final String $name) {
		return Boolean.parseBoolean(getProperty($name));
	}
	
	/**
	 * Setzt eine Property und gibt dieses EnhancedProperties-Objekt zurück (für Method-Chaining).
	 * 
	 * @param $name Die zu schreibene Eigenschaft.
	 * @param $value Der zu setzende Wert als Objekt (wird mittels {@link Object#toString()} ermittelt).
	 * 
	 * @throws IllegalArgumentException Wenn $value null ist.
	 * @return Dieses Objekt (für Method-Chaining).
	 */
	public EnhancedProperties setProperty(final String $name, final Object $value) {
		if ($value == null) {
			throw new IllegalArgumentException("$value must not be null");
		}
		super.setProperty($name, $value.toString());
		return this;
	}

	/**
	 * Setzt eine Property und gibt dieses EnhancedProperties-Objekt zurück (für Method-Chaining).
	 * 
	 * @param $name Die zu schreibene Eigenschaft.
	 * @param $value Der zu setzende Wert.
	 * 
	 * @throws IllegalArgumentException Wenn $value null ist.
	 * @return Dieses Objekt (für Method-Chaining).
	 */
	@Override
	public synchronized EnhancedProperties setProperty(final String $name, final String $value) {
		if ($value == null) {
			throw new IllegalArgumentException("$value must not be null");
		}
		super.setProperty($name, $value);
		return this;
	}
	
	/**
	 * Importiere Properties von einem anderen Properties-Objekt.
	 * 
	 * @param $properties Das andere Properties-Objekt.
	 * @return Dieses Objekt (für Method-Chaining).
	 */
	public EnhancedProperties merge(final Properties $properties) {
		for (Entry<Object, Object> $entry : $properties.entrySet()) {
			put($entry.getKey(), $entry.getValue());
		}
		return this;
	}

	/**
	 * 
	 */
	public boolean getBoolean(final String $name, final boolean $default) {
		String $value = getProperty($name);
		if ($value == null) {
			return $default;
		}
		return ("true".equalsIgnoreCase($value) || "yes".equalsIgnoreCase($value));
	}

	/**
	 * 
	 */
	public int getInteger(final String $name, final int $default) {
		Integer $value = getInteger($name);
		if ($value == null) {
			return $default;
		}
		return $value;
	}


	/**
	 * 
	 */
	public int getProperty(final String $propertyName, final int $defaultValue) {
		Integer $value = getInteger($propertyName);
		if ($value == null) {
			return $defaultValue;
		}
		return $value;
	}
	
	/**
	 * 
	 */
	public float getProperty(final String $propertyName, final float $defaultValue) {
		Float $value = getFloat($propertyName);
		if ($value == null) {
			return $defaultValue;
		}
		return $value;
	}
	
	@Override
	public void store(final OutputStream $out, final String $comments) throws IOException {
		ByteArrayOutputStream $bytes = new ByteArrayOutputStream();
		super.store($bytes, $comments);
		String[] $lines = $bytes.toString().split("\n");
		Arrays.sort($lines);
		$out.write(AbusingStrings.implode("\n", $lines).getBytes());
	}
}