package de.skat3.gamelogic;

import de.skat3.network.server.ServerLogicController;
import java.util.concurrent.ThreadLocalRandom;


public class GameController implements GameLogicInterface {


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
  int timer;
  int mode;
  GameThread gameThread;
  static final CardDeck deck = new CardDeck();
  RoundInstance roundInstance;

  /**
   * 
   * @param players
   */
  public GameController(ServerLogicController slc, Player[] players, int timer,
      boolean kontraRekontraEnabled, int mode) {
    this.slc = slc;
    this.gameActive = true;
    this.firstRound = true;
    this.timer = timer;
    this.kontraRekontraEnabled = kontraRekontraEnabled;
    this.mode = mode;
    this.gameId = 0; // TODO
    this.numberOfPlayers = players.length;
    this.numberOfRounds = 0;
    this.players = new Player[3];
    this.allPlayers = new Player[numberOfPlayers];
    for (int i = 0; i < players.length; i++) {
      this.allPlayers[i] = players[i];
    }
    this.gameThread = new GameThread(this);
    this.gameThread.start();


  }

  void startNewRound() {
    if (firstRound) {
      this.firstRound = false;
      this.determineDealer();
    } else {
      this.rotatePlayers();
    }
    this.roundInstance = new RoundInstance(slc, this.players,this.gameThread,this.mode);

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

  @Override
  public void notifyLogicofPlayedCard(Card card) {
    this.roundInstance.addCardtoTrick(card);
    this.roundInstance.notifyRoundInstance();

  }

  @Override
  public void notifyLogicofBid(boolean accepted) {
    this.roundInstance.setBid(accepted);
    this.roundInstance.notifyRoundInstance();

  }

  @Override
  public void notifyLogicofContract(Contract contract, AdditionalMulipliers additionMultipliers) {
    this.roundInstance.contract = contract;
    // broadcastContract(contract);
    this.roundInstance.setAdditionalMultipliers(additionMultipliers);
    this.roundInstance.notifyRoundInstance();
  }

  @Override
  public void notifyLogicofKontra(boolean accepted) {
    this.roundInstance.kontra = accepted;
    this.roundInstance.notifyRoundInstance();

  }


  @Override
  public void notifyLogicofRekontra(boolean accepted) {
    this.roundInstance.rekontra = accepted;
    this.roundInstance.notifyRoundInstance();

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


