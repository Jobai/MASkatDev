package de.skat3.main;

import java.net.Inet4Address;
import de.skat3.gamelogic.Player;

public class Lobby {


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


  }

  public Player[] getPlayers() {
    return this.players;
  }

  public void addPlayer(Player player) {
    for (int i = 0; i < this.numberOfPlayers; i++) {
      if (this.players[i] == null) {
        this.players[i] = player;
        break;
      }
    }
  }

  public void removePlayer(Player player) {
    for (int i = 0; i < this.numberOfPlayers; i++) {
      if (this.players[i].getUuid() == player.getUuid()) {
        this.players[i] = null;
      }
    }
  }
  
  public Inet4Address getIp(){
    return this.ip;
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
}
