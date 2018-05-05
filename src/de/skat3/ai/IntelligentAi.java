package de.skat3.ai;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import de.skat3.gamelogic.AdditionalMultipliers;
import de.skat3.gamelogic.Card;
import de.skat3.gamelogic.Contract;
import de.skat3.gamelogic.Hand;
import de.skat3.gamelogic.Player;
import de.skat3.gamelogic.Position;
import de.skat3.gamelogic.Suit;
import de.skat3.gamelogic.Value;
import de.skat3.main.SkatMain;

@SuppressWarnings("serial")
public class IntelligentAi extends Ai {
  final int NINE = 0;
  final int KING = 4;
  final int QUEEN = 3;
  final int ACE = 11;
  final int TEN = 10;
  final int JACK = 2;

  // Updated with setters
  private Position myPosition;
  private boolean isSolo;
  private Hand hand;

  // Updated from lgs
  private Contract contract = SkatMain.lgs.getContract();
  private Card[] trick = SkatMain.lgs.getTrick();
  private Player enemyOne = SkatMain.lgs.getEnemyOne();
  private Player enemyTwo = SkatMain.lgs.getEnemyTwo();
  private Player localClient = SkatMain.lgs.getLocalClient();

  // Updated intern
  private boolean acceptHandGame;

  @Override
  public void setPosition(Position position) {
    this.myPosition = position;
  }

  @Override
  public void setIsSolo(boolean isSolo) {
    this.isSolo = isSolo;
  }

  @Override
  public void setHand(Hand hand) {
    this.hand = new Hand(hand.getCards());
  }

  // TODO check if return null possible
  @Override
  public Contract chooseContract() {
    Contract contract = null;

    if (checkGrand()) {
      contract = Contract.GRAND;
      return contract;
    } else if (checkNull()) {
      contract = Contract.NULL;
      return contract;
    } else if (checkSuit(Suit.SPADES)) {
      contract = Contract.SPADES;
      return contract;
    } else if (checkSuit(Suit.DIAMONDS)) {
      contract = Contract.DIAMONDS;
      return contract;

    } else if (checkSuit(Suit.HEARTS)) {
      contract = Contract.HEARTS;
      return contract;
    } else if (checkSuit(Suit.CLUBS)) {
      contract = Contract.CLUBS;
      return contract;
    }
    return contract;
  }

  @Override
  public boolean acceptBid(int bid) {

    /*
     * if (maxBid > 0) { switch (mostFrequentSuitColor) { case CLUBS: contract = Contract.CLUBS;
     * break; case SPADES: contract = Contract.SPADES; break; case HEARTS: contract =
     * Contract.HEARTS; break; case DIAMONDS: contract = Contract.DIAMONDS; break; } }
     */

    int maxBidSpades = hand.getMaximumBid(Contract.SPADES);
    int maxBidDiamonds = hand.getMaximumBid(Contract.DIAMONDS);
    int maxBidHearts = hand.getMaximumBid(Contract.HEARTS);
    int maxBidClubs = hand.getMaximumBid(Contract.CLUBS);
    int maxBidGrand = hand.getMaximumBid(Contract.GRAND);
    int maxBidNull = hand.getMaximumBid(Contract.NULL);

    if (bid < maxBidGrand && checkGrand()) {
      return true;
    } else if (bid < maxBidNull && checkNull()) {
      return true;
    } else if (bid < maxBidSpades && checkSuit(Suit.SPADES)) {
      return true;
    } else if (bid < maxBidDiamonds && checkSuit(Suit.DIAMONDS)) {
      return true;
    } else if (bid < maxBidHearts && checkSuit(Suit.HEARTS)) {
      return true;
    } else if (bid < maxBidClubs && checkSuit(Suit.CLUBS)) {
      return true;
    } else {
      return false;
    }
  }

  @Override
  public Card chooseCard() {

    if (myPosition.equals(Position.FOREHAND)) {
      return playForeHand();
    } else if (myPosition.equals(Position.MIDDLEHAND)) {
      return playMiddlehandCard();
    } else {
      return playRearhandCard();
    }
  }

  @Override
  public boolean acceptHandGame() {
    boolean handGame = false;
    int noOfTrumps = countTrumps(chooseContract());
    int noOfJacks = countJacks();
    int noOfAces = countAces();
    int noOfTens = countTens();
    int noOfLusche = countSevens() + countEights() + countNines();
    Card lowestCard = getLowestCard();
    Card highestCard = getHighestCard();

    if (noOfJacks == 4) {
      handGame = true;
    } else if (noOfJacks >= 3 && noOfAces >= 3 && noOfTens >= 2) {
      handGame = true;
    } else if (noOfJacks >= 2 && noOfAces >= 3 && lowestCard.getTrickValue() >= QUEEN) {
      handGame = true;
    } else if (noOfLusche >= 7) {
      handGame = true;
    } else if (noOfLusche >= 5 && highestCard.getTrickValue() <= QUEEN) {
      handGame = true;
    } else if ((noOfTrumps >= 7)) {
      handGame = true;
    } else if (noOfTrumps >= 5 && noOfAces >= 3) {
      handGame = true;
    } else if (noOfTrumps >= 4 && lowestCard.getTrickValue() >= KING) {
      handGame = true;
    }
    setHandGame(handGame);
    return handGame;
  }

