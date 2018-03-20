package de.skat3.gamelogic;

import de.skat3.network.server.ServerLogicController;
import java.util.ArrayList;
import java.util.Collections;

public class RoundInstance {

  ServerLogicController slc;
  Player[] players;
  Player solo;
  Player[] team;
  Card[] trick;
  Card[] skat;
  Card[] originalSkat;
  Hand soloPlayerStartHand;
  Contract contract;
  boolean handGame;
  AdditionalMulipliers addtionalMultipliers;
  RoundInstanceThread roundInstanceThread;
  boolean kontra;
  boolean rekontra;
  boolean bidAccepted;
  int trickcount;
  Object lock = new Object();


  /**
   * A single round of bidding and playing.
   * 
   * @param players the three current players
   */
  public RoundInstance(ServerLogicController slc, Player[] players) {
    this.slc = slc;
    this.players = new Player[3];
    this.kontra = false;
    this.rekontra = false;
    this.trickcount = 0;
    this.soloPlayerStartHand = new Hand();
    for (int i = 0; i < players.length; i++) {
      this.players[i] = players[i];
    }
    this.roundInstanceThread = new RoundInstanceThread(this);
    this.roundInstanceThread.start();

  }

  void initializeAuction() {

    this.dealCards();
    for (int i = 0; i < this.players.length; i++) {
      this.players[i].setPosition(i);
    }
    this.sortCards(null);
    this.notifyPlayers();


  }


  private void notifyPlayers() {
    for (int i = 0; i < players.length; i++) {
      System.out.println("Aufgerufen, Logik");
      slc.sendStartHandtoPlayer(this.players[i]);
    }

  }

  private void sortCards(Contract c) {
    for (int i = 0; i < this.players.length; i++) {
      if (c == null) {
        this.players[i].hand.sort();
      } else {
        this.players[i].hand.sort(c);
      }
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

  private Player determineTrickWinner() {
    Contract contract = this.getContract();

    if (this.getFirstCard().beats(contract, this.getSecondCard())
        && this.getFirstCard().beats(contract, this.getThirdCard())) {
      return this.getForehand();

    } else {
      return (this.getSecondCard().beats(contract, this.getThirdCard())) ? this.getMiddlehand()
          : this.getRearhand();
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

  Card getFirstCard() {
    return this.trick[0];
  }

  Card getSecondCard() {
    return this.trick[1];
  }

  Card getThirdCard() {
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


  Contract getContract() {
    return this.getContract();
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

  /**
   * @throws InterruptedException
   * 
   */
  public Player startBidding() throws InterruptedException {
    synchronized (lock) {
      int position = 0;
      Player respond = this.players[0];
      Player bid = this.players[1];
      Player currentWinner = null;
      boolean initialBidding = true;
      while (position < BiddingValues.values.length) {
        System.out.println("Warte auf Gebot");
        this.slc.callForBid(bid, BiddingValues.values[position]);
        lock.wait();
        if (this.bidAccepted) {
          currentWinner = bid;
        } else {
          if (initialBidding) {
            bid = this.players[2];
            initialBidding = false;
            continue;
          }
        }
        this.slc.callForBid(respond, BiddingValues.values[position]);
        lock.wait();
        if (this.bidAccepted) {
          currentWinner = respond;
        } else {
          if (initialBidding) {
            respond = this.players[1];
            bid = this.players[2];
            initialBidding = false;
            continue;
          } else {
            return currentWinner;
          }
        }
        position++;

      }


      return currentWinner;
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

  /**
   * 
   * @throws InterruptedException
   */
  public void startGame() throws InterruptedException {

    synchronized (this.lock) {
      this.trick = new Card[3];

      for (int i = 0; i < 10; i++) {
        //
        
        slc.callForPlay(this.players[0]);
        this.lock.wait();
        slc.sendPlayedCard(this.players[0], this.trick[0]);
        
        slc.callForPlay(this.players[1]);
        this.lock.wait();
        slc.sendPlayedCard(this.players[1], this.trick[1]);

        slc.callForPlay(this.players[2]);
        this.lock.wait();
        slc.sendPlayedCard(this.players[2], this.trick[2]);
        Thread.sleep(2500); //delay for visuals
        Player trickWinner = this.determineTrickWinner();
        for (Card c : this.trick) {
          if (trickWinner.isSolo) {
            trickWinner.wonTricks.add(c);
          } else {
            this.team[0].wonTricks.add(c);
            this.team[1].wonTricks.add(c);
          }
        }
        slc.broadcastTrickResult(trickWinner);
        this.rotatePlayers(trickWinner);
      }
      this.determineGameWinner();

    }
  }


  private void determineGameWinner() {
    int contractValue;
    switch (contract) {
      case DIAMONDS:
        contractValue = 9;
        break;
      case HEARTS:
        contractValue = 10;
        break;
      case SPADES:
        contractValue = 11;
        break;
      case CLUBS:
        contractValue = 12;
        break;
      case GRAND:
        contractValue = 24;
        break;
      case NULL:
        contractValue = 23;
      default:
        break;

    }
    int pointsSoloPlayer = 0;
    int baseValue = 0;
    for (Card c : this.solo.wonTricks) {
      pointsSoloPlayer += c.getTrickValue();
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
        this.soloPlayerStartHand.hand[i] = this.solo.hand.hand[i];
      }
      this.slc.callforHandOption(this.solo);
      this.lock.wait();

      this.slc.sendSkatToPlayer(this.solo,this.skat);
      this.lock.wait(); // notified by notifyLogicOfNewSkat(Card[] skat);


      this.slc.callForContract(this.solo);
      this.lock.wait(); // Waits for the winner to select a contract, notified by
      // notifyLogicofContract()



    }

  }

  public void setAdditionalMultipliers(AdditionalMulipliers additionMultipliers) {
    this.addtionalMultipliers = additionMultipliers;

  }
}

