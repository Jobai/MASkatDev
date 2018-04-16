package de.skat3.gamelogic;

import java.io.Serializable;

public enum Suit implements Serializable {

  DIAMONDS, HEARTS, SPADES, CLUBS;

  public static int length = Suit.values().length;
}
