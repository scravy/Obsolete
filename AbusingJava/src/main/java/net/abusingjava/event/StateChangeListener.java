package net.abusingjava.event;

import net.abusingjava.Author;
import net.abusingjava.Since;
import net.abusingjava.Version;

@Author("Julian Fleischer")
@Version("2011-10-17")
@Since(version = "1.0", value = "2011-10-17")
public interface StateChangeListener<S extends Enum<S>> {

	void stateChanged(StateChangeEvent<S> $ev);
	
}
