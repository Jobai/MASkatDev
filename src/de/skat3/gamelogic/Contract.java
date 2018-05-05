package de.skat3.gamelogic;

import java.io.Serializable;

/**
 * All possible contracts in skat.
 * @author kai29
 *
 */
public enum Contract implements Serializable {

  DIAMONDS, HEARTS, SPADES, CLUBS, GRAND, NULL;



  /**
   * Converts a String to the corresponding enum.
   * @author Jonas Bauer
   * @param s String to be converted
   * @return corresponding Contract enum.
   */
  public static Contract getEnum(String s) {
    switch (s) {
      case "DIAMONDS":
      case "DIAMONDS ♦":
        return Contract.DIAMONDS;
      case "HEARTS":
      case "HEARTS ♥":
        return Contract.HEARTS;
      case "SPADES":
      case "SPADES ♠":
        return Contract.SPADES;
      case "CLUBS":
      case "CLUBS ♣":
        return Contract.CLUBS;
      case "GRAND":
        return Contract.GRAND;
      case "NULL":
        return Contract.NULL;
      default:
        throw new AssertionError();
    }

  }

  @Override // JB
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

  public static int length = Contract.values().length;
}
