package de.skat3.gamelogic;

import java.io.Serializable;

/**
 * All existing suits in a skat game.
 * @author Kai Baumann
 *
 */
public enum Suit implements Serializable {

  DIAMONDS, HEARTS, SPADES, CLUBS;

  public static int length = Suit.values().length;
}
