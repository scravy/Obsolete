package net.abusingjava.swing.util;

public class TableButtonActionEvent {
	private final int modelRow;
	private final int modelColumn;
	private final int tableRow;
	private final int tableColumn;

	public TableButtonActionEvent(int modelRow, int modelColumn,
			int tableRow, int tableColumn) {
		this.modelRow = modelRow;
		this.modelColumn = modelColumn;
		this.tableRow = tableRow;
		this.tableColumn = tableColumn;
	}

	public int getModelRow() {
		return modelRow;
	}

	public int getModelColumn() {
		return modelColumn;
	}

	public int getTableRow() {
		return tableRow;
	}

	public int getTableColumn() {
		return tableColumn;
	}

}