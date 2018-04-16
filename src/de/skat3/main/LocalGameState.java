package de.skat3.main;

import de.skat3.gamelogic.AdditionalMultipliers;
import de.skat3.gamelogic.Card;
import de.skat3.gamelogic.Contract;
import de.skat3.gamelogic.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.ArrayList;
import java.util.Arrays;

public class LocalGameState {

  boolean gameActive;
  int gameId;
  int timerInSeconds;
  Contract contract;
  AdditionalMultipliers additionalMultipliers;
  int trickcount;
  Player localClient;
  Player enemyOne;
  Player enemyTwo;
  Player dealer;
  Card[] trick;
  Card[] skat;
  ArrayList<String> chatMessages;

  /**
   * The current state of a game.
   * 
   */
  public LocalGameState(int numberOfPlayers, Player localClient, int timerInSeconds) {
    trickcount = 0;
    trick = new Card[3];
    skat = new Card[2];
    
  }

  public void setPlayer(Player player) {
    this.localClient = player;
  }

  /**
   * Adds a played card to the trick.
   * 
   * @param card the played card.
   */

  public void addToTrick(Card card) {
    this.trick[this.trickcount] = card;
    this.trickcount = (trickcount + 1) % 3;
  }

  public Card[] getHand() {
    return this.localClient.getHand().cards;
  }

  public void setTimerInSeconds(int seconds) {
    this.timerInSeconds = seconds;
  }

}
