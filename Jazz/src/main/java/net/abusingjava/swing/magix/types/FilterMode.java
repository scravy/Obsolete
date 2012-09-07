package net.abusingjava.swing.magix.types;

import net.abusingjava.Author;
import net.abusingjava.Since;
import net.abusingjava.Version;

@Author("Julian Fleischer")
@Version("2011-08-25")
@Since(value = "2011-08-25", version = "1.0")
public class FilterMode {

	enum Mode {
		AND, OR
	}
	
	final Mode $mode;
	
	public FilterMode(final String $filter) {
		$mode = ("or".equalsIgnoreCase($filter)) ? Mode.OR : Mode.AND;
	}
	
	public boolean isAnd() {
		return $mode == Mode.AND;
	}
	
	public boolean isOr() {
		return $mode == Mode.OR;
	}
}
