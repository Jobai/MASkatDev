package ai;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

import org.junit.Test;

import de.skat3.ai.IntelligentAi;
import de.skat3.ai.ReturnSkat;
import de.skat3.gamelogic.Card;
import de.skat3.gamelogic.Contract;
import de.skat3.gamelogic.Hand;
import de.skat3.gamelogic.Suit;
import de.skat3.gamelogic.Value;

public class TestIntelligentAI {

  @Test
  public void test() {
    Random random = new Random();

    int contractLength = Contract.values().length;
    int suitLength = Suit.values().length;
    int valueLength = Value.values().length;

    int contractOrdinal;
    int suitOrdinal;
    int valueOrdinal;

		IntelligentAi ai = new IntelligentAi();

    for (int j = 0; j < 5; j++) {
      contractOrdinal = random.nextInt(contractLength);
      Contract contract = Contract.values()[contractOrdinal];

      HashSet<Card> cardSet = new HashSet<Card>();

      // full cardSet with 10 unique Cards
      while (cardSet.size() < 10) {
        suitOrdinal = random.nextInt(suitLength);
        Suit suit = Suit.values()[suitOrdinal];

        valueOrdinal = random.nextInt(valueLength);
        Value value = Value.values()[valueOrdinal];

        Card card = new Card(suit, value);
        cardSet.add(card);
      }

      // put 10 unique Cards from set to array
      Iterator<Card> iter = cardSet.iterator();
      Card[] cardArray = new Card[10];
      int i = 0;
      while (iter.hasNext()) {
        cardArray[i] = iter.next();
      }

      Hand hand = new Hand(cardArray);
      ai.getPlayer().setHand(hand);

      // check if played Card contains in a set
      Card aiChosenCard = ai.chooseCard();
      assertTrue(cardSet.contains(aiChosenCard));
    }
    // Contract contractDiamonds = (Contract.DIAMONDS);
    // Contract contractHearts = (Contract.HEARTS);
    // Contract contractSpades = (Contract.SPADES);
    // Contract contractClubs = (Contract.CLUBS);
    // Contract contractGrand = (Contract.GRAND);
    // Contract contractNull = (Contract.NULL);

  }

	@Test
	public void testSelectSkat() {
		IntelligentAi ai = new IntelligentAi();
		Card card1 = new Card(Suit.CLUBS, Value.JACK);
		Card card2 = new Card(Suit.CLUBS, Value.ACE);
		Card[] skat = { card1, card2 };
		assertEquals(ai.selectSkat(skat), new ReturnSkat(ai.hand, skat));
	}

	@Test
	public void testChooseContract() {
		IntelligentAi ai = new IntelligentAi();
		assertEquals(ai.chooseContract(), Contract.GRAND);
	}

	@Test
	public void testBidding() {
		IntelligentAi ai = new IntelligentAi();
		int bid = 18;
		assertTrue(ai.acceptBid(bid));
	}

}
