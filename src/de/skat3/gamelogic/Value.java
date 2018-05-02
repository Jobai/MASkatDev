package de.skat3.gamelogic;

import java.io.Serializable;

public enum Value implements Serializable {

  SEVEN, EIGHT, NINE, QUEEN, KING, TEN, ACE, JACK;
  
  public static int length = Value.values().length;
}
