package de.skat3.ai;

import de.skat3.gamelogic.Card;
import de.skat3.gamelogic.Hand;

/**
 * ReturnSkat represents a construct constisting of 10 cards - hand and 2 cards - skat. This class
 * alows returning skat and hand in one method.
 * 
 * @author Kai Baumann
 */
public class ReturnSkat {
  Hand hand;
  Card[] skat;

  /**
   * Constructor.
   * 
   * @param hand represents hand of player after selecting skat.
   * @param skat represents skat after player selected skat.
   */
  public ReturnSkat(Hand hand, Card[] skat) {
    this.hand = new Hand(hand.cards);
    this.skat = new Card[2];
    this.skat[0] = skat[0].copy();
    this.skat[1] = skat[1].copy();
  }

  public Hand getHand() {
    return this.hand;
  }

  public Card[] getSkat() {
    return this.skat;
  }
}

