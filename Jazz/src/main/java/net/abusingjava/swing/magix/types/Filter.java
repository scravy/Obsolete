package net.abusingjava.swing.magix.types;

import java.util.regex.Pattern;

public class Filter {
	
	String $tableId;
	
	String[] $columns;
	
	public Filter(final String $filters) {
		if (Pattern.matches("#([a-zA-Z][a-zA-Z0-9]*)\\([^\\(\\),]+(,[^\\(\\),]+)*\\)", $filters)) {
			$tableId = $filters.substring(1, $filters.indexOf('('));
			$columns = $filters.substring($filters.indexOf('(')+1, $filters.indexOf(')')).split(", *");
		} else if (Pattern.matches("#([a-zA-Z][a-zA-Z0-9]*)", $filters)) {
			$tableId = $filters.substring(1);
			$columns = new String[] {};
		} else {
			throw new RuntimeException("Illegal filters-Attribute");
		}
	}
	
	public String getTableName() {
		return $tableId;
	}
	
	public String[] getColumns() {
		return $columns;
	}
}