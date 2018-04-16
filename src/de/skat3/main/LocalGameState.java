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
  ObservableList<String> chatMessages;

  /**
   * The current state of a game.
   * 
   */
  public LocalGameState(int numberOfPlayers, Player[] players, int timerInSeconds) {
    trickcount = 0;
    trick = new Card[3];
    skat = new Card[2];
    localClient = players[0];
    enemyOne = players[1];
    enemyTwo = players[2];
    if (numberOfPlayers == 4) {
      dealer = players[3];
    }
    chatMessages = FXCollections.observableArrayList();

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


  public void addMessage(String message) {
    this.chatMessages.add(message);
  }

}
