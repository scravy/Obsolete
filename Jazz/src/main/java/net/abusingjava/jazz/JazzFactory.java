package net.abusingjava.jazz;

import javax.swing.JComponent;

public interface JazzFactory<T extends JComponent & JazzWidget> {

	T newInstance();
	
}
