package de.skat3.gamelogic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

public class GameController {


  boolean gameActive;
  int gameId;
  int numberOfPlayers;
  Player dealer;
  Player[] players; // just the players
  Player[] allPlayers; // players and dealer
  ArrayList<String> chatMessages;
  ServerGameState serverGameState;
  static final CardDeck deck = new CardDeck();

  /**
   * 
   * @param players
   */
  public GameController(Player[] players) {
    this.gameActive = true;
    this.gameId = 0; // TODO
    this.numberOfPlayers = players.length;
    this.players = new Player[3];
    this.allPlayers = new Player[numberOfPlayers];
    for (int i = 0; i < players.length; i++) {
      this.allPlayers[i] = players[i];
    }
    chatMessages = new ArrayList<String>();


  }

  void startNewRound(boolean firstRound) {
    if (firstRound) {
      this.determineDealer();
    } else {
      this.rotatePlayers();
    }
    new RoundInstance(players);

  }

  private void determineDealer() {
    int i = ThreadLocalRandom.current().nextInt(0, numberOfPlayers);
    if (numberOfPlayers == 3) {
      this.dealer = allPlayers[i];
      this.players[0] = allPlayers[(i + 1) % 3];
      this.players[1] = allPlayers[(i + 2) % 3];
      this.players[2] = allPlayers[i];
    } else {
      this.dealer = allPlayers[i];
      this.players[0] = allPlayers[(i + 1) % 4];
      this.players[1] = allPlayers[(i + 2) % 4];
      this.players[2] = allPlayers[(i + 3) % 4];
    }
  }

  Player temp;

  /**
   * 
   */
  private void rotatePlayers() {

    if (numberOfPlayers == 3) {
      Player temp = this.players[0];
      this.players[0] = this.players[1];
      this.players[1] = this.players[2];
      this.players[2] = temp;
      this.dealer = this.players[0];
    } else {
      temp = this.getDealer();
      this.dealer = this.players[0];
      this.players[0] = this.players[1];
      this.players[1] = this.players[2];
      this.players[2] = temp;

    }

  }

  Player getDealer() {
    return this.dealer;
  }
}


