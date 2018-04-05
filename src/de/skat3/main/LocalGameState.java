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
  Player localClient;
  ObservableList<Card> trick;
  ObservableList<Card> hand;
  Card[] skat;
  ArrayList<String> chatMessages;

  /**
   * The current state of a game.
   * 
   */
  public LocalGameState() {
    trick = FXCollections.observableArrayList();
    hand = FXCollections.observableArrayList();
    skat = new Card[2];
  }

  public void setPlayer(Player player) {
    this.localClient = player;
    this.hand = FXCollections.observableArrayList(Arrays.asList(this.localClient.getHand().cards));
  }

  /**
   * Adds a played card to the trick.
   * 
   * @param card the played card.
   */

  public void addToTrick(Card card) {
    this.trick.add(card);
    if (this.trick.size() == 3) {
      this.trick.clear();
    }
  }

  public void setTimerInSeconds(int seconds) {
    this.timerInSeconds = seconds;
  }

}
