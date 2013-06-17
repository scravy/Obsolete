package net.scravy.technetium.util;

import java.io.IOException;

/**
 * A RuntimeException that wraps IOExceptions and IOExceptions only.
 * 
 * @author Julian Fleischer
 * @since 1.0
 */
public class RuntimeIOException extends RuntimeException {

	private static final long serialVersionUID = -7113014887835382129L;

	/**
	 * A RuntimeIOException can only be constructed from an existing
	 * IOException.
	 * 
	 * @param exc
	 *            The IOException.
	 */
	public RuntimeIOException(IOException exc) {
		super(exc);

		if (exc == null) {
			throw new IllegalArgumentException(
					"A RuntimeIOException must be cosntructed from an existing IOException. The given IOException is null.");
		}
	}

	/**
	 * A RuntimeIOException can only be constructed from an existing
	 * IOException.
	 * 
	 * @param message
	 *            A detailed Error message explaining the condition or context
	 *            of why this exception may have happened.
	 * @param exc
	 *            The IOException.
	 */
	public RuntimeIOException(String message, IOException exc) {
		super(exc);
	}

	@Override
	public IOException getCause() {
		return (IOException) super.getCause();
	}
}