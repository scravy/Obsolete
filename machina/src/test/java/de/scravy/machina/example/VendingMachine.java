package de.scravy.machina.example;

import de.scravy.machina.StateMachineWithContext;
import de.scravy.machina.example.VendingMachine.CoinSafe;
import de.scravy.machina.example.VendingMachine.Event;
import de.scravy.machina.example.VendingMachine.EventType;
import de.scravy.machina.example.VendingMachine.State;

public interface VendingMachine extends
    StateMachineWithContext<State, Event, EventType, CoinSafe> {

  enum State {
    LOCKED,
    UNLOCKED
  }

  enum Event {

  }

  enum EventType {
    COIN,
    PUSH
  }

  class CoinSafe {

  }

}
