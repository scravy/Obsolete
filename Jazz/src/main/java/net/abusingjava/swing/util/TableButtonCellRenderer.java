package net.abusingjava.swing.util;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class TableButtonCellRenderer implements TableCellRenderer {

	private JButton $rendererComponent = new JButton();
	private JLabel $empty = new JLabel();
	
	public TableButtonCellRenderer() {
		
	}
	
	@Override
	public Component getTableCellRendererComponent(JTable $table, Object $value,
			boolean isSelected, boolean hasFocus, int row, int col) {
		if ($value == null) {
			return $empty;
		}
		$rendererComponent.setText($value.toString());
		return $rendererComponent;
	}

}