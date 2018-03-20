package de.skat3.main;

import de.skat3.gamelogic.Card;
import de.skat3.gamelogic.Contract;
import de.skat3.gamelogic.Player;
import java.util.ArrayList;

public class LocalGameState {

  boolean gameActive;
  int gameId;
  Contract contract;
  Player localClient;
  Card[] trick;
  int trickCount;
  Card[] skat;
  ArrayList<String> chatMessages;

  /**
   * 
   */
  public LocalGameState() {
    trick = new Card[3];
    skat = new Card[2];
    trickCount = 0;
  }

  public void setPlayer(Player player) {
    this.localClient = player;
  }


  public void addToTrick(Card c) {
    this.trick[trickCount] = c;
    this.trickCount = (this.trickCount + 1) % 3;
  }


}
