package de.skat3.gamelogic;

import de.skat3.main.SkatMain;
import de.skat3.network.server.ServerLogicController;
import java.util.ArrayList;
import java.util.Collections;

class RoundInstance {

  ServerLogicController slc;
  Player[] players;
  Player solo;
  Player[] team;
  Card[] trick;
  Card[] skat;
  Card[] originalSkat;
  Hand soloPlayerStartHand;
  Contract contract;
  AdditionalMultipliers addtionalMultipliers;
  boolean kontra;
  boolean rekontra;
  boolean kontaRekontraAvailable;
  boolean bidAccepted;
  int mode;
  int trickcount;
  GameThread gameThread;
  Object lock = new Object();


  /**
   * A single round of bidding and playing.
   * 
   * @param players the three current players
   * @param gameThread The GameThread dictates the game flow of a match.
   * @param kontraRekontraEnabled
   * @param mode Mode is either Seeger (positive number divisible by 3) or Bierlachs (negative
   *        number between -500 and -1000)
   */
  public RoundInstance(ServerLogicController slc, Player[] players, GameThread gameThread,
      boolean kontraRekontraEnabled, int mode) {
    this.slc = slc;
    this.players = players;
    this.kontra = false;
    this.rekontra = false;
    this.kontaRekontraAvailable = kontraRekontraEnabled;
    this.trickcount = 0;
    this.mode = mode;
    this.addtionalMultipliers = new AdditionalMultipliers();
    this.soloPlayerStartHand = new Hand();
    for (int i = 0; i < this.players.length; i++) {
      this.players[i].shortenPlayer(); // XXX
    }
    this.gameThread = gameThread;

  }



  void startRound() throws InterruptedException {

    this.initializeAuction();
    this.updatePlayer();
    Thread.sleep(5000);
    slc.broadcastRoundStarted();
    Player winner = this.startBidding();
    if (winner == null) {
      // this.slc.broacastRoundRestarded(); TODO
    } else {
      this.setDeclarer(winner);
      this.updatePlayer();
      this.startGame();
    }
    this.slc.broadcastRoundResult(new Result(this));


  }

  void initializeAuction() {

    this.dealCards();
    for (int i = 0; i < this.players.length; i++) {
      this.players[i].setPosition(i);
    }
  }

  private void dealCards() {

    ArrayList<Card> temp = new ArrayList<Card>();

    for (Card c : GameController.deck.getCards()) {
      temp.add(c);
    }

    Collections.shuffle(temp);
    int numberOfCards = 10;
    Card[] firstHand = new Card[numberOfCards];
    Card[] secondHand = new Card[numberOfCards];
    Card[] thirdHand = new Card[numberOfCards];
    Card[] skat = new Card[2];
    for (int i = 0; i < numberOfCards; i++) {
      firstHand[i] = temp.get(i);
      secondHand[i] = temp.get(i + numberOfCards);
      thirdHand[i] = temp.get(i + 2 * numberOfCards);
      if (i < 2) {
        skat[i] = temp.get(i + 3 * numberOfCards);
      }
    }
    this.players[0].setHand(new Hand(firstHand));
    this.players[1].setHand(new Hand(secondHand));
    this.players[2].setHand(new Hand(thirdHand));
    this.setSkat(skat);

  }

  private void updatePlayer() {
    for (int i = 0; i < players.length; i++) {
      slc.updatePlayerDuringRound(this.players[i]);
      System.out.println("LOGIC KARTEN GESENDET: " + this.players[i].getHand());
    }

  }



  /**
   * This method realizes the Auction.
   * 
   * @throws InterruptedException
   * 
   */

  int currentBiddingValue;

  /**
   * 
   * @return
   * @throws InterruptedException
   */
  public Player startBidding() throws InterruptedException {
    synchronized (lock) {
      this.currentBiddingValue = 0;
      Player currentWinner = bidDuel(this.players[1], this.players[0]);
      currentWinner = bidDuel(this.players[2], currentWinner);
      if (currentWinner.equals(this.players[0]) && this.currentBiddingValue == 0) {
        this.slc.callForBid(this.players[0], BiddingValues.values[this.currentBiddingValue]);
        if (this.bidAccepted) {
          return this.players[0];
        } else {
          return null;
        }
      } else {
        return currentWinner;
      }



    }
  }


  private Player bidDuel(Player bid, Player respond) throws InterruptedException {


    while (this.currentBiddingValue < BiddingValues.values.length) {
      this.slc.callForBid(bid, BiddingValues.values[this.currentBiddingValue]);
      lock.wait();
      if (this.bidAccepted) {
        this.slc.callForBid(respond, BiddingValues.values[this.currentBiddingValue]);
        lock.wait();
        if (this.bidAccepted) {
          this.currentBiddingValue++;
          continue;
        } else {
          this.currentBiddingValue++;
          return bid;
        }
      } else {
        return respond;
      }
    }
    return bid;

  }

  /**
   * Removes the card from the player's hand and adds it to the trick.
   * 
   * @param card played Card
   */
  public void addCardtoTrick(Card card) {
    this.players[this.trickcount].hand.remove(card);
    this.trick[this.trickcount] = card;
    this.trickcount = (this.trickcount + 1) % 3;
  }

