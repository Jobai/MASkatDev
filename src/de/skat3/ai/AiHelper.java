package de.skat3.ai;

import java.io.Serializable;
import de.skat3.gamelogic.AdditionalMultipliers;

/**
 * 
 * @author emrecura
 *
 */
public class AiHelper implements Serializable {

  /**
   * @param acceptedHandGame Tells if a hand game was accepted
   * 
   * @return if a handGame was accepted - returns array consisting of only one element -
   *         multiplierOpen else retuns an array consisting of 4 elements : noMultipliers,
   *         multiplierSchneider, multiplierSchwarz, multiplierHand
   * 
   */
  public AdditionalMultipliers[] getAllPossibleMultipliers(boolean acceptedHandGame) {
    if (!acceptedHandGame) {
      AdditionalMultipliers noHandGameNoMultipliers = new AdditionalMultipliers();
      AdditionalMultipliers[] multiplierOpenArray = new AdditionalMultipliers[1];
      multiplierOpenArray[0] = noHandGameNoMultipliers;

      return multiplierOpenArray;
    } else {
      AdditionalMultipliers handGameNoMultipliers = new AdditionalMultipliers();
      handGameNoMultipliers.setHandGame(true);

      AdditionalMultipliers handGameMultiplierSchneider =
          new AdditionalMultipliers(true, false, false);
      handGameMultiplierSchneider.setHandGame(true);

      AdditionalMultipliers handGameMultiplierSchwarz =
          new AdditionalMultipliers(true, true, false);
      handGameMultiplierSchwarz.setHandGame(true);

      AdditionalMultipliers handGameMultiplierOpenHand =
          new AdditionalMultipliers(true, true, true);
      handGameMultiplierOpenHand.setHandGame(true);

      AdditionalMultipliers[] multiplayers = new AdditionalMultipliers[4];

      multiplayers[0] = handGameNoMultipliers;
      multiplayers[1] = handGameMultiplierSchneider;
      multiplayers[2] = handGameMultiplierSchwarz;
      multiplayers[3] = handGameMultiplierOpenHand;

      return multiplayers;
    }
  }

}