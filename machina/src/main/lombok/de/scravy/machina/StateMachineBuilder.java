package de.scravy.machina;

import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import lombok.experimental.Wither;
import de.scravy.cons.Cons;
import de.scravy.machina.StateMachineBuilderException.ErrorType;
import de.scravy.machina.Transition.TransitionBuilder2;

/**
 * A builder to create {@link SimpleStateMachine}s.
 *
 * @since 1.0.0
 * @author Julian Fleischer
 *
 * @param <S>
 *          State
 * @param <E>
 *          Event
 * @param <T>
 *          Event Type
 * @param <C>
 *          Context
 * @param <M>
 *          Target Machine Type
 */
public final class StateMachineBuilder<S, E, T, C, M extends StateMachine<S, E, T>> {

  private final Class<M> targetType;
  private final @Wither S initialState;
  private final @Wither EventTypeClassifier<E, T> eventTypeClassifier;
  private final BaseDefinition<S, E, T, C> baseDefinition;
  private final Cons<Transition<S, T>> transitions;
  private final Cons<StateMachineListener<S, E, T, C>> listeners;

  private StateMachineBuilder(
      final Class<M> targetType,
      final S initialState,
      final EventTypeClassifier<E, T> eventTypeClassifier,
      final BaseDefinition<S, E, T, C> baseDefinition,
      final Cons<Transition<S, T>> transitions,
      final Cons<StateMachineListener<S, E, T, C>> listeners) {

    final Class<S> states = baseDefinition.getStates();

    if (StateWithEnterHandler.class.isAssignableFrom(states)) {
      final Type[] ifaces = states.getGenericInterfaces();
      for (final Type iface : ifaces) {
        if (iface instanceof ParameterizedType) {
          final ParameterizedType giface = (ParameterizedType) iface;
          if (giface.getRawType() == StateWithEnterHandler.class) {
            final Type[] types = giface.getActualTypeArguments();
            if (types[0] != baseDefinition.getEvents()
                || types[1] != baseDefinition.getContext()) {
              throw new StateMachineBuilderException(
                  ErrorType.STATE_WITH_ENTER_HANDLER_ILLEGALY_PARAMETERIZED);
            }
          }
        }
      }
    }
    if (StateWithExitHandler.class.isAssignableFrom(states)) {
      final Type[] ifaces = states.getGenericInterfaces();
      for (final Type iface : ifaces) {
        if (iface instanceof ParameterizedType) {
          final ParameterizedType giface = (ParameterizedType) iface;
          if (giface.getRawType() == StateWithExitHandler.class) {
            final Type[] types = giface.getActualTypeArguments();
            if (types[0] != baseDefinition.getEvents()
                || types[1] != baseDefinition.getContext()) {
              throw new StateMachineBuilderException(
                  ErrorType.STATE_WITH_EXIT_HANDLER_ILLEGALY_PARAMETERIZED);
            }
          }
        }
      }
    }

    this.targetType = targetType;
    this.initialState = initialState;
    this.eventTypeClassifier = eventTypeClassifier;
    this.baseDefinition = baseDefinition;
    this.transitions = transitions;
    this.listeners = listeners;
  }

  /**
   * Creates a new Builder that builds a state machine which accepts any kinds
   * of objects as States, Events, etc.
   *
   * This method is most useful for playing around, quickly prototyping.
   *
   * @since 1.2.0
   * @return A new Builder that creates a {@link GenericStateMachine}.
   */
  public static StateMachineBuilder<Object, Object, Object, Void, GenericStateMachine>
      create() {
    return new StateMachineBuilder<Object, Object, Object, Void, GenericStateMachine>(
        null, null, null,
        new BaseDefinition<Object, Object, Object, Void>(
            Object.class, Object.class, Object.class, null),
        Cons.<Transition<Object, Object>> empty(),
        Cons.<StateMachineListener<Object, Object, Object, Void>> empty());
  }

  /**
   * Creates a new Builder that builds simple state machines.
   *
   * @since 1.0.0
   * @param states
   *          The class that all states inherit from.
   * @param events
   *          The class that all events inherit from.
   * @return The new Builder that builds simple state machines.
   */
  public static <S, E> StateMachineBuilder<S, E, E, Void, SimpleStateMachine<S, E, E>>
      create(
          final Class<S> states,
          final Class<E> events) {
    return new StateMachineBuilder<S, E, E, Void, SimpleStateMachine<S, E, E>>(
        null, null, null,
        new BaseDefinition<S, E, E, Void>(states, events, events, null),
        Cons.<Transition<S, E>> empty(),
        Cons.<StateMachineListener<S, E, E, Void>> empty());
  }

