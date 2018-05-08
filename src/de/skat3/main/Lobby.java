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
import java.util.logging.Logger;
import de.skat3.gamelogic.Player;

public class Lobby implements Serializable {

  private static final long serialVersionUID = 7279502331048915007L;

  private transient Logger logger = Logger.getLogger("de.skat3.gamelogic");

  String name;


  String password;

  public String getPassword() {
    return password;
  }

  int numberOfPlayers;

  int currentPlayers;
  Player[] players;
  int timer;
  int scoringMode;
  Inet4Address ip;
  int serverMode;
  boolean singlePlayerGame;
  boolean kontraRekontraEnabled;
  private UUID uuid;

  public int lobbyPlayer = 0;

  boolean hasPassword;

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
    this.currentPlayers = 0;
    this.name = name;
    this.timer = timer;

    if (password != null && !password.isEmpty()) {
      this.password = password;
      this.hasPassword = true;
    } else {
      this.hasPassword = false;
    }
    this.scoringMode = scoringMode;
    this.kontraRekontraEnabled = kontraRekontraEnabled;
    uuid = UUID.randomUUID();
    this.singlePlayerGame = false;


  }

  /**
   * 
   * @param ip
   * @param serverMode
   * @param timer
   * @param scoringMode
   * @param kontraRekontraEnabled
   */
  public Lobby(Inet4Address ip, int serverMode, int scoringMode, boolean kontraRekontraEnabled) {
    this.numberOfPlayers = 3;
    this.players = new Player[this.numberOfPlayers];
    this.ip = ip;
    this.serverMode = serverMode;
    this.hasPassword = false;
    this.currentPlayers = 0;
    this.timer = 0;
    this.scoringMode = scoringMode;
    this.kontraRekontraEnabled = kontraRekontraEnabled;
    uuid = UUID.randomUUID();
    this.singlePlayerGame = true;

  }



  /**
   * @author Jonas Bauer
   * @return the hasPassword
   */
  public boolean isHasPassword() {
    return hasPassword;
  }

  public Lobby() {

  }

  /**
   * 
   * @param ip
   * @param serverMode
   */
  public Lobby(Inet4Address ip, int serverMode) {
    this.numberOfPlayers = 3;
    this.players = new Player[this.numberOfPlayers];
    this.ip = ip;
    this.serverMode = serverMode;
    this.hasPassword = false;
    this.currentPlayers = 0;
    this.timer = 0;
    uuid = UUID.randomUUID();
    this.singlePlayerGame = true;
  }

  public Player[] getPlayers() {
    return this.players;
  }

  public void addPlayer(Player player) {
    if (this.currentPlayers < this.numberOfPlayers) {
      for (int i = 0; i < this.numberOfPlayers; i++) {
        if (this.players[i] == null) {
          this.players[i] = player; // XXX
          this.currentPlayers++;
          if (SkatMain.mainController.numberOfPlayerProperty != null) {
            SkatMain.mainController.numberOfPlayerProperty.set(this.currentPlayers);
          }
          System.out.println("Player added " + player + " (" + this.currentPlayers + "/"
              + this.numberOfPlayers + ")");
          break;
        }
      }
      if (SkatMain.lgs == null) {
        SkatMain.mainController.setLgs();
      }
      if (this.currentPlayers > 1) {
        SkatMain.lgs.addPlayer();
      }

    } else {
      System.err.println("Lobby full");
    }
  }

  public void removePlayer(Player player) {
    if(player==null) {
      System.out.println("Fehler");
      return;
    }
    for (int i = 0; i < this.numberOfPlayers; i++) {
      if (this.players[i] != null) {
        if (this.players[i].equals(player)) {
          SkatMain.lgs.removePlayer(player);
          if (player.isBot()) {
            SkatMain.aiController.removeBot(player);
          }
          this.players[i] = null;
          this.currentPlayers--;
          if (SkatMain.mainController.maxNumberOfPlayerProperty != null) {
            SkatMain.mainController.numberOfPlayerProperty.set(this.currentPlayers);
          }
          break;
        }
      }
      if (i == this.numberOfPlayers - 1) {
        try {
          logger.severe("Player not found: " + player);
        } catch (Exception e) {
          System.out.println("Error in remove Player, Logger or Player null");
        }
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
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    return null;
  }

  public String getName() {
    return name;
  }

  public String toString() {
    return "----------\nName : " + this.name + "\nPassword = " + this.password + "\nTimer = "
        + this.timer + "\nSingleplayer = " + this.singlePlayerGame + "\nServer mode = "
        + this.serverMode + "\nScoring mode = " + this.scoringMode + "\nPlayers = "
        + this.currentPlayers + "/" + this.numberOfPlayers + "\n----------";
  }


  public void incrementconnectedPlayerNumberbyHand() {
    currentPlayers++;
  }


  public int getTimer() {
    return this.timer;
  }

  public boolean getKontraRekontraEnabled() {
    return this.kontraRekontraEnabled;
  }
}
