package de.skat3.gamelogic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

public class Player implements Serializable {

  boolean isSolo;
  Hand hand;
  ArrayList<Card> wonTricks;
  Position position;
  private UUID uuid;

  public Player() {
    this.uuid = UUID.randomUUID();
  }

  public UUID getUuid() {
    return this.uuid;
  }

  void setHand(Hand hand) {
    this.hand = hand;
  }
  public Hand getHand() {
    return this.hand;
  }

  /**
   * Sets position. (F, M, R)
   * 
   * @param position 0=F, 1=M, 2=R
   */
  public void setPosition(int position) {
    this.position = Position.values()[position];

  }


  public void setSolo() {
    this.isSolo = true;

  }

  /**
   * 
   * @param p
   * @return
   */
  public boolean equals(Player p) {
    if (this.uuid == p.uuid) {
      return true;
    } else {
      return false;
    }
  }
}
