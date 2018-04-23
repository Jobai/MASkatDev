package de.skat3.gamelogic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;
import de.skat3.io.profile.Profile;
import javafx.scene.image.Image;

public class Player implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -4721822427911236663L;
  boolean isSolo;
  String name;
  Image image;
  Hand hand;
  ArrayList<Card> wonTricks;
  Position position;
  private UUID uuid;
  int points;
  int wonGames;
  int lostGames;

  public Player(Profile profile) {

    //this.name = profile.getName();
    this.wonTricks = new ArrayList<Card>();
    this.uuid = UUID.randomUUID(); //FIXME
    //this.image = profile.getImage();
    this.points = 0;
    this.wonGames = 0;
    this.lostGames = 0;
    Card[] temp = new Card[10];
    for (int i = 0; i < 10; i++) {
      temp[i] = new Card();
    }
    this.hand = new Hand(temp);

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