  /**
   * This method sets the declarer based on the auction.
   * 
   * @param winner winner of the Auction
   * @throws InterruptedException synchronized on lock
   */
  public void setDeclarer(Player winner) throws InterruptedException {
    synchronized (this.lock) {

      winner.setSolo();
      this.solo = winner;
      this.team = this.getTeamPlayer();
      for (int i = 0; i < this.soloPlayerStartHand.getAmountOfCards(); i++) {
        this.soloPlayerStartHand.cards[i] = this.solo.hand.cards[i].copy();
      }

      // FIXME
      this.slc.callForHandOption(this.solo);
      this.lock.wait();
      if (!this.addtionalMultipliers.isHandGame()) {
        this.slc.sendSkat(this.solo, this.skat);
        this.lock.wait(); // notified by notifyLogicOfNewSkat(Card[] skat);
        slc.updatePlayerDuringRound(solo);
      }


      this.slc.callForContract(this.solo);
      this.lock.wait(); // Waits for the winner to select a contract, notified by
      // notifyLogicofContract()



    }

  }

  /**
   * Starts a single game round. Every player has to play a card and the winner is determined.
   * 
   */
  public void startGame() throws InterruptedException {

    synchronized (this.lock) {
      this.trick = new Card[3];

      for (int i = 0; i < 10; i++) {


        if (this.kontaRekontraAvailable) {
          slc.kontraRequest(this.getTeamPlayer());
        }

        slc.callForPlay(this.players[0]);
        this.lock.wait();
        System.out.println("LOGIC: first card played");
        if (this.kontaRekontraAvailable && !this.players[0].isSolo) {
          // slc.broadcastKontraRekontraExpired(this.players[0]); XXX
        }
        slc.sendPlayedCard(this.players[0], this.trick[0]);


        slc.callForPlay(this.players[1]);
        this.lock.wait();
        System.out.println("LOGIC: second card played");

        if (this.kontaRekontraAvailable && !this.players[1].isSolo) {
          // slc.broadcastKontraRekontraExpired(this.players[1]); XXX
        }
        slc.sendPlayedCard(this.players[1], this.trick[1]);



        slc.callForPlay(this.players[2]);
        this.lock.wait();
        System.out.println("LOGIC: third card played");
        if (this.kontaRekontraAvailable && !this.players[2].isSolo) {
          // slc.broadcastKontraRekontraExpired(this.players[2]); XXX
        }

        slc.sendPlayedCard(this.players[2], this.trick[2]);
        if (this.kontaRekontraAvailable) {
          this.kontaRekontraAvailable = false;
        }
        Player trickWinner = this.determineTrickWinner();
        if (!!trickWinner.equals(this.solo) && this.contract == Contract.NULL) {
          this.slc.broadcastRoundResult(new Result(this));
          break;
        }
        for (Card card : this.trick) {
          trickWinner.wonTricks.add(card);
        }
        slc.broadcastTrickResult(trickWinner);
        this.rotatePlayers(trickWinner);
      }
    }

  }


  public Player determineTrickWinner() {
    Contract contract = this.getContract();

    if (this.getFirstCard().beats(contract, this.getSecondCard())
        && this.getFirstCard().beats(contract, this.getThirdCard())) {
      return this.getForehand();

    } else {
      return (this.getSecondCard().beats(contract, this.getThirdCard())) ? this.getMiddlehand()
          : this.getRearhand();
    }


  }



  private void rotatePlayers(Player trickWinner) {
    if (trickWinner.equals(this.getForehand())) {
      return;
    } else {
      if (trickWinner.equals(this.getMiddlehand())) {
        Player temp = this.getForehand();
        this.players[0] = this.getMiddlehand();
        this.players[1] = this.getRearhand();
        this.players[2] = temp;
      } else {
        Player temp = this.getForehand();
        this.players[0] = this.getRearhand();
        this.players[2] = this.getMiddlehand();
        this.players[1] = temp;
      }
    }

    for (int i = 0; i < this.players.length; i++) {
      this.players[i].setPosition(i);
    }

  }



  Player getForehand() {
    return this.players[0];
  }

  Player getMiddlehand() {
    return this.players[1];
  }

  Player getRearhand() {
    return this.players[2];
  }

  public Card getFirstCard() {
    return this.trick[0];
  }

  public Card getSecondCard() {
    return this.trick[1];
  }

  public Card getThirdCard() {
    return this.trick[2];
  }

  Player[] getTeamPlayer() {

    Player[] temp = new Player[2];
    int found = 0;
    for (int i = 0; i < this.players.length; i++) {
      if (!this.players[i].isSolo) {
        temp[found++] = this.players[i];
      }
    }
    return temp;
  }


  public Contract getContract() {
    return this.contract;
  }

  /**
   * Puts the two remaining cards in the skat.
   * 
   * @param skat two cards that are left from the dealing.
   */
  public void setSkat(Card[] skat) {
    this.skat = new Card[skat.length];
    this.originalSkat = new Card[skat.length];
    for (int i = 0; i < skat.length; i++) {
      this.skat[i] = skat[i];
      this.originalSkat[i] = skat[i];
    }

  }



  void notifyRoundInstance() {
    synchronized (lock) {
      this.lock.notify();
    }
  }

  void setBid(boolean b) {
    this.bidAccepted = b;

  }



  public void setAdditionalMultipliers(AdditionalMultipliers additionMultipliers) {
    this.addtionalMultipliers = additionMultipliers;

  }


}

