package de.skat3.gui.matchfield;

import java.util.ArrayList;
import de.skat3.gamelogic.Card;
import de.skat3.gamelogic.CardDeck;

public class GuiCardDeck {
  private static ArrayList<GuiCard> front = new ArrayList<GuiCard>();
  private static ArrayList<GuiCard> back = new ArrayList<GuiCard>();
  private static int index = 0;

  public static void ini() {
    CardDeck d = new CardDeck();
    for (Card c : d.getCards()) {
      front.add(new GuiCard(c, true));
    }
    for (int i = 0; i < 40; i++) {
      back.add(new GuiCard(new Card(), true));
    }

  }


  public static GuiCard getCard(Card card) {
    for (GuiCard c : front) {
      System.out.print(c);
      System.out.println();
    }

    try {
      if (card.equals(new Card())) {
        index++;
        if (index > 40) {
          index = 0;
        }
        return back.get(index);
      }
    } catch (Exception e1) {

    }

    for (GuiCard c : front) {
      System.out.print(c);
      System.out.println();
      try {
        if (c.getCard().equals(card)) {
          return c;
        }
      } catch (Exception e) {

      }
    }

    index++;
    if (index > 40) {
      index = 0;
    }
    return back.get(index);
  }
}
