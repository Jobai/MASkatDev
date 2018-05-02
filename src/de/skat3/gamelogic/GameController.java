package de.skat3.gamelogic;

import de.skat3.network.server.ServerLogicController;
import java.io.Serializable;
import java.util.concurrent.ThreadLocalRandom;


/**
 * 
 * @author kai29
 *
 */
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
    this.gameThread = new GameThread(this);



  }



  public void startGame(Player[] players, ServerLogicController slc) {

    if (players.length != 3 && players.length != 4) {
      if (players.length < 3) {
        System.err.println("LOGIC: Not enough layers connected: " + players.length);
        return;
      } else {
        System.err.println("LOGIC: Too many players connected: " + players.length);
        return;
      }
    }
    for (int i = 0; i < players.length; i++) {
      for (int j = i + 1; j < players.length; j++) {
        if (players[i].equals(players[j])) {
          System.err
              .println("LOGIC: Duplicate Player: " + players[i].name + ", " + players[i].getUuid());
          return;
        }
      }
    }
    this.numberOfPlayers = players.length;
    this.allPlayers = new Player[numberOfPlayers];
    for (int i = 0; i < players.length; i++) {
      this.allPlayers[i] = players[i].copyPlayer();

    }
    this.matchResult = new MatchResult(this.allPlayers);
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
      System.err.println("LOGIC: Runde konnte nicht gestartet werden: " + e);
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

  public Player getDealer() {
    return this.dealer;
  }

  /**
   * Adds a played card to the trick.
   */

  @Override
  public void notifyLogicofPlayedCard(Card card) {
    if (card.getSuit() == null || card.getValue() == null) {
      System.err.println("LOGIC: Null Card sent to Logic");
      return;
    }
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
    if (contract == null || additionalMultipliers == null) {
      System.err.println("LOGIC: Wrong Contract sent to Logic");
      return;
    }
    this.roundInstance.contract = contract;
    additionalMultipliers.setHandGame(this.roundInstance.addtionalMultipliers.isHandGame());
    this.roundInstance.setAdditionalMultipliers(additionalMultipliers);
    this.slc.broadcastContract(contract, additionalMultipliers);
    this.roundInstance.notifyRoundInstance();
  }

  @Override
  public void notifyLogicofKontra() {
    if (this.roundInstance.kontaRekontraAvailable) {
      this.roundInstance.kontra = true;
      this.slc.broadcastKontraAnnounced();
      this.slc.reKontraRequest(this.roundInstance.solo);
    } else {
      System.err.println("LOGIC: Kontra set although its not enabled.");
    }

  }


  @Override
  public void notifyLogicofRekontra() {
    if (this.roundInstance.kontaRekontraAvailable && this.roundInstance.kontra) {
      this.roundInstance.rekontra = true;
      this.slc.broadcastRekontraAnnounced();
    } else {
      System.err
          .println("LOGIC: Rekontra set although its not enabled or kontra was not announced.");
    }

  }

  @Override
  public void notifyLogicOfHandGame(boolean accepted) {
    this.roundInstance.addtionalMultipliers.setHandGame(accepted);
    this.roundInstance.notifyRoundInstance();

  }



  @Override
  public void notifyLogicOfNewSkat(Hand hand, Card[] skat) {
    if (hand.cards.length != 10 || skat[0] == null || skat[1] == null) {
      System.err.println("LOGIC: Wrong hand or Skat send to logic.");
      return;
    }
    this.roundInstance.solo.setHand(new Hand(hand.cards)); // XXX ???
    System.out.println("logic hand: " + hand);
    this.roundInstance.skat[0] = skat[0].copy();
    this.roundInstance.skat[1] = skat[1].copy();
    this.roundInstance.notifyRoundInstance();

  }

  public void exchangePlayer(Player oldPlayer, Player newPlayer) {
    for (int i = 0; i < this.allPlayers.length; i++) {
      if (this.allPlayers[i].equals(oldPlayer)) {
        this.allPlayers[i] = newPlayer.copyPlayer();
        return; // XXX
      }
    }
  }
}


