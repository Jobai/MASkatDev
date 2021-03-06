package de.skat3.gamelogic;

import de.skat3.ai.Ai;
import de.skat3.ai.AiNames;
import de.skat3.ai.IntelligentAi;
import de.skat3.ai.RandomAi;
import de.skat3.io.profile.ImageConverter;
import de.skat3.io.profile.Profile;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;
import javafx.scene.image.Image;

/**
 * Represents a player in a skat match.
 * 
 * @author Kai Baumann
 *
 */
@SuppressWarnings("serial")
public class Player implements Serializable, Comparable<Player> {

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

  /**
   * Creates a new player.
   * 
   * @param profile the locally saved profile of this player.
   */
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

  /**
   * Creates an empty player.
   */
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
   * Clones a player.
   */
  public Player(Player player) {
    this.isSolo = player.isSolo;
    this.name = player.name;
    this.wonTricks = new ArrayList<Card>();
    for (Card card : player.wonTricks) {
      this.wonTricks.add(card);
    }
    this.position = player.position;
    this.uuid = player.uuid;
    this.image = player.image;
    this.points = player.points;
    this.setHand(player.hand);
    this.isBot = player.isBot;
    this.ai = player.ai;
    this.isHardBot = player.isHardBot;
    this.wonGames = player.wonGames;
    this.lostGames = player.lostGames;
    this.seegerPoints = player.seegerPoints;
  }

  /**
   * Creates a bot player.
   * 
   * @param hardBot true if the ai is hard.
   */
  public Player(boolean hardBot) {
    this.uuid = UUID.randomUUID();
    this.name = AiNames.getRandomName(hardBot);
    this.isHardBot = hardBot;
    this.wonTricks = new ArrayList<Card>();
    this.points = 0;;
    this.hand = new Hand();
    this.isBot = true;
    this.wonGames = 0;
    this.lostGames = 0;
    this.seegerPoints = 0;
    if (this.isHardBot) {
      this.ai = new IntelligentAi();
    } else {
      this.ai = new RandomAi();
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

  /**
   * Updates the points of this player.
   * 
   * @param seeger true if the gamemode is seeger.
   */
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
  /**
   * Returns true if the uuid is identical.
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


  /**
   * Returns a copy of the player.
   * 
   * @return
   */
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

  /**
   * Converts the String to an image.
   */
  public Image convertToImage() {
    ImageConverter ic = new ImageConverter();
    Image im = ic.encodedStringToImage(image);
    return im;
  }

  public boolean isBot() {
    return this.isBot;
  }

  /**
   * Returns true if the player is a hard bot.
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

  public String getName() {
    return name;
  }

  public ArrayList<Card> getWonTricks() {
    return wonTricks;
  }

  public int getPoints() {
    return points;
  }

  public int getSeegerPoints() {
    return this.seegerPoints;
  }

  void shortenPlayer() {
    this.image = null;
  }

  /**
   * Updates all attributes that can vary during a match.
   */
  public void updatePlayer(Player player) {
    this.position = player.position;
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
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * Used to sort an array of players. It will be sorted by (seeger)points in a descending order.
   */
  @Override
  public int compareTo(Player p) {
    if (this.seegerPoints > p.seegerPoints) {
      return -1;
    }
    if (this.seegerPoints < p.seegerPoints) {
      return 1;
    }
    if (this.seegerPoints == p.seegerPoints) {
      if (this.points > p.points) {
        return -1;
      }
      if (this.points < p.points) {
        return 1;
      }
    }
    return 0;
  }


  public void clearWonTricks() {
    this.wonTricks.clear();
  }

}


