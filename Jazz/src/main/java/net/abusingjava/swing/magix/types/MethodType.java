package net.abusingjava.swing.magix.types;

import net.abusingjava.functions.AbusingFunctions;
import net.abusingjava.functions.DynamicInvocationTargetException;

public class MethodType {
	public static enum SpecialMethodType {
		GOTO, SHOW, HIDE, ENABLE, DISABLE
	}

	final String $method;

	final SpecialMethodType $specialMethodType;

	final String $specialMethodArg;

	public MethodType(String $method) {
		if (($method == null) || $method.isEmpty()) {
			throw new IllegalArgumentException("$method name must be something."); // TODO
		}
		$method = $method.trim();

		if ($method.matches("go[tT]o\\(.+\\)")) {
			$specialMethodType = SpecialMethodType.GOTO;
		} else if ($method.matches("show\\(.+\\)")) {
			$specialMethodType = SpecialMethodType.SHOW;
		} else if ($method.matches("hide\\(.+\\)")) {
			$specialMethodType = SpecialMethodType.HIDE;
		} else if ($method.matches("enable\\(.+\\)")) {
			$specialMethodType = SpecialMethodType.ENABLE;
		} else if ($method.matches("disable\\(.+\\)")) {
			$specialMethodType = SpecialMethodType.DISABLE;
		} else {
			this.$specialMethodType = null;
			if ($method.indexOf('(') > 0) {
				//String $args = $method.substring($method.indexOf('(')+1);
				$method = $method.substring(0, $method.indexOf('('));
			}
		}

		if ($specialMethodType != null) {
			$specialMethodArg = $method.substring($method.indexOf('(') + 1, $method.lastIndexOf(')'));
		} else {
			$specialMethodArg = null;
		}
		this.$method = $method;
	}

	public boolean isSpecialMethod() {
		return $specialMethodType != null;
	}

	public SpecialMethodType getSpecialMethodType() {
		return $specialMethodType;
	}

	public String getSpecialMethodArg() {
		return $specialMethodArg;
	}

	public void call(final Object $obj, final Object... $args) {
		try {
			AbusingFunctions.accessibleCallback($obj, $method).call($args);
		} catch (DynamicInvocationTargetException $exc) {
			if ($exc.getCause() instanceof NoSuchMethodException) {
				AbusingFunctions.accessibleCallback($obj, $method).call();
			} else {
				throw $exc;
			}
		}
	}
}
