package de.skat3.gamelogic;

import java.io.Serializable;

public enum Contract implements Serializable {

  DIAMONDS, HEARTS, SPADES, CLUBS, GRAND, NULL;


  @Override //JB
  public String toString() {
    switch (this) {
      case DIAMONDS:
        return "DIAMONDS ♦";
      case HEARTS:
        return "HEARTS ♥";
      case SPADES:
        return "SPADES ♠";
      case CLUBS:
        return "CLUBS ♣";
      case GRAND:
        return "GRAND";
      case NULL:
        return "NULL";
      default:
        throw new AssertionError();
    }
  }
}
