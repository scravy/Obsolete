package net.abusingjava.swing.magic;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import net.abusingjava.AbusingArrays;
import net.abusingjava.swing.MagicPanel;
import net.abusingjava.swing.TableActionEvent;
import net.abusingjava.swing.magic.Table.Column;
import net.abusingjava.swing.magix.types.Color;
import net.abusingjava.swing.magix.types.FilterMode;
import net.abusingjava.swing.magix.types.JavaType;
import net.abusingjava.swing.magix.types.MethodType;
import net.abusingjava.swing.magix.types.Unit;
import net.abusingjava.swing.magix.types.Value;
import net.abusingjava.swing.util.TableButtonCellEditor;
import net.abusingjava.swing.util.TableButtonCellRenderer;
import net.abusingjava.xml.XmlAttribute;
import net.abusingjava.xml.XmlChildElements;
import net.abusingjava.xml.XmlElement;
import net.abusingjava.xml.XmlTextContent;

import org.jdesktop.swingbinding.JTableBinding;
import org.jdesktop.swingx.JXTable;
import org.slf4j.LoggerFactory;

@XmlElement("table")
public class Table extends Component implements Iterable<Column> {

	@XmlChildElements
	Column[] $columns;

	@XmlAttribute
	Boolean $sortable;

	@XmlAttribute
	Boolean $editable;

	@XmlAttribute("grid-color")
	Color $gridColor;

	@XmlAttribute("column-control-visible")
	Boolean $columnControlVisible;

	@XmlAttribute("horizontal-scroll-enabled")
	Boolean $horizontalScrollEnabled;

	@XmlAttribute("auto-pack")
	Boolean $autoPack;

	@XmlAttribute("column-margin")
	Integer $columnMargin;

	@XmlAttribute("terminate-edit-on-focus-lost")
	Boolean $terminateEditOnFocusLost;

	@XmlAttribute("sorts-on-update")
	Boolean $sortsOnUpdate;

	@XmlAttribute("fix-vertical-align")
	boolean $fixVerticalAlign;
	
	@XmlAttribute("row-height")
	Value $rowHeight;

	@XmlAttribute("filter-mode")
	FilterMode $filterMode = new FilterMode("and");

	@XmlAttribute
	MethodType $ondblclick;

	@XmlAttribute
	MethodType $onselect;

	@XmlAttribute("button-classes")
	String $buttonClasses = "";

	boolean $selectedFilter = false;

	@SuppressWarnings("serial")
	public static class ComboBoxRenderer extends JComboBox implements TableCellRenderer {

		public ComboBoxRenderer(final Class<?> $class) {
			Object[] $array = $class.getEnumConstants();
			for (Object $x : $array) {
				this.addItem($x);
			}
		}

		@Override
		public JComponent getTableCellRendererComponent(
				final JTable $table, final Object $value, final boolean $2,
				final boolean $3, final int $row, final int $col) {

			this.setSelectedItem($value);

			return this;
		}
	}

	public class Filter {

		final int[] $columnIndizes;
		String $filterString = "";

		Filter(final String[] $columns) {

			if (Table.this instanceof MultiList) {
				this.$columnIndizes = new int[]{1};
			} else {
				this.$columnIndizes = new int[$columns.length];
				for (int $i = 0; $i < $columns.length; $i++) {
					$columnIndizes[$i] = ((JXTable) $realComponent).getColumn($columns[$i]).getModelIndex();
				}
			}
		}

		public void setFilterString(final String $filterString) {
			this.$filterString = $filterString.toLowerCase();
		}

		public boolean apply(final javax.swing.RowFilter.Entry<? extends Object, ? extends Object> $entry) {
			if ($filterString.isEmpty()) {
				if ($selectedFilter) {
					return false;
				}
				return true;
			}
			boolean $result = false;
			for (int $index : $columnIndizes) {
				$result = $result || $entry.getStringValue($index).toLowerCase().contains($filterString);
			}
			return $result;
		}
	}

	List<Filter> $filterList = new ArrayList<Filter>();

	public Filter newFilter(final String[] $columns) {
		Filter $filter = new Filter($columns);
		$filterList.add($filter);
		return $filter;
	}

	public void updateFilters() {
		List<RowFilter<Object, Object>> $filters = new LinkedList<RowFilter<Object, Object>>();

		for (final Filter $f : $filterList) {
			$filters.add(new RowFilter<Object, Object>() {
				@Override
				public boolean include(final javax.swing.RowFilter.Entry<? extends Object, ? extends Object> $entry) {
					return $f.apply($entry);
				}
			});
		}

		if ($selectedFilter) {
			$filters.add(new RowFilter<Object, Object>() {
				@Override
				public boolean include(final javax.swing.RowFilter.Entry<? extends Object, ? extends Object> $entry) {
					return $entry.getValue(0) == Boolean.TRUE;
				}
			});
		}

		if ($filters.size() > 0) {

			RowFilter<Object, Object> $filter = $filterMode.isAnd()
					? RowFilter.andFilter($filters)
					: RowFilter.orFilter($filters);

			((JXTable) $realComponent).setRowFilter($filter);
		}
	}

