package de.skat3.gamelogic;

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

  public static void main(String[] args) {
    CardDeck a = new CardDeck();
    for (Card c : a.cards) {
      System.out.println(c.toString());
    }

  }
}
