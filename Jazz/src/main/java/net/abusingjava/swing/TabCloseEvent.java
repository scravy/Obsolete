package net.abusingjava.swing;

import java.util.EventObject;

import javax.swing.JTabbedPane;

public class TabCloseEvent extends EventObject {

	private static final long serialVersionUID = 2888790658056534955L;
	
	boolean $canceled = false;
	
	public TabCloseEvent(final JTabbedPane $tabs) {
		super($tabs);
	}
	
	public void cancel() {
		$canceled = true;
	}
	
	public boolean isCanceled() {
		return $canceled;
	}
	
}
