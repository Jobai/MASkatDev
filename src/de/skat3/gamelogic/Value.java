package de.skat3.gamelogic;

import java.io.Serializable;

/**
 * All values that exist in skat.
 * 
 * @author Kai Baumann
 *
 */
public enum Value implements Serializable {

  SEVEN, EIGHT, NINE, QUEEN, KING, TEN, ACE, JACK;

  public static int length = Value.values().length;
}
