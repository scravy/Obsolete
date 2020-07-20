package de.scravy.machina;

/**
 *
 * @since 1.2.0
 * @author Julian Fleischer
 *
 * @param <S>
 *          State
 */
public interface StateWithParent<S> {

  S getParent();
}
