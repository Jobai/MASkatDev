package de.skat3.gamelogic;

import java.util.ArrayList;
import java.util.Collections;

public class RoundInstance {

  Player[] players = new Player[3];
  Card[] trick;
  Card[] skat;
  Contract contract;


  /**
   * A single round of bidding and playing.
   * 
   * @param players the three current players
   */
  public RoundInstance(Player[] players) {

    for (int i = 0; i < players.length; i++) {
      this.players[i] = players[i];
    }
    this.initializeAuction();

  }

  private void initializeAuction() {

    this.dealCards();
    for (int i = 0; i < this.players.length; i++) {
      this.players[i].setPosition(i);
    }
    // Bidding starts



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
    return this.trick[0];
  }

  Card getThirdCard() {
    return this.trick[0];
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
    for (int i = 0; i < skat.length; i++) {
      this.skat[i] = skat[i];
    }

  }
}

