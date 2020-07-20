package de.scravy.machina;

/**
 *
 *
 * @since 1.0.0
 * @author julfleischer
 */
public class GuardEvaluationException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  GuardEvaluationException(final String expression, final Exception cause) {
    super(expression, cause);
  }

}
