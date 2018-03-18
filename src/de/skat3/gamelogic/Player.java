package de.skat3.gamelogic;

import java.util.ArrayList;
import de.skat3.main.Position;

public class Player {

  boolean isSolo;
  Hand hand;
  ArrayList<Card> wonTricks;
  Position position;

  public Player() {}


  void setHand(Hand hand) {
    this.hand = hand;
  }

  /**
   * Sets position. (F, M, R)
   * 
   * @param position 0=F, 1=M, 2=R
   */
  public void setPosition(int position) {
    this.position = Position.values()[position];

  }
}
