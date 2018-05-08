package de.skat3.gamelogic;

import java.io.Serializable;

/**
 * Represents up to ten cards that a player has currently on his hand.
 */
@SuppressWarnings("serial")
public class Hand implements Serializable {

  public Card[] cards;

  /**
   * Fills the hand with the specified cards.
   */
  public Hand(Card[] cards) {
    this.cards = new Card[cards.length];
    for (int i = 0; i < cards.length; i++) {
      this.cards[i] = cards[i].copy();
    }
  }

  /**
   * Creates an hand with 10 empty cards.
   */
  public Hand() {
    this.cards = new Card[10];
    for (int i = 0; i < this.cards.length; i++) {
      this.cards[i] = new Card();
    }
  }

  /**
   * Creates an empty hand with the amount of cards.
   */
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

  /**
   * Sorts the hand in this order: all jacks, clubs, spades, hearts, diamonds.
   */
  void sort() {
    this.sort(null);
  }

  /**
   * Sorts the hand in this order: all trumps, clubs, spades, hearts, diamonds.
   * 
   */
  public void sort(Contract contract) {
    int pointer = 0;
    CardDeck deck = GameController.deck;
    Card[] sortedHand = new Card[this.cards.length];
    String current;
    String x = "";
    if (contract != Contract.NULL) {
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
      if (contract != null && contract != Contract.NULL && contract != Contract.GRAND) {
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
    } else {
      Value[] nullValues = {Value.SEVEN, Value.EIGHT, Value.NINE, Value.TEN, Value.JACK,
          Value.QUEEN, Value.KING, Value.ACE};
      for (int i = 3; i >= 0; i--) {
        for (int j = Value.length - 1; j >= 0; j--) {
          current = nullValues[j].name() + " OF " + Suit.values()[i].name();
          if (this.contains(deck.getCard(current))) {
            sortedHand[pointer++] = deck.getCard(current);
          }
        }
      }
    }
    for (int i = 0; i < this.cards.length; i++) {
      this.cards[i] = sortedHand[i];
    }
  }

  /**
   * Calculates the consecutive matators in the hand of the player.
   */
  int calcConsecutiveMatadors(Contract contract) {
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
            if (contract != Contract.GRAND) {
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
      }
    } else {
      consecutiveMatadors++;
      if (!this.contains(deck.getCard("JACK OF SPADES"))) {
        consecutiveMatadors++;
        if (!this.contains(deck.getCard("JACK OF HEARTS"))) {
          consecutiveMatadors++;
          if (!this.contains(deck.getCard("JACK OF DIAMONDS"))) {
            consecutiveMatadors++;
            if (contract != Contract.GRAND) {
              for (int i = Value.length - 2; i >= 0; i--) {
                if (!this
                    .contains(deck.getCard(Value.values()[i].name() + " OF " + contract.name()))) {
                  consecutiveMatadors++;
                } else {
                  break;
                }
              }
            }
          }
        }
      }
    }
    return consecutiveMatadors;

  }

  /*
   * Calculates the amount of consecutive matadors in the a combined pool of the player's hand and
   * the skat.
   */
  int calcConsecutiveMatadors(Contract contract, Card[] skat) {
    if (contract == Contract.NULL) {
      return 0;
    }
    int consecutiveMatadors = 0;

    Card[] oldCards = new Card[this.cards.length];
    for (int i = 0; i < oldCards.length; i++) {
      oldCards[i] = this.cards[i].copy();
    }
    Card[] temporary = new Card[this.cards.length + skat.length];
    for (int i = 0; i < this.cards.length; i++) {
      temporary[i] = this.cards[i].copy();
    }
    temporary[temporary.length - 2] = skat[0];
    temporary[temporary.length - 1] = skat[1];
    this.cards = temporary;
    CardDeck deck = GameController.deck;
    if (this.contains(deck.getCard("JACK OF CLUBS"))) {
      consecutiveMatadors++;
      if (this.contains(deck.getCard("JACK OF SPADES"))) {
        consecutiveMatadors++;
        if (this.contains(deck.getCard("JACK OF HEARTS"))) {
          consecutiveMatadors++;
          if (this.contains(deck.getCard("JACK OF DIAMONDS"))) {
            consecutiveMatadors++;
            if (contract != Contract.GRAND) {
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
      }
    } else {
      consecutiveMatadors++;
      if (!this.contains(deck.getCard("JACK OF SPADES"))) {
        consecutiveMatadors++;
        if (!this.contains(deck.getCard("JACK OF HEARTS"))) {
          consecutiveMatadors++;
          if (!this.contains(deck.getCard("JACK OF DIAMONDS"))) {
            consecutiveMatadors++;
            if (contract != Contract.GRAND) {
              for (int i = Value.length - 2; i >= 0; i--) {
                if (!this
                    .contains(deck.getCard(Value.values()[i].name() + " OF " + contract.name()))) {
                  consecutiveMatadors++;
                } else {
                  break;
                }
              }
            }
          }
        }
      }
    }
    this.cards = oldCards;
    return consecutiveMatadors;

  }

  /**
   * Returns true if the hand cointains the specific card.
   */
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
        e.printStackTrace();
      }
    }
    return false;

  }

  /**
   * Sets all cards playable that are playable while the specific card is the first card that was
   * played in the trick and the specific contract is selected.
   */
  public void setPlayableCards(Card card, Contract contract) {
    boolean mustFollow = false;;
    boolean trumpCard = card.isTrump(contract);

    for (Card c : this.cards) {

      if (trumpCard) {
        if (c.isTrump(contract)) {
          mustFollow = true;
        }
      } else {
        if (c.getSuit() == card.getSuit() && !c.isTrump(contract)) {
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

          if (c.getSuit() == card.getSuit() && !c.isTrump(contract)) {
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

  /**
   * Calculates the maximum bid that a player can make while holding this hand with the specific
   * contract.
   */
  public int getMaximumBid(Contract contract) {
    if (this.cards.length == 10) {

      int trumps = 0;
      if (contract != Contract.NULL) {
        trumps = this.calcConsecutiveMatadors(contract);
      }
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
          System.out.println("error in maxBid");
          return 0;

      }
    } else {
      System.err.println("Card does not contain 10 Cards");
      return 0;
    }

  }

  /**
   * Sets all cards playable that are in this hand.
   */
  public void setAllCardsPlayable() {
    for (Card card : this.cards) {
      card.setPlayable(true);
    }

  }
}
