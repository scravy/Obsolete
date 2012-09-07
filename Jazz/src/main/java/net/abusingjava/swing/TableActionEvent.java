package net.abusingjava.swing;

import java.util.EventObject;

import javax.swing.JTable;

public class TableActionEvent extends EventObject {

	private static final long serialVersionUID = 5902067393858116983L;
	
	final JTable $table;
	final int $row;
	final int $col;
	final int $modelRow;
	final int $modelCol;
	
	public TableActionEvent(final JTable $table, final int $row, final int $col) {
		super($table);
		this.$table = $table;
		this.$row = $row;
		this.$col = $col;
		this.$modelRow = $table.convertRowIndexToModel($row);
		this.$modelCol = $table.convertColumnIndexToModel($col);
	}
	
	public int getRowIndex() {
		return $row;
	}
	
	public int getColumnIndex() {
		return $col;
	}
	
	public int getModelRowIndex() {
		return $modelRow;
	}
	
	public int getModelColumnIndex() {
		return $modelCol;
	}
	
	@Override
	public JTable getSource() {
		return $table;
	}
	
}
