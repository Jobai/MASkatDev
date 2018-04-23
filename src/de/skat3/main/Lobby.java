package de.skat3.main;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Inet4Address;
import java.util.UUID;
import de.skat3.gamelogic.Player;

public class Lobby implements Serializable {


  /**
   * 
   */
  private static final long serialVersionUID = 7279502331048915007L;


  String name;


  String password;
  int numberOfPlayers;
  int currentPlayers;
  Player[] players;
  int timer;
  int scoringMode;
  Inet4Address ip;
  int serverMode;
  boolean kontraRekontraEnabled;
  private UUID uuid;

  /**
   * 
   * @param ip
   * @param serverMode
   * @param name
   * @param password
   * @param numberOfPlayers
   * @param timer
   * @param scoringMode
   * @param kontraRekontraEnabled
   */

  public Lobby(Inet4Address ip, int serverMode, String name, String password, int numberOfPlayers,
      int timer, int scoringMode, boolean kontraRekontraEnabled) {
    this.numberOfPlayers = numberOfPlayers;
    this.players = new Player[this.numberOfPlayers];
    this.ip = ip;
    this.serverMode = serverMode;
    this.currentPlayers = 1;
    this.name = name;
    this.password = password;
    this.scoringMode = scoringMode;
    this.kontraRekontraEnabled = kontraRekontraEnabled;
    uuid = UUID.randomUUID();


  }

  public Lobby() {
    // TODO Auto-generated constructor stub
  }



  public Player[] getPlayers() {
    return this.players;
  }

  public void addPlayer(Player player) {
    for (int i = 0; i < this.numberOfPlayers; i++) {
      if (this.players[i] == null) {
        this.players[i] = player;
        System.out.println("Player added" + player);
        break;
      }
      if (i == this.numberOfPlayers - 1) {
        System.err.println("Lobby full, " + player + " cant join");
      }
    }
  }

  public void removePlayer(Player player) {
    for (int i = 0; i < this.numberOfPlayers; i++) {
      if (this.players[i].equals(player)) {
        this.players[i] = null;
        break;
      }
      if (i == this.numberOfPlayers - 1) {
        System.err.println("Player not found: " + player);
      }
    }
  }

  public Inet4Address getIp() {
    return this.ip;
  }


  public int getMaximumNumberOfPlayers() {
    return this.numberOfPlayers;
  }

  public int getCurrentNumberOfPlayers() {
    return this.currentPlayers;
  }

  /**
   * @author Jonas Bauer
   */
  @Override
  public boolean equals(Object obj) {
    Lobby lo = (Lobby) obj;
    // System.out.println(this.uuid);
    // System.out.println(lo.uuid);
    if (this.uuid.equals(lo.uuid)) {
      return true;
    } else {
      return false;
    }

  }

  /**
   * Converts the lobby object to a byte array. <br>
   * Used for datagrams used during lobby discovery.
   * 
   * @author Jonas Bauer
   * @param lobby Lobby object for the conversion
   * @return byte array (converted lobby object)
   */
  public byte[] convertToByteArray(Lobby lobby) {
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
  public Lobby convertFromByteArray(byte[] byteArray) {
    try (ByteArrayInputStream bais = new ByteArrayInputStream(byteArray);
        ObjectInput oIn = new ObjectInputStream(bais)) {
      return (Lobby) oIn.readObject();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return null;
  }

  public String getName() {
    return name;
  }

}
