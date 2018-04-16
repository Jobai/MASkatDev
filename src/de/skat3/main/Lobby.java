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

  /**
   * 
   * @param ip
   * @param name
   * @param password
   * @param numberOfPlayers
   * @param players
   * @param timer
   * @param mode
   */

  public Lobby(Inet4Address ip, int serverMode, String name, String password, int numberOfPlayers, int timer, int scoringMode) {
    this.ip = ip;
    this.serverMode = serverMode;
    this.currentPlayers = 1;
    this.name = name;
    this.password = password;
    this.numberOfPlayers = numberOfPlayers;
    this.players = players;
    this.scoringMode = scoringMode;


  }
}
