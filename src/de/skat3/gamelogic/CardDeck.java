package de.skat3.gamelogic;


/*
 * Represents all 32 cards that exist in a skat game.
 */
public class CardDeck {

  private Card[] cards;

  /**
   * Fills the deck with all 32 cards.
   */
  public CardDeck() {
    cards = new Card[Suit.length * Value.length];
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

  /**
   * Returns card from the deck.
   * 
   * @param s "VALUE OF SUIT"
   * @return Card
   */
  public Card getCard(String s) {
    for (Card c : this.cards) {
      if (c.toString().equals(s)) {
        return c;
      }
    }
    System.err.println("Ungueltige Karte, " + s);
    return null;
  }

}