  /**
   * Creates a new builder that builds state machines with a distinct event
   * type.
   *
   * @since 1.0.0
   * @param states
   *          The class that all states inherit from.
   * @param events
   *          The class that all events inherit from.
   * @param eventTypes
   *          The class that all event types inherit from.
   * @return The new builder that builds state machines with a distinct event
   *         type.
   */
  public static <S, E, T> StateMachineBuilder<S, E, T, Void, SimpleStateMachine<S, E, T>>
      create(
          final Class<S> states,
          final Class<E> events,
          final Class<T> eventTypes) {
    return new StateMachineBuilder<S, E, T, Void, SimpleStateMachine<S, E, T>>(
        null, null, null,
        new BaseDefinition<S, E, T, Void>(states, events, eventTypes, null),
        Cons.<Transition<S, T>> empty(),
        Cons.<StateMachineListener<S, E, T, Void>> empty());
  }

  /**
   * Creates a new builder that builds state machines from an interface
   * definition.
   *
   * @since 1.0.0
   * @param iface
   *          The interface to read the state, event, event types and context
   *          classes form. This interface must implement
   *          {@link SimpleStateMachine}. The types are read from the
   *          {@link ParameterizedType}.
   * @return The new builder that builds state machines from an interface
   *         definition.
   */
  public static <S, E, T, M extends SimpleStateMachine<S, E, T>>
      StateMachineBuilder<S, E, T, Void, M>
      create(final Class<M> iface) {

    if (!iface.isInterface()) {
      throw new IllegalArgumentException(
          "The given class must resemble an interface.");
    }

    final Type[] ifaces = iface.getGenericInterfaces();
    for (final Type type : ifaces) {
      if (type instanceof GenericDeclaration) {
        final ParameterizedType genericType = (ParameterizedType) type;
        if (genericType.getRawType() == SimpleStateMachine.class) {
          final Type[] typeArgs = genericType.getActualTypeArguments();

          @SuppressWarnings("unchecked")
          final BaseDefinition<S, E, T, Void> def = new BaseDefinition<>(
              (Class<S>) typeArgs[0],
              (Class<E>) typeArgs[1],
              (Class<T>) typeArgs[2], null);
          return new StateMachineBuilder<S, E, T, Void, M>(
              iface, null, null, def,
              Cons.<Transition<S, T>> empty(),
              Cons.<StateMachineListener<S, E, T, Void>> empty());
        }
      }
    }

    throw new RuntimeException("-this should relly not have happend-");
  }

  /**
   * Creates a builder that builds state machines with a computational context.
   *
   * @since 1.0.0
   * @param states
   *          The class that all states inherit from.
   * @param events
   *          The class that all events inherit from.
   * @param context
   *          The class that implements the context.
   * @return The new builder that builds state machines with a computational
   *         context.
   */
  public static <S, E, T, C>
      StateMachineBuilder<S, E, E, C, StateMachineWithContext<S, E, E, C>>
      createWithContext(
          final Class<S> states,
          final Class<E> events,
          final Class<C> context) {
    return new StateMachineBuilder<S, E, E, C, StateMachineWithContext<S, E, E, C>>(
        null, null, null,
        new BaseDefinition<S, E, E, C>(states, events, events, context),
        Cons.<Transition<S, E>> empty(),
        Cons.<StateMachineListener<S, E, E, C>> empty());
  }

  /**
   * Creates a builder that builds state machines with a distinct event type and
   * a computational context.
   *
   * @since 1.0.0
   * @param states
   *          The class that all states inherit from.
   * @param events
   *          The class that all events inherit from.
   * @param eventTypes
   *          The class that all event types inherit from.
   * @param context
   *          The class that implements the context.
   * @return The new builder.
   */
  public static <S, E, T, C> StateMachineBuilder<S, E, T, C, StateMachineWithContext<S, E, T, C>>
      createWithContext(
          final Class<S> states,
          final Class<E> events,
          final Class<T> eventTypes,
          final Class<C> context) {
    return new StateMachineBuilder<S, E, T, C, StateMachineWithContext<S, E, T, C>>(
        null, null, null,
        new BaseDefinition<S, E, T, C>(states, events, eventTypes, context),
        Cons.<Transition<S, T>> empty(),
        Cons.<StateMachineListener<S, E, T, C>> empty());
  }

