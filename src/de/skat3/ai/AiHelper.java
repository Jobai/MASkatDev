package de.skat3.ai;

import de.skat3.gamelogic.AdditionalMultipliers;
import de.skat3.gamelogic.Card;
import de.skat3.gamelogic.Contract;
import de.skat3.gamelogic.Player;
import de.skat3.gamelogic.Suit;
import de.skat3.gamelogic.Value;

/**
 * 
 * @author emrecura
 *
 */
public class AiHelper {

  Card[] cards;

  public AiHelper(Player player) {
    cards = player.getHand().getCards();
  }

  /**
   * Gets the suit with the most Cards in the CardsDeck (without considering the jacks!), without
   * considering the given suit
   *
   * @param exclude suit to exclude from calculating the most frequent suit (normally the trump
   *        suit)
   * @return Suit with most Cards in the CardList,<br>
   *         the highest ranking suit, if there the highest count gives more than one suit
   */
  public Suit getMostFrequentSuit(final Suit exclude) {

    int maxCount = 0;
    Suit mostFrequentSuitColor = null;
    for (Suit suit : Suit.values()) {
      if (exclude != null && suit == exclude) {
        continue;
      }
      int cardCount = Suit.length;
      if (cardCount > maxCount) {
        mostFrequentSuitColor = suit;
        maxCount = cardCount;
      }
    }

    return mostFrequentSuitColor;
  }

  /**
   * Gets the suit with the most Cards in the CardList (without considering the jacks!)
   *
   * @return Suit with most Cards in the CardList,<br>
   *         the highest ranking suit, if there the highest count gives more than one suit
   */
  public Suit getMostFrequentSuit() {
    return getMostFrequentSuit(null);
  }

  /**
   * Returns the first index of a card with the given suit.
   *
   * @param suit Suit to search
   * @param includeJacks flag whether to include jacks in the result
   * @return First index of a card with the given suit, -1 if there is no such card
   */
  public int getFirstIndexOfSuit(final Suit suit, final boolean includeJacks) {
    int result = -1;
    int index = 0;
    for (int i = 0; i < cards.length; i++) {
      if (result == -1 && cards[i].getSuit() == suit) {
        if (cards[i].getValue() != Value.JACK
            || cards[i].getValue() == Value.JACK && includeJacks) {
          result = index;
        }
      }
      index++;
    }
    return result;
  }

  /**
   * Returns the first index of a card with the given suit (including jacks!)
   *
   * @param suit Suit to search
   * @return First index of a card with the given suit, -1 if there is no such card
   */
  public int getFirstIndexOfSuit(final Suit suit) {

    return getFirstIndexOfSuit(suit, true);
  }

  /**
   * Returns the last index of a card with the given suit (including jacks!)
   *
   * @param suit Suit to search
   * @return Last index of a card with the given suit, -1 if there is no such card
   */
  public int getLastIndexOfSuit(final Suit suit) {

    return getLastIndexOfSuit(suit, true);
  }

  /**
   * Returns the last index of a card with the given suit
   *
   * @param suit Suit to search
   * @param includeJacks flag whether to include jacks in the result
   * @return Last index of a card with the given suit, -1 if there is no such card
   */
  public int getLastIndexOfSuit(final Suit suit, final boolean includeJacks) {

    int result = -1;
    int index = 0;
    for (int i = 0; i < cards.length; i++) {
      if (result == -1 && cards[i].getSuit() == suit) {
        if (cards[i].getValue() != Value.JACK
            || cards[i].getValue() == Value.JACK && includeJacks) {
          result = index;
        }
      }
      index++;
    }
    return result;
  }

  /**
   * Gets the number of jacks in the given hand
   * 
   * @param cards a hand
   * @return number of jacks
   */
  public static int countJacks(Card[] cards) {
    int counter = 0;
    for (int i = 0; i < cards.length; i++) {
      if (cards[i].getValue() == Value.JACK) {
        counter++;
      }
    }
    return counter;

  }

  /**
   * Gets the number of trumps in the given hand
   * 
   * @param cards a hand
   * @return number of trumps
   */
  public static int countTrumps(Card[] cards, Contract contract) {
    int counter = 0;
    for (int i = 0; i < cards.length; i++) {
      if (cards[i].isTrump(contract)) {
        counter++;
      }

    }
    return counter;
  }

  /**
   * Gets the number of jacks in the given hand
   * 
   * @param cards a hand
   * @return number of jacks
   */
  public static int countAces(Card[] cards) {
    int counter = 0;
    for (int i = 0; i < cards.length; i++) {
      if (cards[i].getValue() == Value.ACE) {
        counter++;
      }
    }
    return counter;
  }

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

  /**
   * 
   * @return return an array of ints int[0] = anzahl der DIAMONDS aus cards int[1] = anzahl der
   *         HEARTS int[2] = anzahl der SPADES int[3] = anzahl der CLUBS
   * 
   */
  private int[] calculateSuitsInHand() {
    int[] suitArray = {0, 0, 0, 0};

    for (int i = 0; i < cards.length; i++) {
      switch (cards[i].getSuit()) {
        case DIAMONDS:
          suitArray[0]++;
          break;

        case HEARTS:
          suitArray[1]++;
          break;

        case SPADES:
          suitArray[2]++;
          break;

        case CLUBS:
          suitArray[3]++;
          break;
      }
    }
    return suitArray;
  }

  public int getAmountOfSuitsByCardSuit(Card card) {
    int[] suitArray = calculateSuitsInHand();
    switch (card.getSuit()) {
      case DIAMONDS:
        return suitArray[0];

      case HEARTS:
        return suitArray[1];

      case SPADES:
        return suitArray[2];

      // es kann nicht anderes sein aks CLUBS
      default:
        return suitArray[3];
    }
  }
}
