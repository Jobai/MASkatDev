package de.skat3.ai;

import de.skat3.gamelogic.Card;
import de.skat3.gamelogic.Hand;

public class ReturnSkat {
  Hand hand;
  Card[] skat;

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

