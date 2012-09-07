package net.abusingjava.swing.util;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

public class TableButtonCellEditor extends AbstractCellEditor implements
		TableCellEditor {

	final protected JComponent $editorComponent;
	
	final protected EditorDelegate $delegate;

	protected int $clickCountToStart = 1;

	final protected JTable $parentTable;

	final List<TableButtonActionListener> $buttonListeners = new ArrayList<TableButtonActionListener>(
			2);

	public TableButtonCellEditor(final JTable $parent) {
		this($parent, "");
	}

	public TableButtonCellEditor(final JTable $parent, final String $text) {
		this($parent, new JButton($text));
	}

	public TableButtonCellEditor(final JTable $parent, final JButton $button) {
		$parentTable = $parent;
		$editorComponent = $button;
		$delegate = new EditorDelegate() {

			@Override
			public void setValue(Object $value) {
				super.setValue($value);
				$button.setText($value == null ? "" : $value.toString());
			}

		};
		$button.addActionListener($delegate);
		$button.setRequestFocusEnabled(false);
	}

	public void addTableButtonActionListener(TableButtonActionListener $l) {
		$buttonListeners.add($l);
	}

	public void removeTableButtonActionListener(TableButtonActionListener $l) {
		$buttonListeners.remove($l);
	}

	public Component getComponent() {
		return $editorComponent;
	}

	@Override
	public Object getCellEditorValue() {
		return $delegate.getCellEditorValue();
	}

	@Override
	public boolean isCellEditable(EventObject $ev) {
		return $delegate.isCellEditable($ev);
	}

	@Override
	public boolean shouldSelectCell(EventObject $ev) {
		return $delegate.shouldSelectCell($ev);
	}

	@Override
	public boolean stopCellEditing() {
		return $delegate.stopCellEditing();
	}

	@Override
	public void cancelCellEditing() {
		$delegate.cancelCellEditing();
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		$delegate.setValue(value);
		return $editorComponent;
	}

	class EditorDelegate implements ActionListener, ItemListener, Serializable {

		protected Object value;

		public Object getCellEditorValue() {
			return value;
		}

		public void setValue(Object value) {
			this.value = value;
		}

		public boolean isCellEditable(EventObject anEvent) {
			if (anEvent instanceof MouseEvent) {
				return ((MouseEvent) anEvent).getClickCount() >= $clickCountToStart;
			}
			return true;
		}

		public boolean shouldSelectCell(EventObject anEvent) {
			return true;
		}

		public boolean startCellEditing(EventObject anEvent) {
			return true;
		}

		public boolean stopCellEditing() {
			int $col = $parentTable.getEditingColumn();
			int $row = $parentTable.getEditingRow();
			TableButtonActionEvent $ev = new TableButtonActionEvent(
					$parentTable.convertRowIndexToModel($row),
					$parentTable.convertColumnIndexToModel($col), $row, $col);
			fireEditingStopped();
			fireTableButtonActionEvent($ev);
			return true;
		}

		public void cancelCellEditing() {
			fireEditingCanceled();
		}

		@Override
		public void actionPerformed(ActionEvent $ev) {
			TableButtonCellEditor.this.stopCellEditing();
		}

		@Override
		public void itemStateChanged(ItemEvent $ev) {
			TableButtonCellEditor.this.stopCellEditing();
		}
	}

	public void fireTableButtonActionEvent(TableButtonActionEvent $ev) {
		for (TableButtonActionListener $listener : $buttonListeners) {
			$listener.buttonClicked($ev);
		}
	}
}
