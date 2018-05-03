package de.skat3.gamelogic;

import java.io.Serializable;

public class Hand implements Serializable {

  public Card[] cards;

  /**
   * Represents up to ten cards that a player has currently on his hand.
   */
  public Hand(Card[] cards) {
    this.cards = new Card[cards.length];
    for (int i = 0; i < cards.length; i++) {
      this.cards[i] = cards[i].copy();
    }
  }

  public Hand() {
    this.cards = new Card[10];
    for (int i = 0; i < this.cards.length; i++) {
      this.cards[i] = new Card();
    }
  }

  public Hand(int amountOfCards) {
    this.cards = new Card[amountOfCards];
    for (int i = 0; i < this.cards.length; i++) {
      this.cards[i] = new Card();
    }
  }

  public Card[] getCards() {
    return this.cards;
  }

  int getAmountOfCards() {
    return this.cards.length;
  }

  void sort() {
    this.sort(null);
  }

  public void sort(Contract contract) {
    int pointer = 0;
    CardDeck deck = GameController.deck;
    Card[] sortedHand = new Card[this.cards.length];
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
    for (int i = 0; i < this.cards.length; i++) {
      this.cards[i] = sortedHand[i];
    }
  }

  // TODO skat
  int calcConsecutiveMatadors(Contract contract, Card[] skat) {
    int consecutiveMatadors = 0;
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
              if (this.contains(
                  deck.getCard(Value.values()[i].name() + " OF " + contract.toString()))) {
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
              if (!this.contains(
                  deck.getCard(Value.values()[i].name() + " OF " + contract.toString()))) {
                consecutiveMatadors++;
              } else {
                break;
              }
            }
          }
        }
      }
    }
    return consecutiveMatadors;

  }

  boolean contains(Card c) {
    for (Card card : this.cards) {
      try {
        if (c == null && card == null) {
          return true;
        }
        if (c.equals(card)) {
          return true;
        }
      } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
    return false;

  }

  public void setPlayableCards(Card card, Contract contract) {
    boolean mustFollow = false;;
    boolean trumpCard = card.isTrump(contract);

    for (Card c : this.cards) {

      if (trumpCard) {
        if (c.isTrump(contract)) {
          mustFollow = true;
        }
      } else {
        if (c.getSuit() == card.getSuit() && !c.isJack()) {
          mustFollow = true;
        }
      }
    }
    for (Card c : this.cards) {
      if (mustFollow) {
        if (trumpCard) {
          if (c.isTrump(contract)) {
            c.setPlayable(true);
          } else {
            c.setPlayable(false);
          }
        } else {

          if (c.getSuit() == card.getSuit() && !c.isJack()) {
            c.setPlayable(true);
          } else {
            c.setPlayable(false);
          }

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
    for (int i = 0; i < this.cards.length; i++) {
      st += this.cards[i];

      st += " Playble = " + this.cards[i].isPlayable();

      if (i < this.cards.length - 1) {
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
    Card[] temp = new Card[this.cards.length - 1];
    int skipped = 0;
    for (int i = 0; i < temp.length; i++) {
      try {
        if (this.cards[i].equals(card)) {
          skipped = 1;
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
      if (i + skipped < this.cards.length) {
        temp[i] = this.cards[i + skipped];
      }
    }
    this.cards = new Card[temp.length];
    for (int i = 0; i < this.cards.length; i++) {
      this.cards[i] = temp[i].copy();
    }
  }


  public int getMaximumBid(Contract contract) {
    if (this.cards.length == 10) {

      int trumps = this.calcConsecutiveMatadors(contract, null);

      switch (contract) {
        case CLUBS:
          return (trumps + 1) * 12;
        case DIAMONDS:
          return (trumps + 1) * 9;
        case GRAND:
          return (trumps + 1) * 24;
        case HEARTS:
          return (trumps + 1) * 9;
        case NULL:
          return 23;
        case SPADES:
          return (trumps + 1) * 11;
        default:
          return 0;

      }
    } else {
      System.err.println("Card does not contain 10 Cards");
      return 0;
    }

  }

  public void setAllCardsPlayable() {
    for (Card card : this.cards) {
      card.setPlayable(true);
    }

  }
}
