package de.skat3.main;

import java.util.ArrayList;
import de.skat3.gamelogic.Card;
import de.skat3.gamelogic.Player;

public class LocalGameState {

  boolean gameActive;
  int gameId;
  Player localClient;
  Card[] trick;
  Card[] skat;
  ArrayList<String> chatMessages;
 
  
  public void setPlayer(Player player) {
    this.localClient = player;
    System.out.println(player.getHand());
  }
//  void updateLocalGameState() {
//    
//  }
}