  /**
   * Creates a builder that builds state machines with a distinct event type and
   * a computational context from an interface definition.
   *
   * @since 1.0.0
   * @param iface
   *          The interface to read the state, event, event types and context
   *          classes form. This interface must implement
   *          {@link StateMachineWithContext}. The types are read from the
   *          {@link ParameterizedType}.
   * @return The new builder.
   */
  public static <S, E, T, C, M extends StateMachineWithContext<S, E, T, C>>
      StateMachineBuilder<S, E, T, C, M>
      createWithContext(final Class<M> iface) {

    final Type[] ifaces = iface.getGenericInterfaces();
    for (final Type type : ifaces) {
      if (type instanceof ParameterizedType) {
        final ParameterizedType genericType = (ParameterizedType) type;

        if (genericType.getRawType() == StateMachineWithContext.class) {
          final Type[] typeArgs = genericType.getActualTypeArguments();

          @SuppressWarnings("unchecked")
          final BaseDefinition<S, E, T, C> def = new BaseDefinition<>(
              (Class<S>) typeArgs[0],
              (Class<E>) typeArgs[1],
              (Class<T>) typeArgs[2],
              (Class<C>) typeArgs[3]);

          return new StateMachineBuilder<S, E, T, C, M>(
              iface, null, null, def,
              Cons.<Transition<S, T>> empty(),
              Cons.<StateMachineListener<S, E, T, C>> empty());
        }
      }
    }

    throw new RuntimeException("-this should relly not have happend-");
  }

  /**
   * Define a new transition.
   *
   * @since 1.0.0
   * @param from
   *          The state from which this transition originates.
   * @param on
   *          The event on which this transition is eligible.
   * @param to
   *          The state this transition leads to.
   * @return The builder with that transition added.
   */
  public StateMachineBuilder<S, E, T, C, M> withTransition(
      final S from, final T on, final S to) {
    return new StateMachineBuilder<S, E, T, C, M>(
        this.targetType,
        this.initialState,
        this.eventTypeClassifier,
        this.baseDefinition,
        this.transitions.cons(new Transition<S, T>(from, on, to)),
        this.listeners);
  }

  /**
   * Defines a bunch of new Transitions using {@link Transition#from(Object)}.
   *
   * @since 1.2.0
   * @param transitions
   * @return A builder with the defined Transitions added.
   */
  @SafeVarargs
  public final StateMachineBuilder<S, E, T, C, M> withTransitions(
      final TransitionBuilder2<S, T>... transitions) {
    Cons<Transition<S, T>> newTransitions = this.transitions;
    for (final TransitionBuilder2<S, T> builder : transitions) {
      for (final Transition<S, T> transition : builder.getTransitions()) {
        newTransitions = newTransitions.cons(transition);
      }
    }
    return new StateMachineBuilder<S, E, T, C, M>(
        this.targetType,
        this.initialState,
        this.eventTypeClassifier,
        this.baseDefinition,
        newTransitions,
        this.listeners);
  }

  /**
   * @since 1.2.0
   * @param from
   * @param to
   * @return
   */
  public StateMachineBuilder<S, E, T, C, M> withFallback(
      final S from, final S to) {
    return withTransition(from, null, to);
  }

  /**
   * @since 1.3.0
   * @param from
   * @param to
   * @return
   */
  public StateMachineBuilder<S, E, T, C, M> withCatchall(
      final S from, final S to) {
    return withTransition(from, null, to);
  }

  /**
   * Define a new transition guarded by an MVEL expression.
   *
   * You can use the variables <code>e</code>, <code>event</code>,
   * <code>c</code>, and <code>code</code> to refer the the current Event of
   * type <code>E</code> and the context of type <code>C</code>.
   *
   * @since 1.0.0
   * @param from
   *          The state from which this transition originates.
   * @param on
   *          The event on which this transition is eligible.
   * @param to
   *          The state this transition leads to.
   * @param guard
   *          The guard (an MVEL expression which has to evaluate to true) that
   *          has to be passed before this transition can be considered
   *          eligible.
   * @return The builder with that transition added.
   */
  public StateMachineBuilder<S, E, T, C, M> withTransition(
      final S from, final T on, final S to, final String guard) {
    return new StateMachineBuilder<S, E, T, C, M>(
        this.targetType,
        this.initialState,
        this.eventTypeClassifier,
        this.baseDefinition,
        this.transitions.cons(new Transition<S, T>(
            from, on, to, new ExpressionGuard<E, C>(guard))),
        this.listeners);
  }

  /**
   * Define a new transition guarded by a {@link Guard}.
   *
   * @since 1.0.0
   * @param from
   *          The state from which this transition originates.
   * @param on
   *          The event on which this transition is eligible.
   * @param to
   *          The state this transition leads to.
   * @param guard
   *          The guard that has to be passed before this transition can be
   *          considered eligible.
   * @return The builder with that transition added.
   */
  public StateMachineBuilder<S, E, T, C, M> withTransition(
      final S from, final T on, final S to, final Guard<E, C> guard) {
    return new StateMachineBuilder<S, E, T, C, M>(
        this.targetType,
        this.initialState,
        this.eventTypeClassifier,
        this.baseDefinition,
        this.transitions.cons(new Transition<S, T>(from, on, to, guard)),
        this.listeners);
  }

