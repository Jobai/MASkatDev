package de.skat3.gamelogic;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;
import de.skat3.ai.Ai;
import de.skat3.ai.IntelligentAi;
import de.skat3.ai.RandomAI;
import de.skat3.io.profile.ImageConverter;
import de.skat3.io.profile.Profile;
import javafx.scene.image.Image;

public class Player implements Serializable, Comparable<Player> {

  /**
   * 
   */
  boolean isSolo;
  String name;
  String image;
  Hand hand;
  public ArrayList<Card> wonTricks;
  Position position;
  private UUID uuid;
  int points;
  boolean isBot;
  boolean isHardBot;
  int wonGames;
  int lostGames;
  int seegerPoints;
  public Ai ai;

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
    this.isSolo = player.isSolo;
    this.name = player.name;
    this.wonTricks = player.wonTricks;
    this.uuid = player.uuid;
    this.image = player.image;
    this.points = player.points;
    this.setHand(player.hand);
    this.isBot = player.isBot;
    this.ai = player.ai;
    this.wonGames = player.wonGames;
    this.lostGames = player.lostGames;
    this.seegerPoints = player.seegerPoints;
  }

  /**
   * 
   * @param difficulty
   */
  public Player(boolean hardBot) {
    if (hardBot) {
      this.name = "Hard Ai";
    } else {
      this.name = "Easy Ai";
    }
    this.isHardBot = hardBot;
    this.wonTricks = new ArrayList<Card>();
    this.points = 0;
    this.uuid = UUID.randomUUID();
    this.hand = new Hand();
    this.isBot = true;
    this.wonGames = 0;
    this.lostGames = 0;
    this.seegerPoints = 0;
    if (this.isHardBot) {
      this.ai = new IntelligentAi();
    } else {
      this.ai = new RandomAI();
    }
  }

  public UUID getUuid() {
    return this.uuid;
  }

  public void setHand(Hand hand) {
    this.hand = new Hand(hand.cards);
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
        this.lostGames++;
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

  public void setSolo(boolean solo) {
    this.isSolo = solo;

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

  // @Override
  // public String toString() {
  // return this.name + " (" + this.uuid + ")";
  // }



  public Player copyPlayer() {
    return new Player(this);

  }



  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return this.name + " (" + this.uuid + ")\n" + this.hand;
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

  public int getSeegerPoints() {
    return this.seegerPoints;
  }

  void shortenPlayer() {
    this.image = null;
  }

  public void updatePlayer(Player player) {
    this.isSolo = player.isSolo;
    this.wonTricks = player.wonTricks;
    this.points = player.points;
    this.setHand(player.hand);
    this.isBot = player.isBot;
    this.wonGames = player.wonGames;
    this.lostGames = player.lostGames;
    this.seegerPoints = player.seegerPoints;
  }


  /**
   * Converts the lobby object to a byte array. <br>
   * Used for datagrams used during lobby discovery.
   * 
   * @author Jonas Bauer
   * @param lobby Lobby object for the conversion
   * @return byte array (converted lobby object)
   */
  public byte[] convertToByteArray(Player lobby) {
    try (ByteArrayOutputStream os = new ByteArrayOutputStream();
        ObjectOutput oOut = new ObjectOutputStream(os)) {
      oOut.writeObject(lobby);
      return os.toByteArray();
    } catch (IOException e) {
      e.printStackTrace();
      System.err.println("ERROR during conversion to Bytes! - See Lobby Class");
    }
    return null;
  }

  /**
   * Converts the byte array back to a lobby object. <br>
   * Used for datagrams used during lobby discovery.
   * 
   * @author Jonas Bauer
   * @param byteArray for conversion
   * @return lobby object (converted back from bytes)
   */

  public Player convertFromByteArray(byte[] byteArray) {
    try (ByteArrayInputStream bais = new ByteArrayInputStream(byteArray);
        ObjectInput oIn = new ObjectInputStream(bais)) {
      System.out.println(byteArray.length);
      return (Player) oIn.readObject();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public int compareTo(Player p) {
    if (this.seegerPoints > p.seegerPoints) {
      return 1;
    }
    if (this.seegerPoints < p.seegerPoints) {
      return -1;
    }
    if (this.seegerPoints == p.seegerPoints) {
      if (this.points > p.points) {
        return 1;
      }
      if (this.points < p.points) {
        return -1;
      }
    }
    return 0;
  }

}


