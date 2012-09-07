package net.abusingjava.event;

public interface OffersStateChangeEvents<S extends Enum<S> & State> {

	public void addStateChangeListener(final StateChangeListener<S> $listener);

	public void removeStateChangeListener(final StateChangeListener<S> $listener);

	public StateChangeListener<S>[] getStateChangeListeners();
	
}
