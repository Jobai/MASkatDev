package de.skat3.gamelogic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;
import de.skat3.io.profile.ImageConverter;
import de.skat3.io.profile.Profile;
import javafx.scene.image.Image;

public class Player implements Serializable {

  /**
   * 
   */
  boolean isSolo;
  String name;
  String image;
  Hand hand;
  ArrayList<Card> wonTricks;
  Position position;
  private UUID uuid;
  int points;
  int wonGames;
  int lostGames;

  public Player(Profile profile) {

    this.name = profile.getName();
    this.wonTricks = new ArrayList<Card>();
    this.uuid = profile.getUuid();
    this.image = profile.getEncodedImage();
    this.points = 0;
    this.wonGames = 0;
    this.lostGames = 0;
    this.hand = new Hand();

  }

  /**
   * 
   * @param player
   */
  public Player(Player player) {
    this.name = player.name;
    this.wonTricks = player.wonTricks;
    this.uuid = player.uuid;
    this.image = player.image;
    this.points = player.points;
    this.wonGames = player.wonGames;
    this.lostGames = player.lostGames;
    this.hand = player.hand;
  }

  /**
   * 
   * @param difficulty
   */
  public Player(int difficulty) {
    this.name = "Ai";
    this.wonTricks = new ArrayList<Card>();
    // this.image = festes Bild? TODO
    // this.difficulty ??? TODO
    this.points = 0;
    this.wonGames = 0;
    this.lostGames = 0;
    this.hand = new Hand();
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
    if (this.uuid.equals(p.uuid)) {
      return true;
    } else {
      return false;
    }
  }

  @Override
  public String toString() {
    return this.uuid + ": " + this.name;
  }

  public Player copyPlayer() {
    return new Player(this);

  }



  public Image convertToImage() {
    ImageConverter ic = new ImageConverter();
    Image im = ic.encodedStringToImage(image);
    return im;
  }
}


