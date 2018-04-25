package ai;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import de.skat3.ai.RandomAI;
import de.skat3.gamelogic.AdditionalMultipliers;
import de.skat3.gamelogic.Player;
import de.skat3.io.profile.Profile;

public class TestRandomIAI {

  @Test
  public void testAdditionalMultipliers() {
    for (int i = 0; i < 20; i++) {
      Profile profile = new Profile("testUser");
      Player player = new Player(profile);
      RandomAI ai = new RandomAI(player);

      boolean handGameAccepted = ai.acceptHandGame();
      AdditionalMultipliers multiplier = ai.chooseAdditionalMultipliers();

      if (handGameAccepted) {
        AdditionalMultipliers openHandMultiplier = new AdditionalMultipliers();
        openHandMultiplier.setHandGame(true);
        assertEquals(multiplier.isHandGame(), openHandMultiplier.isHandGame());
        assertEquals(multiplier.isOpenHand(), openHandMultiplier.isOpenHand());
        assertEquals(multiplier.isSchneiderAnnounced(), openHandMultiplier.isSchneiderAnnounced());
        assertEquals(multiplier.isSchwarzAnnounced(), openHandMultiplier.isSchwarzAnnounced());
      } else {
        AdditionalMultipliers[] multipliersArray = ai.getAllPossibleMultipliers(false);
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
}
