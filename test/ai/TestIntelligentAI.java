package ai;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import de.skat3.ai.IntelligentAi;
import de.skat3.gamelogic.Card;
import de.skat3.gamelogic.Contract;
import de.skat3.gamelogic.Hand;
import de.skat3.gamelogic.Suit;
import de.skat3.gamelogic.Value;

public class TestIntelligentAI {

  Hand handAi;
  IntelligentAi ai;

  @Before
  public void setUp() {

    Card card1 = new Card(Suit.CLUBS, Value.ACE);
    Card card2 = new Card(Suit.CLUBS, Value.TEN);
    Card card3 = new Card(Suit.CLUBS, Value.KING);
    Card card4 = new Card(Suit.SPADES, Value.ACE);
    Card card5 = new Card(Suit.SPADES, Value.TEN);
    Card card6 = new Card(Suit.SPADES, Value.KING);
    Card card7 = new Card(Suit.SPADES, Value.QUEEN);
    Card card8 = new Card(Suit.SPADES, Value.EIGHT);
    Card card9 = new Card(Suit.SPADES, Value.NINE);
    Card card10 = new Card(Suit.HEARTS, Value.ACE);

    Card[] cards = {card1, card2, card3, card4, card5, card6, card7, card8, card9, card10};
    handAi = new Hand(cards);
  }

  // TODO look at selectSkat
  @Test
  public void testSelectSkat() throws Exception {
    IntelligentAi ai = new IntelligentAi();
    ai.setHand(handAi);
    // to include in IntelligentAi before testing
    // ai.setContractTest(Contract.CLUBS);

    Card card1 = new Card(Suit.CLUBS, Value.JACK);
    Card card2 = new Card(Suit.CLUBS, Value.ACE);
    Card[] skat = {card1, card2};

    Card card3 = new Card(Suit.SPADES, Value.EIGHT);
    Card card4 = new Card(Suit.SPADES, Value.NINE);
    Card[] testSkat = {card3, card4};

    Card[] skatExpected = ai.selectSkat(skat).getSkat();

    boolean equals1 = testSkat[0].equals(skatExpected[0]);
    boolean equals2 = skatExpected[1].equals(testSkat[1]);

    assertTrue(equals1);
    assertTrue(equals2);
  }

  // @Test
  public void testChooseContract() {
    IntelligentAi ai = new IntelligentAi();
    ai.setHand(handAi);
    assertEquals(ai.chooseContract(), Contract.SPADES);

  }

  // @Test
  public void testBidding() {
    IntelligentAi ai = new IntelligentAi();
    ai.setHand(handAi);
    int bid = 18;
    assertTrue(ai.acceptBid(bid));
  }

  // @Test
  public void testHandGame() {
    IntelligentAi ai = new IntelligentAi();
    ai.setHand(handAi);
    assertTrue(ai.acceptHandGame());
  }

}
