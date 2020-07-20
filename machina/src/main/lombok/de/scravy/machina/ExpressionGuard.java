package de.scravy.machina;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

import org.mvel2.CompileException;
import org.mvel2.MVEL;

/**
 * A Guard which is defined by an MVEL expression.
 *
 * @author Julian Fleischer
 *
 * @since 1.0.0
 *
 * @param <C>
 *          Context
 */
@Value
@EqualsAndHashCode(of = { "expression" })
public class ExpressionGuard<E, C> implements Guard<E, C>, Serializable {

  private static final long serialVersionUID = 1L;

  private final @NonNull String expression;
  private final @NonNull Serializable compiledExpression;

  /**
   * Create a condition with an MVEL expression as guard.
   *
   * @since 1.0.0
   *
   * @param expression
   *          The expression. You can use the variables <code>e</code>,
   *          <code>event</code>, <code>c</code>, and <code>context</code> to
   *          refer to the event of type <code>E</code> and the context of type
   *          <code>C</code>.
   * @throws InvalidExpressionException
   *           If the expression failed to compile.
   * @throws IllegalArgumentException
   *           If the given expression is <code>null</code>.
   */
  public ExpressionGuard(final String expression)
      throws InvalidExpressionException, IllegalArgumentException {
    if (expression == null) {
      throw new IllegalArgumentException("`expression' must not be null.");
    }
    try {
      this.compiledExpression = MVEL.compileExpression(expression);
      this.expression = expression;
    } catch (final CompileException exc) {
      throw new InvalidExpressionException(exc);
    }
  }

  private static <C, E> Map<String, Object> evaluationContext(
      final E event,
      final C context) {
    final Map<String, Object> map = new HashMap<>();
    map.put("e", event);
    map.put("event", event);
    map.put("c", context);
    map.put("context", context);
    return map;
  }

  @Override
  public boolean evaluate(final E event, final C context) {
    try {
      final Object result = MVEL.executeExpression(
          this.compiledExpression, evaluationContext(event, context));
      if (result instanceof Boolean && ((Boolean) result).booleanValue()) {
        return true;
      }
      return false;
    } catch (final Exception exc) {
      throw new GuardEvaluationException(this.expression, exc);
    }
  }

  @Override
  public String toString() {
    return expression;
  }
}
