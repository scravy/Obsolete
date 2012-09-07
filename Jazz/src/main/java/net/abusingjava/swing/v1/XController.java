package net.abusingjava.swing.v1;

import net.abusingjava.Author;
import net.abusingjava.Since;
import net.abusingjava.Version;

@Author("Julian Fleischer")
@Since(version = "1.0", value = "2011-10-20")
@Version("2011-10-20")
public interface XController<C> {

	C getComponent();
	
	void reset();
}
