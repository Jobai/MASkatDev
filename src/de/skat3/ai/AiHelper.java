package de.skat3.ai;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import de.skat3.gamelogic.AdditionalMultipliers;
import de.skat3.gamelogic.Card;
import de.skat3.gamelogic.Contract;
import de.skat3.gamelogic.Position;
import de.skat3.gamelogic.Suit;
import de.skat3.gamelogic.Value;
import de.skat3.main.LocalGameState;

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
    return getAllPlayableCardsOfValue(cards, Value.JACK).size();
  }


  /**
   * Gets the number of trumps in the given hand
   * 
   * @param cards a hand
   * @return number of trumps
   */
  public int countTrumps(Card[] cards, Contract contract) {
    int counter = 0;
    for (Card card : cards) {
      if (card.isTrump(contract)) {
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
    return getAllPlayableCardsOfValue(cards, Value.ACE).size();
  }
  
  /**
   * Gets the number of Nines in the given hand
   * 
   * @param cards a hand
   * @return number of aces
   */
  public int countNines(Card[] cards) {
    return getAllCardsOfValue(cards, Value.NINE).size();
  }
  /**
   * Gets the number of Eights in the given hand
   * 
   * @param cards a hand
   * @return number of aces
   */
  public int countEights(Card[] cards) {
    return getAllCardsOfValue(cards, Value.EIGHT).size();
  }
  /**
   * Gets the number of Sevens in the given hand
   * 
   * @param cards a hand
   * @return number of aces
   */
  public int countSevens(Card[] cards) {
    return getAllCardsOfValue(cards, Value.SEVEN).size();
  }
  /**
   * Gets the number of Tens in the given hand
   * 
   * @param cards a hand
   * @return number of Tens
   */
  public int countTens(Card[] cards) {
    return getAllCardsOfValue(cards, Value.TEN).size();
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


  public Card getHighestCard(Card[] cards, Contract contract) {

    return new Card();
  }

  private Card getHighestCard(Card[] cards) {
    Card output = null;
    for ( Card card : cards) {
      if (output == null || card.getTrickValue() > output.getTrickValue()){
    nullnullnull;
      }
    }
    return output;
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

  public ArrayList<Card> getAllPlayableCardsOfValue(Card[] cards, Value value) {
    ArrayList<Card> jacksList = new ArrayList<Card>();
    for (Card card : cards) {
      if (card.getValue() == value && card.isPlayable()) {
        jacksList.add(card);
      }
    }
    return jacksList;
  }

  public ArrayList<Card> getAllPlayableCardsOfSuit(Card[] cards, Suit suit) {
    ArrayList<Card> suitList = new ArrayList<Card>();
    for (Card card : cards) {
      if (card.getSuit() == suit && card.isPlayable()) {
        suitList.add(card);
      }
    }
    return suitList;
  }

  public Card getHighestPlayableCardExcludeSuitIfPossible(ArrayList<Card> cardsList,
      Contract contract) {
    Iterator<Card> iter = cardsList.iterator();
    Suit exclude = convertContractToTrump(contract);
    Card highestCard = null;
    Card highestCardWithoutExcludedSuit = null;
    // get first highestCard
    while (iter.hasNext()) {
      Card current = iter.next();
      if ((highestCard == null || current.getTrickValue() > highestCard.getTrickValue())
          && current.isPlayable()) {
        highestCard = current;
      }
      if ((highestCard == null || current.getTrickValue() > highestCard.getTrickValue())
          && current.isPlayable() && !(current.getSuit() != exclude)) {
        highestCardWithoutExcludedSuit = current;
      }
    }

    if (highestCardWithoutExcludedSuit != null) {
      return highestCardWithoutExcludedSuit;
    } else {
      return highestCard;
    }
  }

  public Card getLowestPlayableCardExcludeSuitIfPossible(ArrayList<Card> cardsList,
      Contract contract) {
    Iterator<Card> iter = cardsList.iterator();
    Suit exclude = convertContractToTrump(contract);
    Card lowestCard = null;
    Card lowestCardWithoutExcludedSuit = null;
    // get first lowestCard
    while (iter.hasNext()) {
      Card current = iter.next();
      if ((lowestCard == null || current.getTrickValue() < lowestCard.getTrickValue())
          && current.isPlayable()) {
        lowestCard = current;
      }
      if ((lowestCard == null || current.getTrickValue() < lowestCard.getTrickValue())
          && current.isPlayable() && !(current.getSuit() != exclude)) {
        lowestCardWithoutExcludedSuit = current;
      }
    }

    if (lowestCardWithoutExcludedSuit != null) {
      return lowestCardWithoutExcludedSuit;
    } else {
      return lowestCard;
    }
  }


  public Card getAnyPlayableCard(Card[] cards) {
    for (Card card : cards) {
      if (card.isPlayable()) {
        return card;
      }
    }
    return null;
  }

	public boolean checkGrand(Card[] cards) {
		int noOfJacks = countJacks(cards);
		int noOfAces = countAces(cards);
		int noOfTens = countTens(cards);
		boolean grand = false;

		if (noOfJacks >= 2 && noOfAces >= 3) {
			grand = true;
		} else if (noOfJacks >= 1 && noOfAces > 2 && noOfTens > 2) {
			grand = true;
		}
		return grand;
	}

	public boolean checkNull(Card[] cards) {
		int noOfAces = countAces(cards);
		int noOfTens = countTens(cards);
		int noOfLusche = countSevens(cards) + countEights(cards) + countNines(cards);
		boolean nullB = false;

		if (noOfLusche >= 5) {
			nullB = true;
		} else if (noOfLusche >= 3 && noOfAces == 0 && noOfTens == 0) {
			nullB = true;
		}
		return nullB;
	}

	public boolean checkSuit(Card[] cards,Suit suit) {
		int noOfAces = countAces(cards);
		int noOfTens = countTens(cards);
		int noOfLusche = countSevens(cards) + countEights(cards) + countNines(cards);
		boolean suites = false;

		int noOfTrumpsSpades = countTrumps(cards, Contract.SPADES);
		if ((noOfTrumpsSpades >= 5)) {
			suites = true;
		} else if (noOfTrumpsSpades >= 3 && noOfAces >= 3) {
			suites = true;
		} else if (noOfTrumpsSpades >= 2 && noOfAces >= 3 && noOfTens >= 3) {
			suites = true;
		}
	
		int noOfTrumpsDiamonds = countTrumps(cards, Contract.DIAMONDS);
		if ((noOfTrumpsDiamonds >= 5)) {
			suites = true;
		} else if (noOfTrumpsDiamonds >= 3 && noOfAces >= 3) {
			suites = true;
		} else if (noOfTrumpsDiamonds >= 2 && noOfAces >= 3 && noOfTens >= 3) {
			suites = true;
		}
	
		int noOfTrumpsHearts = countTrumps(cards, Contract.HEARTS);
		if ((noOfTrumpsHearts >= 5)) {
			suites = true;
		} else if( noOfTrumpsHearts >= 3 && noOfAces >= 3) {
			suites = true;
		} else if (noOfTrumpsHearts>= 2 && noOfAces >= 3 && noOfTens >= 3) {
			suites = true;
		}

	int noOfTrumpsClubs = countTrumps(cards, Contract.CLUBS);

		if ((noOfTrumpsClubs >= 5)) {
			suites = true;
		} else if (noOfTrumpsClubs >= 3 && noOfAces >= 3) {
			suites = true;
		} else if (noOfTrumpsClubs >= 2 && noOfAces >= 3 && noOfTens >= 3) {
			suites = true;
		}
		return suites;
	}
  /**
   * Position == REARHAND!!!!!!!
   *
   * 
   **/
  public boolean willTrickBeWonByTeammate(LocalGameState lgs, Card[] cards, Position myPosition,
      boolean isSolo) {
    Contract contract = lgs.getContract();
    Card[] trick = lgs.getTrick();
    if (isSolo) {
      return false;
    } else {
      Position teammatesPosition = getTeammatePosition(lgs, myPosition);
      if (teammatesPosition == Position.FOREHAND) {
        if (trick[0].beats(contract, trick[1])) {
          return true;
        } else {
          return false;
        }
      } else if (teammatesPosition == Position.MIDDLEHAND) {
        if (trick[1].beats(contract, trick[03])) {
          return true;
        } else {
          return false;
        }
      }
    }

    return false;
  }

  private Position getSoloPosition(LocalGameState lgs) {
    if (lgs.getEnemyOne().isSolo()) {
      return lgs.getEnemyOne().getPosition();
    } else if (lgs.getEnemyTwo().isSolo()) {
      return lgs.getEnemyTwo().getPosition();
    } else {
      return lgs.getLocalClient().getPosition();
    }
  }


  /**
   * 
   * !!!!! Only call if you are not solo!!!!! !!!!! Only call if your position is MIDDLEHAND or
   * REARHAND !!!!!! returns position of my teammate
   * 
   * @param lgs
   * @param myPosition
   * @return position of my teammate
   */
  public Position getTeammatePosition(LocalGameState lgs, Position myPosition) {
    Position teamMatePosition = null;
    if (myPosition == Position.MIDDLEHAND) {
      if (getSoloPosition(lgs) == Position.REARHAND) {
        teamMatePosition = Position.FOREHAND;
      } else if (getSoloPosition(lgs) == Position.FOREHAND) {
        teamMatePosition = Position.REARHAND;
      }
    } else if (myPosition == Position.REARHAND) {
      if (getSoloPosition(lgs) == Position.MIDDLEHAND) {
        teamMatePosition = Position.FOREHAND;
      } else if (getSoloPosition(lgs) == Position.FOREHAND) {
        teamMatePosition = Position.MIDDLEHAND;
      }
    }
    return teamMatePosition;
  }

  public boolean isFirstCardInTrickFromTeammate(LocalGameState lgs, Position myPosition) {
    if (getTeammatePosition(lgs, myPosition) == Position.FOREHAND) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Returns the Most Expensive Card That Is Not Trump If there is such
   *
   * @param
   * 
   * @return if contract == suit -> return highest card not trump
   * @return if contract == grand -> return highest card
   * @return if contract == null -> return
   * 
   */
  public Card playMostExpensiveCardThatIsNotTrumpIfPossible(Card[] cards, Contract contract) {
    ArrayList<Card> list = new ArrayList<Card>(Arrays.asList(cards));
    Card toPlay;

    if (contract == Contract.NULL) {
      toPlay = getLowestPlayableCardExcludeSuitIfPossible(list, null);
    } else if (contract == Contract.GRAND) {
      toPlay = getHighestPlayableCardExcludeSuitIfPossible(list, null);
    } else {
      toPlay = getHighestPlayableCardExcludeSuitIfPossible(list, contract);
    }

    return toPlay;
  }

  public Card playLeastExpensiveCardThatIsNotTrumpIfPossible(Card[] cards, Contract contract) {
    ArrayList<Card> list = new ArrayList<Card>(Arrays.asList(cards));
    Card toPlay;

    if (contract == Contract.NULL) {
      toPlay = getHighestPlayableCardExcludeSuitIfPossible(list, null);
    } else if (contract == Contract.GRAND) {
      toPlay = getLowestPlayableCardExcludeSuitIfPossible(list, null);
    } else {
      toPlay = getLowestPlayableCardExcludeSuitIfPossible(list, contract);
    }

    return toPlay;
  }



  /**
   *
   *
   * 
   **/
  public boolean isCardExpensive(Card card, Contract contract) {
    if (contract != Contract.NULL) {
      if (card.getTrickValue() >= 10 || card.getValue() == Value.JACK) {
        return true;
      } else {
        return false;
      }
    } else {
      if (card.getTrickValue() >= 4 || card.getValue() == Value.JACK) {
        return false;
      } else {
        return true;
      }

    }
  }

  public Suit convertContractToTrump(Contract contract) {
    switch (contract) {
      case DIAMONDS:
        return Suit.DIAMONDS;
      case HEARTS:
        return Suit.DIAMONDS;
      case SPADES:
        return Suit.DIAMONDS;
      case CLUBS:
        return Suit.DIAMONDS;
      default:
        return null;
    }
  }

  /**
   * Position == MiddleHand!!!!!!!
   *
   * 
   **/
  public boolean canTrickBeBeatenByMiddleHand(Card[] cards, Card[] trick, Contract contract) {
    for (Card card : cards) {
      if (card.beats(contract, trick[0])) {
        return true;
      }
    }

    return false;
  }

  /**
   * To use only after canTrickBeBeatenByMiddleHand
   *
   * 
   **/
  public Card winTrickAsMiddleHand(Card[] cards, Card[] trick, Contract contract) {
    for (Card card : cards) {
      if (card.beats(contract, trick[0])) {
        return card;
      }
    }
    return null;
  }



  /**
   * Position == REARHAND!!!!!!!
   *
   * 
   **/
  public boolean canTrickBeBeatenByRearHand(Card[] cards, Card[] trick, Contract contract) {
    for (Card card : cards) {
      if (card.beats(contract, trick[0]) && card.beats(contract, trick[1])) {
        return true;
      }
    }

    return false;
  }

  /**
   * To use only after canTrickBeBeatenByRearHand
   *
   * 
   **/
  public Card winTrickAsRearHand(Card[] cards, Card[] trick, Contract contract) {
    for (Card card : cards) {
      if (card.beats(contract, trick[0]) && card.beats(contract, trick[1])) {
        return card;
      }
    }
    return null;
  }

}
