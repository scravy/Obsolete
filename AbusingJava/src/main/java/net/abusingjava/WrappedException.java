package net.abusingjava;

public class WrappedException extends RuntimeException {

	private static final long serialVersionUID = 2758425544614228703L;

	public WrappedException(final Exception $exception) {
		super($exception);
	}
}
