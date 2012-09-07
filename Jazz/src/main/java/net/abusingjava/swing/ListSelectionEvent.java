package net.abusingjava.swing;

import java.util.EventObject;

import javax.swing.JList;

import net.abusingjava.Author;
import net.abusingjava.Version;

@Author("Julian Fleischer")
@Version("2011-08-21")
public class ListSelectionEvent extends EventObject {

	private static final long serialVersionUID = -5841351621197465478L;
	
	final int $index;
	final Object $selectedObject;
	
	public ListSelectionEvent(final int $index, final Object $object, final JList $source) {
		super($source);
		this.$index = $index;
		this.$selectedObject = $object;
	}
	
	public Object getSelectedObject() {
		return $selectedObject;
	}
	
	public int getSelectedIndex() {
		return $index;
	}
}
