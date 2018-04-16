package de.skat3.gamelogic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

public class Player implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -4721822427911236663L;
  boolean isSolo;
  Hand hand;
  ArrayList<Card> wonTricks;
  Position position;
  @Deprecated
  private UUID uuid;
  int points;
  int wonGames;
  int lostGames;

  public Player() {
    
    this.uuid = UUID.randomUUID();
    this.points = 0;
    this.wonGames = 0;
    this.lostGames = 0;
  }

  public UUID getUuid() {
    return this.uuid;
  }

  public void setHand(Hand hand) {
    this.hand = hand;
  }

  public Hand getHand() {
    return this.hand;
  }

  void changePoints(int change) {
    this.points += change;
  }

  void wonAGame() {
    this.wonGames++;
  }

  void lostAGame() {
    this.lostGames++;
  }

  /**
   * Sets position. (F, M, R)
   * 
   * @param position 0=F, 1=M, 2=R
   */
  public void setPosition(int position) {
    this.position = Position.values()[position];

  }

  public Position getPosition() {
    return this.position;
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
