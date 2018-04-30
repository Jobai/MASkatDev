package de.skat3.main;

import de.skat3.ai.Ai;
import de.skat3.ai.IntelligentAI;
import de.skat3.ai.RandomAI;
import de.skat3.gamelogic.AdditionalMultipliers;
import de.skat3.gamelogic.Card;
import de.skat3.gamelogic.Contract;
import de.skat3.gamelogic.Hand;
import de.skat3.gamelogic.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.ArrayList;
import java.util.Arrays;

public class LocalGameState {

  public boolean gameActive;
  public boolean singlePlayerGame;
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
  private int localPosition;

  /**
   * The current state of a game.
   * 
   * @param singlePlayerGame
   * 
   */
  public LocalGameState(int numberOfPlayers, int timerInSeconds, boolean singlePlayerGame) {
    this.localPosition = SkatMain.mainController.currentLobby.currentPlayers;
    this.localClient = SkatMain.mainController.currentLobby.players[this.localPosition - 1];
    this.singlePlayerGame = singlePlayerGame;
    this.trickcount = 0;
    this.trick = new Card[3];
    this.skat = new Card[2];
    chatMessages = FXCollections.observableArrayList();
    SkatMain.mainController.reinitializePlayers();
  }

  /**
   * 
   */


  public void addPlayer() {


    if (SkatMain.mainController.currentLobby.currentPlayers == 2) {
      switch (this.localPosition) {
        case 1:
          this.enemyOne = SkatMain.mainController.currentLobby.players[1];
          break;
        case 2:
          this.enemyTwo = SkatMain.mainController.currentLobby.players[0];
          break;

        default:
          System.err.println("addPlayer klappt net ");
          break;
      }

    }
    if (SkatMain.mainController.currentLobby.currentPlayers == 3) {
      switch (this.localPosition) {
        case 1:
          this.enemyTwo = SkatMain.mainController.currentLobby.players[2];
          break;
        case 2:
          this.enemyOne = SkatMain.mainController.currentLobby.players[2];
          break;
        case 3:
          this.enemyOne = SkatMain.mainController.currentLobby.players[0];
          this.enemyTwo = SkatMain.mainController.currentLobby.players[1];
        default:
          System.err.println("addPlayer klappt net ");
          break;
      }


      // case 4: // TODO
    }

    SkatMain.mainController.reinitializePlayers();



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
      return null;
    }
  }

  public Hand getLocalHand() {
    return this.localClient.getHand();
  }


  public void addMessage(String message) {
    this.chatMessages.add(message);
  }

  /**
   * 
   * @param bot
   * @return
   */

  /**
   * 
   * @return
   */
  public Player getPlayer(Player player) {

    if (this.localClient.equals(player)) {
      return this.localClient;
    }
    if (this.enemyOne.equals(player)) {
      return this.enemyOne;
    }
    if (this.enemyTwo.equals(player)) {
      return this.enemyTwo;
    }
    if (this.dealer.equals(player)) {
      return this.dealer;
    }

    System.err.println("No Player found");
    return null;
  }

}


