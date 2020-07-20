package de.scravy.machina;

/**
 *
 *
 * @since 1.0.0
 * @author Julian Fleischer
 */
public class InvalidExpressionException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  InvalidExpressionException(final Exception cause) {
    super(cause);
  }
}
