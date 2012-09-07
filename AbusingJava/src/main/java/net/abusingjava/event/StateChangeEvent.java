package net.abusingjava.event;

import java.util.EventObject;

import net.abusingjava.Author;
import net.abusingjava.Since;
import net.abusingjava.Version;

@Author("Julian Fleischer")
@Version("2011-10-17")
@Since(version = "1.0", value = "2011-10-17")
public class StateChangeEvent<S extends Enum<S>> extends EventObject {
	
	private static final long serialVersionUID = -2565054049755092250L;

	private final S $oldState;
	private final S $newState;
	
	public StateChangeEvent(final Object $source, final S $oldState, final S $newState) {
		super($source);
		this.$oldState = $oldState;
		this.$newState = $newState;
	}

	public S getOldState() {
		return $oldState;
	}
	
	public S getNewState() {
		return $newState;
	}

}