  @Override
  public AdditionalMultipliers chooseAdditionalMultipliers() {
    // TODO Auto-generated method stub
    if (acceptHandGame) {
  }
    return null;
    }

	@Override
	public ReturnSkat selectSkat(Card[] skat) {
		ArrayList<Card> cards = new ArrayList<Card>();
		
		if (skat[0].getTrickValue() == 2 && skat[1].getTrickValue() == 2) {
			for (int i = 0; i < 10; i++) {
				cards.add(this.hand.cards[i]);
			}
			cards.add(skat[0]);
			cards.add(skat[1]);
			Card[] newSkat = new Card[2];
			Card highestCard = getHighestCard();
			newSkat[0] = highestCard;
			cards.remove(highestCard);
			Card highestCard2 = getLowestPlayableCardExcludeTrumpIfPossible();
			newSkat[1] = highestCard2;
			cards.remove(highestCard2);
			newSkat[0] = skat[0];
			newSkat[1] = skat[1];
			Card[] temp = new Card[10];
			for (int i = 0; i < temp.length; i++) {
				temp[i] = cards.get(i).copy();
			}
			return new ReturnSkat(new Hand(temp), newSkat);
			} else if (skat[0].getTrickValue() <= NINE && skat[1].getTrickValue() <= NINE) {
				for (int i = 0; i < 10; i++) {
					cards.add(this.hand.cards[i]);
				}
				cards.add(skat[0]);
				cards.add(skat[1]);
				Card[] newSkat = new Card[2];
			Card highestCard = getHighestCard();
				newSkat[0] = highestCard;
				cards.remove(highestCard);
			Card highestCard2 = getLowestPlayableCardExcludeTrumpIfPossible();
				newSkat[1] = highestCard2;
				cards.remove(highestCard2);
				newSkat[0] = skat[0];
				newSkat[1] = skat[1];
				Card[] temp = new Card[10];
				for (int i = 0; i < temp.length; i++) {
					temp[i] = cards.get(i).copy();
				}
				return new ReturnSkat(new Hand(temp), newSkat);
			}else {
				for (int i = 0; i < 10; i++) {
				cards.add(this.hand.cards[i]);
				}
				cards.add(skat[0]);
				cards.add(skat[1]);
				Card[] newSkat = new Card[2];

			Card lowestCard = getLowestPlayableCardExcludeTrumpIfPossible();
				newSkat[0] = lowestCard;
				cards.remove(lowestCard);
			newSkat[1] = lowestCard;
			cards.remove(lowestCard);
				Card[] temp = new Card[10];
				for (int i = 0; i < temp.length; i++) {
					temp[i] = cards.get(i).copy();
				}
				return new ReturnSkat(new Hand(temp), newSkat);
		}
	}


  private void setHandGame(boolean handGame) {
    acceptHandGame = handGame;
  }

  private Card playForeHand() {
    return playMostExpensiveCardThatIsNotTrumpIfPossible();
  }

  private Card playMiddlehandCard() {

    boolean firstCardFromTeammate = isFirstCardInTrickFromTeammate();
    if (firstCardFromTeammate) {
      if (isCardExpensive(trick[0])) {
        return getHighestPlayableCardExcludeTrumpIfPossible();
      } else {
        return getLowestPlayableCardExcludeTrumpIfPossible();
      }
    } else {
      boolean canBeBeaten = canTrickBeBeatenByMiddleHand();
      if (canBeBeaten) {
        return winTrickAsMiddleHand();
      } else {
        return getLowestPlayableCardExcludeTrumpIfPossible();
      }
    }
  }

  private Card playRearhandCard() {

    boolean teammateWinTrick = willTrickBeWonByTeammate();
    if (teammateWinTrick) {
      return playMostExpensiveCardThatIsNotTrumpIfPossible();
    } else {
      boolean canTrickBeBeaten = canTrickBeBeatenByRearHand();
      if (canTrickBeBeaten) {
        return winTrickAsRearHand();
      } else {
        return playLeastExpensiveCardThatIsNotTrumpIfPossible();
      }
    }
  }



