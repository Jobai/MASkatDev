package ai;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import org.junit.Test;
import de.skat3.ai.AiHelper;
import de.skat3.ai.IntelligentAi;
import de.skat3.ai.RandomAI;
import de.skat3.gamelogic.AdditionalMultipliers;
import de.skat3.gamelogic.Card;
import de.skat3.gamelogic.Contract;
import de.skat3.gamelogic.Hand;
import de.skat3.gamelogic.Player;
import de.skat3.gamelogic.Suit;
import de.skat3.gamelogic.Value;
import de.skat3.io.profile.Profile;

public class TestRandomIAI {
  AiHelper aiHelper = new AiHelper();

  @Test
  public void testAdditionalMultipliers() {
    for (int i = 0; i < 20; i++) {
      Profile profile = new Profile("testUser");
      Player player = new Player(profile);
      RandomAI ai = new RandomAI();

      boolean handGameAccepted = ai.acceptHandGame();
      AdditionalMultipliers multiplier = ai.chooseAdditionalMultipliers();

      if (!handGameAccepted) {
        AdditionalMultipliers noHandGameNoMultipliers = new AdditionalMultipliers();
        assertEquals(multiplier.isHandGame(), noHandGameNoMultipliers.isHandGame());
        assertEquals(multiplier.isOpenHand(), noHandGameNoMultipliers.isOpenHand());
        assertEquals(multiplier.isSchneiderAnnounced(),
            noHandGameNoMultipliers.isSchneiderAnnounced());
        assertEquals(multiplier.isSchwarzAnnounced(), noHandGameNoMultipliers.isSchwarzAnnounced());
      } else {
        AdditionalMultipliers[] multipliersArray = aiHelper.getAllPossibleMultipliers(true);
        boolean contains = false;
        for (int j = 0; j < multipliersArray.length; j++) {
          if (multipliersArray[j].isHandGame() == multiplier.isHandGame()
              && multipliersArray[j].isOpenHand() == multiplier.isOpenHand()
              && multipliersArray[j].isSchneiderAnnounced() == multiplier.isSchneiderAnnounced()
              && multipliersArray[j].isSchwarzAnnounced() == multiplier.isSchwarzAnnounced()) {
            contains = true;
          }
        }
        assertTrue(contains);
      }
    }
  }

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
      // ai.getPlayer().setHand(hand);

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
