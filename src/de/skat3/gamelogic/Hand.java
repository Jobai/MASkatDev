package de.skat3.gamelogic;

import java.io.Serializable;

public class Hand implements Serializable{

  Card[] hand;

  /**
   * Represents up to twelve cards that a player has currently on his hand.
   */
  public Hand(Card[] cards) {

    this.hand = new Card[cards.length];
    for (int i = 0; i < cards.length; i++) {
      this.hand[i] = cards[i];
    }
  }


  Card[] getCards() {
    return this.hand;
  }

  void sort() {
    this.sort(null);
  }

  void sort(Contract contract) {
    int pointer = 0;
    CardDeck deck = GameController.deck;
    Card[] sortedHand = new Card[this.hand.length];
    if (this.contains(deck.getCard("JACK OF CLUBS"))) {
      sortedHand[pointer++] = deck.getCard("JACK OF CLUBS");
    }
    if (this.contains(deck.getCard("JACK OF SPADES"))) {
      sortedHand[pointer++] = deck.getCard("JACK OF SPADES");
    }
    if (this.contains(deck.getCard("JACK OF HEARTS"))) {
      sortedHand[pointer++] = deck.getCard("JACK OF HEARTS");
    }
    if (this.contains(deck.getCard("JACK OF DIAMONDS"))) {
      sortedHand[pointer++] = deck.getCard("JACK OF DIAMONDS");
    }
    String current;
    String x = "";
    if (contract != null) {
      x = contract.name();
      for (int i = Value.length - 2; i >= 0; i--) {
        current = Value.values()[i].name() + " OF " + x;
        if (this.contains(deck.getCard(current))) {
          sortedHand[pointer++] = deck.getCard(current);
        }
      }
    }
    for (int i = 3; i >= 0; i--) {
      for (int j = Value.length - 2; j >= 0; j--) {
        current = Value.values()[j].name() + " OF " + Suit.values()[i].name();
        if (this.contains(deck.getCard(current)) && !Suit.values()[i].name().equals(x)) {
          sortedHand[pointer++] = deck.getCard(current);
        }
      }
    }
    for (int i = 0; i < 10; i++) {
      this.hand[i] = sortedHand[i];
    }
  }

  // TODO optimieren, optimieren!!
  int calcGameValue(Contract c) {
    int contractValue = -1;
    String game = "";
    switch (c) {
      case DIAMONDS:
        contractValue = 9;
        game = "DIAMONDS";
        break;
      case HEARTS:
        contractValue = 10;
        game = "HEARTS";
        break;
      case SPADES:
        contractValue = 11;
        game = "SPADES";
        break;
      case CLUBS:
        contractValue = 12;
        game = "CLUBS";
        break;
      case GRAND:
        contractValue = 24;
        break;
      case NULL:
        return 23;
      default:
        break;

    }
    int consecutiveMatadors = 1;
    CardDeck deck = GameController.deck;


    if (this.contains(deck.getCard("JACK OF CLUBS"))) {
      consecutiveMatadors++;
      if (this.contains(deck.getCard("JACK OF SPADES"))) {
        consecutiveMatadors++;
        if (this.contains(deck.getCard("JACK OF HEARTS"))) {
          consecutiveMatadors++;
          if (this.contains(deck.getCard("JACK OF DIAMONDS"))) {
            consecutiveMatadors++;
            for (int i = Value.length - 2; i >= 0; i--) {
              if (this.contains(deck.getCard(Value.values()[i].name() + " OF " + game))) {
                consecutiveMatadors++;
              } else {
                break;
              }
            }
          }
        }
      }
    } else {
      consecutiveMatadors++;
      if (!this.contains(deck.getCard("JACK OF SPADES"))) {
        consecutiveMatadors++;
        if (!this.contains(deck.getCard("JACK OF HEARTS"))) {
          consecutiveMatadors++;
          if (!this.contains(deck.getCard("JACK OF DIAMONDS"))) {
            consecutiveMatadors++;
            for (int i = Value.length - 2; i >= 0; i--) {
              if (!this.contains(deck.getCard(Value.values()[i].name() + " OF " + game))) {
                consecutiveMatadors++;
              } else {
                break;
              }
            }
          }
        }
      }
    }
    return consecutiveMatadors * contractValue;

  }

  boolean contains(Card c) {
    for (Card card : this.hand) {
      if (c.equals(card)) {
        return true;
      }
    }
    return false;

  }

  void setPlayableCards(Card card, Contract contract) {
    boolean mustFollow = false;
    for (Card c : this.hand) {
      if (card.isTrump(contract) && c.isTrump(contract)) {
        mustFollow = true;
      }
      if (card.getSuit() == c.getSuit()) {
        mustFollow = true;
      }
    }
    for (Card c : this.hand) {
      if (mustFollow) {
        if (card.isTrump(contract) && c.isTrump(contract) || card.getSuit() == c.getSuit()) {
          c.setPlayable(true);
        } else {
          c.setPlayable(false);
        }
      } else {
        c.setPlayable(true);
      }
    }

  }

  /**
   * Returns a list of all cards in the current hand.
   */
  public String toString() {
    String st = "CARDS: ";
    for (int i = 0; i < this.hand.length; i++) {
      st += this.hand[i];
      if (i < this.hand.length - 1) {
        st += ", ";
      } else {
        st += ";\n";
      }
    }
    return st;
  }

  /**
   * Removes the card from the current hand.
   * 
   * @param card the card that should be removed.
   */
  public void remove(Card card) {
    Card[] temp = new Card[this.hand.length - 1];
    int skipped = 0;
    for (int i = 0; i < temp.length; i++) {
      if (this.hand[i].equals(card)) {
        skipped = 1;
      }
      if (i + skipped < this.hand.length) {
        temp[i] = this.hand[i + skipped];
      }
    }

    this.hand = temp;
  }

  /**
   * 
   * @param skat
   */
  // public void addSkat(Card[] skat,Contract c) {
  // Card[] handWithSkat = new Card[12];
  // for (int i = 0; i < this.hand.length; i++) {
  // handWithSkat[i] = this.getCards()[i];
  // }
  // handWithSkat[10] = skat[0];
  // handWithSkat[11] = skat[1];
  // Hand hand = new Hand(handWithSkat);
  // this.hand = handWithSkat;
  //
  // }
}
