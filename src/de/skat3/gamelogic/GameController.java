package de.skat3.gamelogic;

import de.skat3.network.server.ServerLogicController;
import java.io.Serializable;
import java.util.concurrent.ThreadLocalRandom;


public class GameController implements GameLogicInterface, Serializable {


  boolean gameActive;
  ServerLogicController slc;
  int gameId;
  int numberOfPlayers;
  Player dealer;
  Player[] players; // only the players
  Player[] allPlayers; // players and dealer
  int numberOfRounds;
  boolean firstRound;
  boolean kontraRekontraEnabled;
  int mode;
  GameThread gameThread;
  static final CardDeck deck = new CardDeck();
  RoundInstance roundInstance;
  MatchResult matchResult;

  /**
   * Creates a new match with 3-4 Players, an optional timer, optional kontra and rekontra feature
   * and a Seeger or Bierlachs scoring system.
   * 
   * @param players All participants.
   * @param kontraRekontraEnabled true if the kontra/rekontra feature is enabled.
   * @param mode Mode is either Seeger (positive number divisible by 3) or Bierlachs (negative
   *        number between -500 and -1000)
   */
  public GameController(boolean kontraRekontraEnabled, int mode) {
    this.gameActive = true;
    this.firstRound = true;
    this.kontraRekontraEnabled = kontraRekontraEnabled;
    this.mode = mode;
    this.gameId = 0; // TODO
    this.numberOfRounds = 0;
    this.players = new Player[3];
    this.matchResult = new MatchResult(this.allPlayers);
    this.gameThread = new GameThread(this);



  }

  /**
   * 
   * @param players
   * @param slc
   */
  public void startGame(Player[] players, ServerLogicController slc) {
    this.numberOfPlayers = players.length;
    this.allPlayers = new Player[numberOfPlayers];
    for (int i = 0; i < players.length; i++) {
      this.allPlayers[i] = players[i];
    }
    this.slc = slc;
    this.gameThread.start();
  }

  void startNewRound() {
    if (firstRound) {
      this.firstRound = false;
      this.determineDealer();
    } else {
      this.rotatePlayers();
    }
    this.roundInstance = new RoundInstance(slc, this.players, this.gameThread,
        this.kontraRekontraEnabled, this.mode);
    try {
      this.roundInstance.startRound();
    } catch (InterruptedException e) {
      System.err.println("Runde konnte nicht gestartet werden: " + e);
    }

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
   * This method rotates the player after a single round of play.
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

  /**
   * Adds a played card to the trick.
   */

  @Override
  public void notifyLogicofPlayedCard(Card card) {
    this.roundInstance.addCardtoTrick(card);
    this.roundInstance.notifyRoundInstance();

  }

  /**
   * Tells the logic if a bid was accepted.
   */
  @Override
  public void notifyLogicofBid(boolean accepted) {
    this.roundInstance.setBid(accepted);
    this.slc.broadcastBid(accepted);
    this.roundInstance.notifyRoundInstance();

  }

  /**
   * Sets the selected contract and additionalMultipliers.
   */
  @Override
  public void notifyLogicofContract(Contract contract,
      AdditionalMultipliers additionalMultipliers) {
    this.roundInstance.contract = contract;
    additionalMultipliers.setHandGame(this.roundInstance.addtionalMultipliers.isHandGame());
    this.roundInstance.setAdditionalMultipliers(additionalMultipliers);
    // this.slc.broadcastContract(contract, additionalMultipliers); TODO
    this.roundInstance.notifyRoundInstance();
  }

  @Override
  public void notifyLogicofKontra(boolean accepted) {
    if (this.roundInstance.kontaRekontraAvailable) {
      this.roundInstance.kontra = accepted;
      // this.slc.broadcastKontraAnnounced();
      // this.slc.rekontraRequest(this.roundInstance.solo); TODO
    } else {
      System.err.println("Kontra set although its not enabled.");
    }

  }


  @Override
  public void notifyLogicofRekontra(boolean accepted) {
    if (this.roundInstance.kontaRekontraAvailable && this.roundInstance.kontra) {
      this.roundInstance.rekontra = accepted;
      // this.slc.broadcastRekontraAnnounced(); TODO
    } else {
      System.err.println("Rekontra set although its not enabled or kontra was not announced.");
    }

  }

  @Override
  public void notifyLogicOfHandGame(boolean accepted) {
    this.roundInstance.addtionalMultipliers.setHandGame(accepted);
    this.roundInstance.notifyRoundInstance();

  }



  @Override
  public void notifyLogicOfNewSkat(Hand hand, Card[] skat) {
    this.roundInstance.solo.setHand(hand);
    this.roundInstance.skat = skat;
    this.roundInstance.notifyRoundInstance();

  }
}


