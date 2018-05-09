package de.skat3.ai;

import java.io.Serializable;
import de.skat3.gamelogic.AdditionalMultipliers;

/**
 * 
 * @author Emre Cura, Artem Zamarajev
 *
 */
public class AiHelper implements Serializable {

  AdditionalMultipliers[] allMultipliers = createAllAdditionalMultipliers();

  /**
   * Creates all possible multipliers that can be set in the game.
   * 
   * @return Instance of Additional multipliers.
   */
  private AdditionalMultipliers[] createAllAdditionalMultipliers() {
    AdditionalMultipliers[] allMultipliers = new AdditionalMultipliers[8];

    // NO MULTIPLIERS

    AdditionalMultipliers noHandGameNoMultipliers = new AdditionalMultipliers();

    // NOT NULL
    AdditionalMultipliers handGameNoAdditionalMultipliers = new AdditionalMultipliers();
    handGameNoAdditionalMultipliers.setHandGame(true);

    AdditionalMultipliers handGameMultiplierSchneider =
        new AdditionalMultipliers(true, false, false);
    handGameMultiplierSchneider.setHandGame(true);

    AdditionalMultipliers handGameMultiplierSchwarz = new AdditionalMultipliers(true, true, false);
    handGameMultiplierSchwarz.setHandGame(true);

    AdditionalMultipliers handGameMultiplierOpenHand = new AdditionalMultipliers(true, true, true);
    handGameMultiplierOpenHand.setHandGame(true);

    // NULL

    AdditionalMultipliers handGameNullHandMultiplier = new AdditionalMultipliers();
    handGameMultiplierOpenHand.setHandGame(true);

    AdditionalMultipliers noHandGameNullOuvert = new AdditionalMultipliers(false, false, true);

    AdditionalMultipliers handGameNullOuvertHand = new AdditionalMultipliers(false, false, true);
    handGameNullOuvertHand.setHandGame(true);


    allMultipliers[0] = noHandGameNoMultipliers;
    allMultipliers[1] = handGameNoAdditionalMultipliers;
    allMultipliers[2] = handGameMultiplierSchneider;
    allMultipliers[3] = handGameMultiplierSchwarz;
    allMultipliers[4] = handGameMultiplierOpenHand;
    allMultipliers[5] = handGameNullHandMultiplier;
    allMultipliers[6] = noHandGameNullOuvert;
    allMultipliers[7] = handGameNullOuvertHand;

    return allMultipliers;
  }

  /**
   * Gives all possible Multipliers back.
   * 
   * @param acceptedHandGame
   * 
   *        The parameter tells if hand game was accepted
   * 
   * @return Instance of Additional multipliers.
   */
  public AdditionalMultipliers[] getAllPossibleMultipliers(boolean acceptedHandGame) {
    if (acceptedHandGame) {
      AdditionalMultipliers[] allMultipliersNotNull = new AdditionalMultipliers[5];
      allMultipliersNotNull[0] = allMultipliers[0];
      allMultipliersNotNull[1] = allMultipliers[1];
      allMultipliersNotNull[2] = allMultipliers[2];
      allMultipliersNotNull[3] = allMultipliers[3];
      allMultipliersNotNull[4] = allMultipliers[4];
      return allMultipliersNotNull;
    } else {
      AdditionalMultipliers[] allMultipliersNull = new AdditionalMultipliers[4];
      allMultipliersNull[0] = allMultipliers[0];
      allMultipliersNull[1] = allMultipliers[5];
      allMultipliersNull[2] = allMultipliers[6];
      allMultipliersNull[0] = allMultipliers[7];
      return allMultipliersNull;
    }

  }

  public AdditionalMultipliers getNoHandGameNoMultipliers() {
    return allMultipliers[0];
  }

  public AdditionalMultipliers getHandGameNoMultipliers() {
    return allMultipliers[1];
  }

  public AdditionalMultipliers getSchneider() {
    return allMultipliers[2];
  }

  public AdditionalMultipliers getSchwarz() {
    return allMultipliers[3];
  }

  public AdditionalMultipliers getOpenHand() {
    return allMultipliers[4];
  }

  public AdditionalMultipliers getNullHand() {
    return allMultipliers[5];
  }

  public AdditionalMultipliers getNullOuvert() {
    return allMultipliers[6];
  }

  public AdditionalMultipliers getNullOuvertHand() {
    return allMultipliers[7];
  }


}