  /**
   * Define a new listener.
   *
   * @since 1.0.0
   * @param listener
   *          The listener.
   * @return The builder with that listener added.
   */
  public StateMachineBuilder<S, E, T, C, M> withListener(
      final StateMachineListener<S, E, T, C> listener) {
    return new StateMachineBuilder<S, E, T, C, M>(
        this.targetType,
        this.initialState,
        this.eventTypeClassifier,
        this.baseDefinition,
        this.transitions,
        this.listeners.cons(listener));
  }

  /**
   * Builds a new state machine of target type <i>M</i> from this builder.
   *
   * @since 1.0.0
   * @return The new state machine of target type <i>M</i>.
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public M build() {

    final EventTypeClassifier<E, T> classifier = getClassifier();

    final S initialState = getInitialState();

    final List<TransitionListener<S, E, T, C>> listeners = gatherTransitionListeners(this.listeners);

    final StateMachine<S, E, T> fsm;
    if (this.baseDefinition.getContext() == null
        || this.baseDefinition.getContext() == Void.class) {
      fsm = new StatelessStateMachine<S, E, T>(
          initialState,
          this.transitions,
          this.baseDefinition.getStates(),
          this.baseDefinition.getEvents(),
          this.baseDefinition.getEventTypes(),
          classifier,
          (List) listeners);
    } else {
      fsm = new StatelessStateMachineWithContext<S, E, T, C>(
          initialState,
          this.transitions,
          this.baseDefinition.getStates(),
          this.baseDefinition.getEvents(),
          this.baseDefinition.getEventTypes(),
          this.baseDefinition.getContext(),
          classifier,
          listeners);
    }

    if (this.targetType != null) {
      return createProxy(fsm);
    }

    return (M) fsm;
  }

  private List<TransitionListener<S, E, T, C>> gatherTransitionListeners(
      final Iterable<StateMachineListener<S, E, T, C>> listeners) {

    final List<TransitionListener<S, E, T, C>> transitionListeners = new ArrayList<>();

    for (final StateMachineListener<S, E, T, C> listener : listeners) {
      if (listener instanceof TransitionListener) {
        transitionListeners.add((TransitionListener<S, E, T, C>) listener);
      }
    }

    return transitionListeners;
  }

  private S getInitialState() {

    final Class<S> states = this.baseDefinition.getStates();

    if (this.initialState != null) {
      return this.initialState;
    } else if (states.isEnum()) {
      final S[] enums = states.getEnumConstants();
      if (enums == null || enums.length <= 0) {
        throw new StateMachineBuilderException(
            ErrorType.NO_INITIAL_STATE_NO_STATES_AT_ALL);
      }
      return enums[0];
    } else {
      throw new StateMachineBuilderException(ErrorType.NO_INITIAL_STATE);
    }
  }

  @SuppressWarnings("unchecked")
  private EventTypeClassifier<E, T> getClassifier() {

    final Class<E> events = this.baseDefinition.getEvents();
    final Class<T> eventTypes = this.baseDefinition.getEventTypes();

    if (this.eventTypeClassifier != null) {
      return this.eventTypeClassifier;

    } else if (isEventWithType(events, eventTypes)) {
      return new EventTypeClassifier<E, T>() {
        @Override
        public T classify(final E event) {
          return ((EventWithType<T>) event).getType();
        }
      };

    } else if (events == this.baseDefinition.getEventTypes()) {
      return new EventTypeClassifier<E, T>() {

        @Override
        public T classify(final E event) {
          return (T) event;
        }
      };

    } else {
      throw new StateMachineBuilderException(
          ErrorType.EVENT_AND_EVENT_TYPES_DIFFER);
    }
  }

  private M createProxy(final Object fsm) {
    @SuppressWarnings("unchecked")
    final M proxy = (M) Proxy.newProxyInstance(
        this.targetType.getClassLoader(),
        new Class<?>[] { this.targetType },
        new InvocationHandler() {

          @Override
          public Object invoke(
              final Object proxy, final Method method, final Object[] args)
              throws Throwable {

            return method.invoke(fsm, args);
          }
        });
    return proxy;
  }

  private static <E, T> boolean isEventWithType(
      final Class<E> events,
      final Class<T> eventTypes) {

    if (!EventWithType.class.isAssignableFrom(events)) {
      return false;
    }

    for (final Type type : events.getGenericInterfaces()) {
      if (type instanceof ParameterizedType
          && ((ParameterizedType) type).getRawType() == EventWithType.class) {
        final Type[] typeArguments =
            ((ParameterizedType) type).getActualTypeArguments();
        return typeArguments[0] == eventTypes;
      }
    }

    return false;
  }
}
