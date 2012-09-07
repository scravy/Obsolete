package net.abusingjava.swing.magix.types;

import net.abusingjava.AbusingJava;

public class Value {
	int $value = -1;
	Unit $unit = Unit.AUTO;
	
	public Value() {
		
	}
	
	public Value(final String $declaration) {
		if (!"auto".equalsIgnoreCase($declaration)) {
			$value = AbusingJava.parseInt($declaration.replaceAll("[^\\-0-9]", ""), 1);
			if ($declaration.endsWith("px")) {
				$unit = Unit.PIXEL;
			} else if ($declaration.endsWith("*")) {
				$unit = Unit.STAR;
			} else if ($declaration.endsWith("%")) {
				$unit = Unit.PERCENT;
			} else if ($declaration.endsWith("pt")) {
				$unit = Unit.POINT;
			} else if ("intrinsic".equalsIgnoreCase($declaration)) {
				$unit = Unit.INTRINSIC;
			}
		}
	}
	
	public Unit getUnit() {
		return $unit;
	}
	
	public int getValue() {
		return $value;
	}
}