	@SuppressWarnings("rawtypes")
	private JTableBinding $binding = null;

	@SuppressWarnings("rawtypes")
	public void setBinding(final JTableBinding $binding) {
		this.$binding = $binding;
	}

	public void clearBinding() {
		if ($binding != null) {
			$binding.unbind();
			$binding = null;
		}
	}

	@XmlElement("col")
	public static class Column {
		@XmlAttribute
		JavaType $type = new JavaType(java.lang.Object.class);

		@XmlAttribute("min-width")
		Value $minWidth;

		@XmlAttribute("max-width")
		Value $maxWidth;

		@XmlAttribute("width")
		Value $width;

		@XmlTextContent
		String $text = "";

		@XmlAttribute
		Boolean $editable;

		@XmlAttribute("invalid-date")
		String $invalidDate;

		@XmlAttribute("fix-vertical-align")
		Boolean $fixVerticalAlign;

		@XmlAttribute("date-format")
		String $dateFormat = "yyyy-MM-dd HH:mm:ss";

		public Class<?> getJavaType() {
			return $type.getJavaType();
		}
	}

	public Column getColumn(final String $label) {
		for (Column $c : $columns) {
			if ($c.$text.equals($label)) {
				return $c;
			}
		}
		return null;
	}

	@SuppressWarnings("serial")
	@Override
	public void create(final MagicPanel $main, final MagicPanel $parent) {
		if ($autoPack == null) {
			$autoPack = true;
		}
		if ($editable == null) {
			$editable = false;
		}
		if ($sortable == null) {
			$sortable = true;
		}
		if ($columnControlVisible == null) {
			$columnControlVisible = true;
		}

		final String[] $columnHeaders = new String[$columns.length];
		for (int $i = 0; $i < $columns.length; $i++) {
			$columnHeaders[$i] = $columns[$i].$text;
			if ($columns[$i].$fixVerticalAlign == null) {
				$columns[$i].$fixVerticalAlign = Table.this.$fixVerticalAlign;
			}
		}

		DefaultTableModel $model = new DefaultTableModel($columnHeaders, 0) {
			private static final long serialVersionUID = -135732270243460558L;

			@Override
			public Class<?> getColumnClass(final int $col) {
				try {
					return $columns[$col].$type.getJavaType();
				} catch (Exception $exc) {
					return Object.class;
				}
			}
		};

		@SuppressWarnings("serial")
		final JXTable $c = new JXTable($model) {

			@Override
			public boolean isCellEditable(final int $rowIndex, int $colIndex) {
				try {
					$colIndex = convertColumnIndexToModel($colIndex);
					if ($columns[$colIndex].$editable != null) {
						return $columns[$colIndex].$editable;
					}
				} catch (Exception $exc) {
					LoggerFactory.getLogger(getClass()).warn("This should not happen at all.", $exc);
				}
				return $editable;
			}
		};

		$c.setSortable($sortable);
		$c.setColumnControlVisible($columnControlVisible);
		if ($horizontalScrollEnabled != null) {
			$c.setHorizontalScrollEnabled($horizontalScrollEnabled);
		}
		if ($sortsOnUpdate != null) {
			$c.setSortsOnUpdates($sortsOnUpdate);
		}
		if ($terminateEditOnFocusLost != null) {
			$c.setTerminateEditOnFocusLost($terminateEditOnFocusLost);
		}
		if (($rowHeight != null) && ($rowHeight.getUnit() == Unit.PIXEL) && ($rowHeight.getValue() >= 0)) {
			$c.setRowHeight($rowHeight.getValue());
		}
		if (($columnMargin != null) && ($columnMargin >= 0)) {
			$c.setColumnMargin($columnMargin);
		}
		if ($gridColor != null) {
			$c.setGridColor($gridColor.getColor());
		}
		for (int $i = 0; $i < $columns.length; $i++) {
			Class<?> $colType = $columns[$i].$type.getJavaType();

			if ($colType.isEnum()) {
				JComboBox $proto = new JComboBox($colType.getEnumConstants());
				$c.setDefaultEditor($colType, new DefaultCellEditor($proto));
				$c.setDefaultRenderer($colType, new ComboBoxRenderer($colType));
			}
			if (($columns[$i].$maxWidth != null) && ($columns[$i].$maxWidth.getUnit() == Unit.PIXEL)) {
				$c.getColumn($columnHeaders[$i]).setMaxWidth($columns[$i].$maxWidth.getValue());
			}
			if (($columns[$i].$minWidth != null) && ($columns[$i].$minWidth.getUnit() == Unit.PIXEL)) {
				$c.getColumn($columnHeaders[$i]).setMinWidth($columns[$i].$minWidth.getValue());
			}
			if (($columns[$i].$width != null) && ($columns[$i].$width.getUnit() == Unit.PIXEL)) {
				$c.getColumn($columnHeaders[$i]).setPreferredWidth($columns[$i].$width.getValue());
			}
		}

		$c.setDefaultRenderer(String.class, new DefaultTableCellRenderer() {

			@Override
			public java.awt.Component getTableCellRendererComponent(final JTable $table,
					final Object $value, final boolean $isSelected, final boolean $hasFocus, final int $row,
					final int $column) {
				super.getTableCellRendererComponent($table, $value, $isSelected, $hasFocus, $row, $column);

				if ($columns[$column].$fixVerticalAlign) {
					this.setVerticalAlignment(SwingConstants.TOP);
				}

				return this;
			}
		});

		$c.setDefaultRenderer(Integer.class, new DefaultTableCellRenderer() {

			@Override
			public java.awt.Component getTableCellRendererComponent(final JTable $table,
					final Object $value, final boolean $isSelected, final boolean $hasFocus, final int $row,
					final int $column) {
				super.getTableCellRendererComponent($table, $value, $isSelected, $hasFocus, $row, $column);

				if ($columns[$column].$fixVerticalAlign) {
					this.setVerticalAlignment(SwingConstants.TOP);
				}
				if ($value instanceof Integer) {
					this.setHorizontalAlignment(SwingConstants.RIGHT);
				}

				return this;
			}
		});

		$c.setDefaultRenderer(java.util.Date.class, new DefaultTableCellRenderer() {

			@Override
			public java.awt.Component getTableCellRendererComponent(final JTable $table,
					final Object $value, final boolean $isSelected, final boolean $hasFocus, final int $row,
					final int $column) {

				super.getTableCellRendererComponent($table, $value, $isSelected, $hasFocus, $row, $column);
				
				if ($value instanceof Date) {
					Date $date = (Date) $value;
					String $formattedDate = new SimpleDateFormat($columns[$column].$dateFormat).format($date);

					if (($columns[$column].$invalidDate != null)
							&& ($date.compareTo(new Date(System.currentTimeMillis()
									- (1000L * 60L * 60L * 24L * 365L * 300L))) < 0)) {
						$formattedDate = $columns[$column].$invalidDate;
					}
					this.setText($formattedDate);
				}

				return this;
			}

		});
		
		for (String $className : $buttonClasses.split(" ,;")) {
			try {
				Class<?> $class = Class.forName($className);
				
				$c.setDefaultRenderer($class, new TableButtonCellRenderer());
				$c.setDefaultEditor($class, new TableButtonCellEditor($c));
				
			} catch (ClassNotFoundException $exc) {
				
			}
		}

		$realComponent = $c;
		$component = new JScrollPane($c);
		$c.setFillsViewportHeight(true);

		if (($ondblclick != null) && !$editable) {
			$c.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(final MouseEvent $ev) {
					if ($ev.getClickCount() == 2) {
						new Thread(eventListener($ondblclick, new TableActionEvent($c,
								$c.rowAtPoint($ev.getPoint()),
								$c.columnAtPoint($ev.getPoint())
								))).start();
					}
				}
			});
		}
		if ($onselect != null) {
			$c.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

				@Override
				public void valueChanged(final ListSelectionEvent $ev) {
					if (!$ev.getValueIsAdjusting()) {
						try {
							new Thread(eventListener($onselect, new TableActionEvent($c,
									$c.getSelectedRow(),
									$c.getSelectedColumn()
									))).start();
						} catch (Exception $exc) {
							$exc.printStackTrace(System.err);
						}
					}
				}

			});
			/*
			 * $c.addMouseListener(new MouseAdapter() {
			 * 
			 * @Override public void mouseClicked(final MouseEvent $ev) { if
			 * ($ev.getClickCount() == 1) { new Thread(eventListener($onselect,
			 * new TableActionEvent($c, $c.rowAtPoint($ev.getPoint()),
			 * $c.columnAtPoint($ev.getPoint()) ))).start(); } } });
			 */
		}

		super.create($main, $parent);
	}
	@Override
	public Iterator<Column> iterator() {
		return AbusingArrays.array($columns).iterator();
	}

	public boolean getAutoPack() {
		return $autoPack;
	}

}