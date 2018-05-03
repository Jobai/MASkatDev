package de.skat3.ai;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import de.skat3.gamelogic.AdditionalMultipliers;
import de.skat3.gamelogic.Card;
import de.skat3.gamelogic.Contract;
import de.skat3.gamelogic.Suit;
import de.skat3.gamelogic.Value;

/**
 * 
 * @author emrecura
 *
 */
public class AiHelper implements Serializable {

  /**
   * Gets the suit with the most Cards in the CardsDeck (without considering the jacks!), without
   * considering the given suit
   *
   * @param exclude suit to exclude from calculating the most frequent suit (normally the trump
   *        suit)
   * @return Suit with most Cards in the CardList,<br>
   *         the highest ranking suit, if there the highest count gives more than one suit
   */
  public Suit getMostFrequentSuit(Card[] cards, Suit exclude) {
    int diamondsCount = 0;
    int heartsCount = 0;
    int spadesCount = 0;
    int clubsCount = 0;

    for (Suit suit : Suit.values()) {
      if (suit == exclude) {
        continue;
      } else {
        switch (suit) {
          case DIAMONDS:
            diamondsCount++;
            break;
          case HEARTS:
            heartsCount++;
            break;
          case SPADES:
            spadesCount++;
            break;
          case CLUBS:
            clubsCount++;
            break;
        }
      }
    }

    int biggestCount =
        Math.max(Math.max(diamondsCount, heartsCount), Math.max(spadesCount, clubsCount));

    if (diamondsCount == biggestCount) {
      return Suit.DIAMONDS;
    } else if (heartsCount == biggestCount) {
      return Suit.HEARTS;
    } else if (spadesCount == biggestCount) {
      return Suit.SPADES;
    } else {
      return Suit.CLUBS;
    }
  }

  /**
   * Gets the suit with the most Cards in the CardList (without considering the jacks!)
   *
   * @return Suit with most Cards in the CardList,<br>
   *         the highest ranking suit, if there the highest count gives more than one suit
   */
  public Suit getMostFrequentSuit(Card[] cards) {
    return getMostFrequentSuit(cards, null);
  }

  /**
   * Returns the first index of a card with the given suit.
   *
   * @param suit Suit to search
   * @param includeJacks flag whether to include jacks in the result
   * @return First index of a card with the given suit, -1 if there is no such card
   */
  public int getFirstIndexOfSuit(Card[] cards, Suit suit, boolean includeJacks) {
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
  public int getFirstIndexOfSuit(Card[] cards, Suit suit) {
    return getFirstIndexOfSuit(cards, suit, true);
  }

  /**
   * Returns the last index of a card with the given suit (including jacks!)
   *
   * @param suit Suit to search
   * @return Last index of a card with the given suit, -1 if there is no such card
   */
  public int getLastIndexOfSuit(Card[] cards, Suit suit) {
    return getLastIndexOfSuit(cards, suit, true);
  }

  /**
   * Returns the last index of a card with the given suit
   *
   * @param suit Suit to search
   * @param includeJacks flag whether to include jacks in the result
   * @return Last index of a card with the given suit, -1 if there is no such card
   */
  public int getLastIndexOfSuit(Card[] cards, Suit suit, boolean includeJacks) {
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
  public int countJacks(Card[] cards) {
    return getAllCardsOfValue(cards, Value.JACK).size();
  }


  /**
   * Gets the number of trumps in the given hand
   * 
   * @param cards a hand
   * @return number of trumps
   */
  public int countTrumps(Card[] cards, Contract contract) {
    int counter = 0;
    for (int i = 0; i < cards.length; i++) {
      if (cards[i].isTrump(contract)) {
        counter++;
      }

    }
    return counter;
  }

  /**
   * Gets the number of aces in the given hand
   * 
   * @param cards a hand
   * @return number of aces
   */
  public int countAces(Card[] cards) {
    return getAllCardsOfValue(cards, Value.ACE).size();
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
  private int[] calculateSuits(Card[] cards) {
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

  public int getAmountOfCardsWithSameSuit(Card card, Card[] cards) {
    int[] suitArray = calculateSuits(cards);
    switch (card.getSuit()) {
      case DIAMONDS:
        return suitArray[0];

      case HEARTS:
        return suitArray[1];

      case SPADES:
        return suitArray[2];

      default:
        return suitArray[3];
    }
  }

  public ArrayList<Card> getAllCardsOfValue(Card[] cards, Value value) {
    ArrayList<Card> jacksList = new ArrayList<Card>();
    for (Card card : cards) {
      if (card.getValue() == value) {
        jacksList.add(card);
      }
    }
    return jacksList;
  }

  public ArrayList<Card> getAllCardsOfSuit(Card[] cards, Suit suit) {
    ArrayList<Card> suitList = new ArrayList<Card>();
    for (int i = 0; i < cards.length; i++) {
      if (cards[i].getSuit() == suit) {
        suitList.add(cards[i]);
      }
    }
    return suitList;
  }

  public Card getHighestCard(ArrayList<Card> cardsList) {
    Iterator<Card> iter = cardsList.iterator();
    Card highestCard = iter.next();
    while (iter.hasNext()) {
      Card current = iter.next();
      if (current.getValue().ordinal() > highestCard.getValue().ordinal()) {
        highestCard = current;
      }
    }

    return highestCard;
  }

  public Card getLowestCard(ArrayList<Card> cardsList) {
    Iterator<Card> iter = cardsList.iterator();
    Card lowestCard = iter.next();
    while (iter.hasNext()) {
      Card current = iter.next();
      if (current.getValue().ordinal() < lowestCard.getValue().ordinal()) {
        lowestCard = current;
      }
    }

    return lowestCard;
  }

  public Card getAnyPlayableCard(Card[] cards) {
    for (Card card : cards) {
      if (card.isPlayable()) {
        return card;
      }
    }
    return null;
  }

}
