package de.skat3.gamelogic;

import java.io.Serializable;

/**
 * All existing suits in a skat game.
 * @author kai29
 *
 */
public enum Suit implements Serializable {

  DIAMONDS, HEARTS, SPADES, CLUBS;

  public static int length = Suit.values().length;
}
