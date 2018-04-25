package de.skat3.main;

import de.skat3.ai.Ai;
import de.skat3.ai.IntelligentAI;
import de.skat3.ai.RandomAI;
import de.skat3.gamelogic.AdditionalMultipliers;
import de.skat3.gamelogic.Card;
import de.skat3.gamelogic.Contract;
import de.skat3.gamelogic.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.ArrayList;
import java.util.Arrays;

public class LocalGameState {

  public boolean gameActive;
  public int gameId;
  public int timerInSeconds;
  public Contract contract;
  public AdditionalMultipliers additionalMultipliers;
  public int trickcount;
  public Player localClient;
  public Player enemyOne;
  public Player enemyTwo;
  public Player dealer;
  public Card[] trick;
  public Card[] skat;
  public ObservableList<String> chatMessages;

  public Ai firstAi;

  public Ai secondAi;

  /**
   * The current state of a game.
   * 
   */
  public LocalGameState(int numberOfPlayers, Player[] players, int timerInSeconds) {
    trickcount = 0;
    trick = new Card[3];
    skat = new Card[2];
    localClient = players[0];
    if (players[1].isBot()) {
      if (players[1].isHardBot()) {
        firstAi = new IntelligentAI(players[1]);
      }else {
        firstAi = new RandomAI(players[1]);
      }
    }
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
    if (trickcount == 0) {
      this.trick[0] = null;
      this.trick[1] = null;
      this.trick[2] = null;
    }
    this.trick[this.trickcount] = card;
    this.trickcount = (trickcount + 1) % 3;
  }

  public Card getCurrentCardInTrick() {
    for (int i = 0; i < this.trick.length; i++) {
      if (this.trick[i] == null && i - 1 > 0) {
        return this.trick[i - 1];
      }
    }
    return null;
  }


  public Card getFirstCardPlayed() {
    if (this.trick[0] != null && this.trickcount != 0) {
      return this.trick[0];
    } else {
      System.err.println("NO CARD IN TRICK");
      return null;
    }
  }

  public Card[] getLocalHand() {
    return this.localClient.getHand().cards;
  }


  public void addMessage(String message) {
    this.chatMessages.add(message);
  }

}
