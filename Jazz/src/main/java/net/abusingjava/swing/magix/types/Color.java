package net.abusingjava.swing.magix.types;

import java.lang.reflect.Field;

public class Color {
	java.awt.Color $color;
	
	public Color(String $color) {
		try {
			Field $f = java.awt.Color.class.getField($color);
			this.$color = (java.awt.Color) $f.get(null);
		} catch (Exception $exc) {
			if ($color.charAt(0) == '#') {
				$color = "0x" + $color.substring(1);
			} else if ($color.length() == 6) {
				$color = "0x" + $color;
			}
			this.$color = java.awt.Color.decode($color.toLowerCase());
		}
	}

	public Color(final java.awt.Color $color) {
		this.$color = $color;
	}
	
	public java.awt.Color getColor() {
		return $color;
	}
}