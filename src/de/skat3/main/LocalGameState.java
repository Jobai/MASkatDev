package de.skat3.main;

import java.util.ArrayList;
import de.skat3.gamelogic.Card;
import de.skat3.gamelogic.Player;

public class LocalGameState {

  boolean gameActive;
  int gameId;
  Player[] players;
  Card[] trick;
  Card[] skat;
  ArrayList<String> chatMessages;
  
  public void setPlayer(Player player) {
    this.players[0] = player;
  }
//  void updateLocalGameState() {
//    
//  }
}
