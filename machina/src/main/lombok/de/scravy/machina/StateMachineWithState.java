package de.scravy.machina;

class StateMachineWithState<S, E, T>
    extends
    AbstractDelegatingStateMachine<S, E, T, SimpleStateMachine<S, E, T>>
    implements SimpleStatefulStateMachine<S, E, T>
{

  private S state;

  StateMachineWithState(
      final SimpleStateMachine<S, E, T> fsm, final S initialState) {
    super(fsm);
    this.state = initialState;
  }

  StateMachineWithState(
      final SimpleStateMachine<S, E, T> fsm) {
    this(fsm, fsm.getInitialState());
  }

  @Override
  public S getState() {
    return this.state;
  }

  @Override
  public void fire(final E event) {
    this.state = this.fsm.run(this.state, event);
  }
}
