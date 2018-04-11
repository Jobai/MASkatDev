package de.skat3.gamelogic;

import javafx.embed.swing.JFXPanel;

public class CardDeck {

  private Card[] cards = new Card[Suit.length * Value.length];

  public CardDeck() {

    int i = 0;
    for (Suit s : Suit.values()) {
      for (Value v : Value.values()) {
        cards[i++] = new Card(s, v);
      }
    }
  }

  public Card[] getCards() {
    return this.cards;
  }

  Card getCard(String s) {
    for (Card c : this.cards) {
      if (c.toString().equals(s)) {
        return c;
      }
    }
    System.err.println("Ungueltige Karte, " + s);
    return null;
  }

}
