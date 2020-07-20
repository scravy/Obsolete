package de.scravy.machina;

/**
 * A marker interface for all kinds of listeners that can be attached to a state
 * machine by {@link StateMachineBuilder#withListener(StateMachineListener)}.
 * 
 * @author Julian Fleischer
 * @since 1.0.0
 *
 * @param <S>
 *          States.
 * @param <T>
 *          Event Types.
 */
public interface StateMachineListener<S, E, T, C> {

}
