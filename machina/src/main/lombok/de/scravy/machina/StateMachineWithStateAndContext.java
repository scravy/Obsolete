package de.scravy.machina;

class StateMachineWithStateAndContext<S, E, T, C>
    extends
    AbstractDelegatingStateMachine<S, E, T, StateMachineWithContext<S, E, T, C>>
    implements StatefulStateMachineWithContext<S, E, T, C>
{

  private S currentState;
  private C currentContext;

  StateMachineWithStateAndContext(
      final StateMachineWithContext<S, E, T, C> fsm, final C context,
      final S initialState) {
    super(fsm);
    this.currentContext = context;
    this.currentState = initialState;
  }

  StateMachineWithStateAndContext(
      final StateMachineWithContext<S, E, T, C> fsm, final C context) {
    this(fsm, context, fsm.getInitialState());
  }

  @Override
  public void fire(final E event) {
    final RunResult<S, C> result =
        this.fsm.run(this.currentState, event, this.currentContext);
    this.currentState = result.getState();
    this.currentContext = result.getContext();
  }

  @Override
  public S getState() {
    return this.currentState;
  }

  @Override
  public C getContext() {
    return this.currentContext;
  }
}
