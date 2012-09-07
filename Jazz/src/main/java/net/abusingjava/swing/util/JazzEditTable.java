package net.abusingjava.swing.util;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import net.abusingjava.swing.util.JazzEditTable.EditTableModel.Column;

import org.jdesktop.swingx.JXTable;

public class JazzEditTable extends JXTable {

	static Object[] getColumnNames(Column[] $columns) {
		Object[] $names = new Object[$columns.length + 1];
		int $i = 0;
		for ($i = 0; $i < $columns.length; $i++) {
			$names[$i] = $columns[$i].getName();
		}
		$names[$i] = "";
		return $names;
	}

	private final EditTableModel $model;
	
	public static class EditTableModel extends DefaultTableModel {

		public static class Column {
			private final String $name;
			private final Class<?> $class;

			public Column(String $name, Class<?> $class) {
				super();
				this.$name = $name;
				this.$class = $class;
			}

			public String getName() {
				return $name;
			}

			public Class<?> getClassObject() {
				return $class;
			}
		}

		final Column[] $columns;

		EditTableModel(Column[] $columns) {
			super(getColumnNames($columns), 0);
			this.$columns = $columns;

			Object[] $row = new Object[$columns.length + 1];
			$row[$columns.length] = new Button("+");
			addRow($row);
		}

		@Override
		public Class<?> getColumnClass(int $column) {
			if ($column >= $columns.length) {
				return Button.class;
			}
			return $columns[$column].getClassObject();
		}
	}

	public static class Button {
		
		final String $text;
		
		Button(String $text) {
			this.$text = $text;
		}
		
		@Override
		public String toString() {
			return $text;
		}
	}

	private TableButtonCellEditor $editor = new TableButtonCellEditor(this);

	public JazzEditTable(final Column... $columns) {
		super();
		$model = new EditTableModel($columns);

		setAutoCreateColumnsFromModel(true);
		super.setModel($model);
		setDefaultRenderer(Button.class, new TableButtonCellRenderer());
		setDefaultEditor(Button.class, $editor);
		setRowHeight(25);
		getColumn($columns.length).setMaxWidth(40);
		getColumn($columns.length).setMinWidth(40);
		setSortable(false);
		setGridColor(Color.LIGHT_GRAY);
		setColumnControlVisible(false);
		
		$editor.addTableButtonActionListener(new TableButtonActionListener() {
			@Override
			public void buttonClicked(TableButtonActionEvent $ev) {
				if ($ev.getModelColumn() == $columns.length) {
					if ($ev.getModelRow() + 1 == $model.getRowCount()) {
						addRow();
					} else {
						$model.removeRow($ev.getModelRow());
					}
				}
			}
		});
	}
	
	public void addRow() {
		Object[] $data = new Object[$model.getColumnCount()];
		$data[$model.getColumnCount()-1] = new Button("-");
		$model.insertRow($model.getRowCount()-1, $data);
		scrollRowToVisible($model.getRowCount()-1);
	}

	@Override
	public boolean isCellEditable(int $row, int $column) {
		if ($row + 1 == getRowCount() && $column + 1 < getColumnCount()) {
			return false;
		}
		return true;
	}

	@Override
	public EditTableModel getModel() {
		return $model;
	}

	@Override
	public final void setModel(TableModel $model) {
		// pass
	}

	public static void main(String... JazzEditTable) throws Exception {
		for (LookAndFeelInfo $info : UIManager.getInstalledLookAndFeels()) {
			if ("Nimbus".equals($info.getName())) {
				UIManager.setLookAndFeel($info.getClassName());
				break;
			}
		}
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				JFrame $frame = new JFrame();
				$frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

				JazzEditTable $table = new JazzEditTable(new Column("ID",
						Integer.class), new Column("String", String.class),
						new Column("Boolean", Boolean.class));
				$table.setAutoCreateColumnsFromModel(true);
				$table.setFillsViewportHeight(true);

				$frame.setLayout(new BorderLayout());
				$frame.add("Center", new JScrollPane($table));

				$frame.pack();
				$frame.setVisible(true);
			}
		});
	}
}
