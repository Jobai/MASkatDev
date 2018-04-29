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
  boolean isBot;
  boolean isHardBot;
  int wonGames;
  int lostGames;
  int seegerPoints;

  public Player(Profile profile) {

    this.name = profile.getName();
    this.wonTricks = new ArrayList<Card>();
    this.uuid = profile.getUuid();
    this.image = profile.getEncodedImage();
    this.points = 0;
    this.hand = new Hand();
    this.isBot = false;
    this.wonGames = 0;
    this.lostGames = 0;
    this.seegerPoints = 0;

  }

  public Player() {

    this.wonTricks = new ArrayList<Card>();
    this.points = 0;
    this.hand = new Hand();
    this.isBot = false;
    this.wonGames = 0;
    this.lostGames = 0;
    this.seegerPoints = 0;

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
    this.hand = player.hand;
    this.isBot = player.isBot;
    this.wonGames = player.wonGames;
    this.lostGames = player.lostGames;
    this.seegerPoints = player.seegerPoints;
  }

  /**
   * 
   * @param difficulty
   */
  public Player(boolean hardBot) {
    this.name = "Ai";
    this.isHardBot = hardBot;
    this.wonTricks = new ArrayList<Card>();
    // this.image = festes Bild? TODO
    this.points = 0;
    this.uuid = UUID.randomUUID();
    this.hand = new Hand();
    this.isBot = true;
    this.wonGames = 0;
    this.lostGames = 0;
    this.seegerPoints = 0;
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

  void changePoints(int change, boolean seeger) {
    this.points += change;
    if (seeger) {
      if (change > 0) {
        this.wonGames++;
      } else {
        this.lostGames--;
      }
      this.seegerPoints = this.points + (this.wonGames - this.lostGames) * 50;
    }
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
    return this.name + " (" + this.uuid + ")";
  }

  public Player copyPlayer() {
    return new Player(this);

  }



  public Image convertToImage() {
    ImageConverter ic = new ImageConverter();
    Image im = ic.encodedStringToImage(image);
    return im;
  }

  public boolean isBot() {
    return this.isBot;
  }

  /**
   * 
   * @return
   */
  public boolean isHardBot() {
    if (this.isBot) {
      return this.isHardBot;
    } else {
      System.err.println(this.name + " is no Bot!");
    }
    return false;
  }

  public boolean isSolo() {
    return this.isSolo;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @return the wonTricks
   */
  public ArrayList<Card> getWonTricks() {
    return wonTricks;
  }

  /**
   * @return the points
   */
  public int getPoints() {
    return points;
  }

}