  public Suit getMostFrequentSuitExcluding(Suit exclude) {
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
   * Gets the number of jacks in the given hand
   * 
   * @param cards a hand
   * @return number of jacks
   */
  public int countJacks() {
    return getAllPlayableCardsOfValue(Value.JACK).size();
  }


  /**
   * Gets the number of trumps in the given hand
   * 
   * @param cards a hand
   * @return number of trumps
   */
  public int countTrumps(Contract contract) {
    Card[] cards = hand.getCards();
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
  public int countAces() {
    return getAllPlayableCardsOfValue(Value.ACE).size();
  }

  /**
   * Gets the number of Nines in the given hand
   * 
   * @param cards a hand
   * @return number of aces
   */
  public int countNines() {
    return getAllCardsOfValue(Value.NINE).size();
  }

  /**
   * Gets the number of Eights in the given hand
   * 
   * @param cards a hand
   * @return number of aces
   */
  public int countEights() {
    return getAllCardsOfValue(Value.EIGHT).size();
  }

  /**
   * Gets the number of Sevens in the given hand
   * 
   * @param cards a hand
   * @return number of aces
   */
  public int countSevens() {
    return getAllCardsOfValue(Value.SEVEN).size();
  }

  /**
   * Gets the number of Tens in the given hand
   * 
   * @param cards a hand
   * @return number of Tens
   */
  public int countTens() {
    return getAllCardsOfValue(Value.TEN).size();
  }



  public Card getHighestCard1() {

    return new Card();
  }

  private Card getHighestCard() {
    Card[] cards = hand.getCards();
    Card output = null;
    for (Card card : cards) {
      if (output == null || card.getTrickValue() > output.getTrickValue()) {

				// nullnullnull;
      }
    }
    return output;
  }

  private Card getLowestCard() {
    Card[] cards = hand.getCards();
    Card output = null;
    for (Card card : cards) {
      if (output == null || card.getTrickValue() < output.getTrickValue()) {
        // nullnullnull;
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
  private int[] calculateSuits() {
    Card[] cards = hand.getCards();
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

  public int getAmountOfCardsWithSameSuit(Card card) {
    int[] suitArray = calculateSuits();
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

  public ArrayList<Card> getAllCardsOfValue(Value value) {
    ArrayList<Card> jacksList = new ArrayList<Card>();
    Card[] cards = hand.getCards();

    for (Card card : cards) {
      if (card.getValue() == value) {
        jacksList.add(card);
      }
    }
    return jacksList;
  }

  public ArrayList<Card> getAllPlayableCardsOfValue(Value value) {
    Card[] cards = hand.getCards();

    ArrayList<Card> jacksList = new ArrayList<Card>();
    for (Card card : cards) {
      if (card.getValue() == value && card.isPlayable()) {
        jacksList.add(card);
      }
    }
    return jacksList;
  }

  public ArrayList<Card> getAllPlayableCardsOfSuit(Suit suit) {
    Card[] cards = hand.getCards();
    ArrayList<Card> suitList = new ArrayList<Card>();
    for (Card card : cards) {
      if (card.getSuit() == suit && card.isPlayable()) {
        suitList.add(card);
      }
    }
    return suitList;
  }

  public Card getHighestPlayableCardExcludeTrumpIfPossible() {
    Card[] cards = hand.getCards();
    ArrayList<Card> cardsList = new ArrayList<Card>(Arrays.asList(cards));
    Iterator<Card> iter = cardsList.iterator();
    Suit exclude = convertContractToTrump();
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

  public Card getLowestPlayableCardExcludeTrumpIfPossible() {
    Card[] cards = hand.getCards();
    ArrayList<Card> cardsList = new ArrayList<Card>(Arrays.asList(cards));
    Iterator<Card> iter = cardsList.iterator();
    Suit exclude = convertContractToTrump();
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


  public Card getAnyPlayableCard() {
    Card[] cards = hand.getCards();
    for (Card card : cards) {
      if (card.isPlayable()) {
        return card;
      }
    }
    return null;
  }

  public boolean checkGrand() {
    int noOfJacks = countJacks();
    int noOfAces = countAces();
    int noOfTens = countTens();
    boolean grand = false;

    if (noOfJacks >= 2 && noOfAces >= 3) {
      grand = true;
    } else if (noOfJacks >= 1 && noOfAces > 2 && noOfTens > 2) {
      grand = true;
    }
    return grand;
  }

  public boolean checkNull() {
    int noOfAces = countAces();
    int noOfTens = countTens();
    int noOfLusche = countSevens() + countEights() + countNines();
    boolean nullB = false;

    if (noOfLusche >= 5) {
      nullB = true;
    } else if (noOfLusche >= 3 && noOfAces == 0 && noOfTens == 0) {
      nullB = true;
    }
    return nullB;
  }

  public boolean checkSuit(Suit suit) {
    int noOfAces = countAces();
    int noOfTens = countTens();
    boolean suites = false;

    int noOfTrumpsSpades = countTrumps(Contract.SPADES);
    if ((noOfTrumpsSpades >= 5)) {
      suites = true;
    } else if (noOfTrumpsSpades >= 3 && noOfAces >= 3) {
      suites = true;
    } else if (noOfTrumpsSpades >= 2 && noOfAces >= 3 && noOfTens >= 3) {
      suites = true;
    }

    int noOfTrumpsDiamonds = countTrumps(Contract.DIAMONDS);
    if ((noOfTrumpsDiamonds >= 5)) {
      suites = true;
    } else if (noOfTrumpsDiamonds >= 3 && noOfAces >= 3) {
      suites = true;
    } else if (noOfTrumpsDiamonds >= 2 && noOfAces >= 3 && noOfTens >= 3) {
      suites = true;
    }

    int noOfTrumpsHearts = countTrumps(Contract.HEARTS);
    if ((noOfTrumpsHearts >= 5)) {
      suites = true;
    } else if (noOfTrumpsHearts >= 3 && noOfAces >= 3) {
      suites = true;
    } else if (noOfTrumpsHearts >= 2 && noOfAces >= 3 && noOfTens >= 3) {
      suites = true;
    }

    int noOfTrumpsClubs = countTrumps(Contract.CLUBS);

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
  public boolean willTrickBeWonByTeammate() {
    if (isSolo) {
      return false;
    } else {
      Position teammatesPosition = getTeammatePosition();
      if (teammatesPosition == Position.FOREHAND) {
        if (trick[0].beats(contract, trick[1])) {
          return true;
        } else {
          return false;
        }
      } else if (teammatesPosition == Position.MIDDLEHAND) {
        if (trick[1].beats(contract, trick[0])) {
          return true;
        } else {
          return false;
        }
      }
    }

    return false;
  }

  private Position getSoloPosition() {
    if (enemyOne.isSolo()) {
      return enemyOne.getPosition();
    } else if (enemyTwo.isSolo()) {
      return enemyTwo.getPosition();
    } else {
      return localClient.getPosition();
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
  public Position getTeammatePosition() {
    Position teamMatePosition = null;
    if (myPosition == Position.MIDDLEHAND) {
      if (getSoloPosition() == Position.REARHAND) {
        teamMatePosition = Position.FOREHAND;
      } else if (getSoloPosition() == Position.FOREHAND) {
        teamMatePosition = Position.REARHAND;
      }
    } else if (myPosition == Position.REARHAND) {
      if (getSoloPosition() == Position.MIDDLEHAND) {
        teamMatePosition = Position.FOREHAND;
      } else if (getSoloPosition() == Position.FOREHAND) {
        teamMatePosition = Position.MIDDLEHAND;
      }
    }
    return teamMatePosition;
  }

  // TODO ask Kai
  // TODO check if its enemyThree or localClient
  public boolean isFirstCardInTrickFromTeammate() {
    if (getTeammatePosition() == Position.FOREHAND) {
      return true;
    } else {
      return false;
    }
  }

  public Card playMostExpensiveCardThatIsNotTrumpIfPossible() {
    Card toPlay;

    if (contract == Contract.NULL) {
      toPlay = getLowestPlayableCardExcludeTrumpIfPossible();
    } else if (contract == Contract.GRAND) {
      toPlay = getHighestPlayableCardExcludeTrumpIfPossible();
    } else {
      toPlay = getHighestPlayableCardExcludeTrumpIfPossible();
    }

    return toPlay;
  }

  public Card playLeastExpensiveCardThatIsNotTrumpIfPossible() {
    Card toPlay;

    if (contract == Contract.NULL) {
      toPlay = getHighestPlayableCardExcludeTrumpIfPossible();
    } else if (contract == Contract.GRAND) {
      toPlay = getLowestPlayableCardExcludeTrumpIfPossible();
    } else {
      toPlay = getLowestPlayableCardExcludeTrumpIfPossible();
    }

    return toPlay;
  }



  /**
   *
   *
   * 
   **/
  public boolean isCardExpensive(Card card) {
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

  public Suit convertContractToTrump() {
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
  public boolean canTrickBeBeatenByMiddleHand() {
    Card[] cards = hand.getCards();
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
  public Card winTrickAsMiddleHand() {
    Card[] cards = hand.getCards();
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
  public boolean canTrickBeBeatenByRearHand() {
    Card[] cards = hand.getCards();
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
  public Card winTrickAsRearHand() {
    Card[] cards = hand.getCards();
    for (Card card : cards) {
      if (card.beats(contract, trick[0]) && card.beats(contract, trick[1])) {
        return card;
      }
    }
    return null;
  }

}


