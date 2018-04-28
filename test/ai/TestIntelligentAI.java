package ai;

import static org.junit.Assert.assertTrue;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import org.junit.Test;
import de.skat3.ai.IntelligentAI;
import de.skat3.gamelogic.Card;
import de.skat3.gamelogic.Contract;
import de.skat3.gamelogic.Hand;
import de.skat3.gamelogic.Player;
import de.skat3.gamelogic.Suit;
import de.skat3.gamelogic.Value;
import de.skat3.io.profile.Profile;

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

    IntelligentAI ai = new IntelligentAI(new Player(new Profile("testUser")));

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

}
