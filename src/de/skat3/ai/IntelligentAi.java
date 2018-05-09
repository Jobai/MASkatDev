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

/**
 * IntelligentAi represents hard bot.
 * 
 * @author Artem Zamarajev, Emre Cura
 */
@SuppressWarnings("serial")
public class IntelligentAi extends Ai {
  private static final int NINE = 0;
  private static final int KING = 4;
  private static final int QUEEN = 3;

  AiHelper aiHelper = new AiHelper();

  // Updated with setters
  private Position myPosition;
  private boolean isSolo;
  private Hand hand;

  // Updated from lgs

  private Contract contract;
  private Card[] trick;
  private Player enemyOne;
  private Player enemyTwo;
  private Player localClient;

	/**
	 * Initializes the players from LocalGameState.
	 */
  private void initializePlayers() {
    enemyOne = SkatMain.lgs.getEnemyOne();
    enemyTwo = SkatMain.lgs.getEnemyTwo();
    localClient = SkatMain.lgs.getLocalClient();
  }

	/**
	 * Initializes the Trick from LocalGameState.
	 */
  private void initializeTrick() {
    trick = SkatMain.lgs.getTrick();
  }

	/**
	 * Initializes the Contract from LocalGameState.
	 */
  private void initializeContract() {
    contract = SkatMain.lgs.getContract();
  }

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

  @Override
  public Contract chooseContract() {
    Contract contract;
    System.out.println("IST WIRKLICH HARD");

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
    } else {
      // just fallback never happens
      contract = Contract.GRAND;
      return contract;
    }
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
    initializeContract();
    initializeTrick();
    try {
      if (myPosition.equals(Position.FOREHAND)) {
        return playForeHand();
      } else if (myPosition.equals(Position.MIDDLEHAND)) {
        return playMiddlehandCard();
      } else {
        return playRearhandCard();
      }
    } catch (Exception ex) {
      ex.printStackTrace();
      return getAnyPlayableCard();
    }
  }

  @Override
  public boolean acceptHandGame() {
    boolean handGame = false;
    int noOfTrumps = countTrumps(chooseContract());
    int noOfJacks = countJacks();
    int noOfAces = countAces();
    int noOfTens = countTens();
    int noOfLows = countSevens() + countEights() + countNines();
    Card lowestCard = getLowestCard();
    Card highestCard = getHighestCard();

    if (noOfJacks == 4) {
      handGame = true;
    } else if (noOfJacks >= 3 && noOfAces >= 3 && noOfTens >= 2) {
      handGame = true;
    } else if (noOfJacks >= 2 && noOfAces >= 3 && lowestCard.getTrickValue() >= QUEEN) {
      handGame = true;
    } else if (noOfLows >= 7) {
      handGame = true;
    } else if (noOfLows >= 5 && highestCard.getTrickValue() <= QUEEN) {
      handGame = true;
    } else if ((noOfTrumps >= 7)) {
      handGame = true;
    } else if (noOfTrumps >= 5 && noOfAces >= 3) {
      handGame = true;
    } else if (noOfTrumps >= 4 && lowestCard.getTrickValue() >= KING) {
      handGame = true;
    }
    return handGame;
  }

  @Override
  public AdditionalMultipliers chooseAdditionalMultipliers() {
    int noOfJacks = countJacks();
    int noOfTrumps = countTrumps(chooseContract());
    int noOfAces = countAces();
    int noOfTens = countTens();
    int noOfLows = countSevens() + countEights() + countNines();
    if (acceptHandGame()) {
      if (chooseContract() == Contract.NULL) {
        if (noOfLows == 10) {
          return aiHelper.getNullOuvert();
        }
        if (noOfLows > 8 && noOfJacks == 0 && noOfAces == 0 && noOfTens == 0) {
          return aiHelper.getNullHand();
        } else {
          return aiHelper.getHandGameNoMultipliers();
        }
      } else if (chooseContract() == Contract.GRAND) {
        if (noOfJacks == 4 && noOfAces == 4) {
          return aiHelper.getOpenHand();
        } else if (noOfJacks == 4 && noOfAces >= 3 && noOfTens >= 2) {
          return aiHelper.getSchwarz();
        } else if (noOfJacks >= 3 && noOfAces >= 2 && noOfTens >= 2) {
          return aiHelper.getSchneider();
        } else {
          aiHelper.getHandGameNoMultipliers();
        }
      } else {
        if (noOfTrumps >= 9) {
          return aiHelper.getSchwarz();
        } else if (noOfJacks == 4 && noOfTrumps >= 8) {
          return aiHelper.getSchwarz();
        } else if (noOfTrumps >= 7 && noOfJacks >= 2) {
          return aiHelper.getSchneider();
        } else {
          aiHelper.getHandGameNoMultipliers();
        }
      }
    }
    return aiHelper.getNoHandGameNoMultipliers();
  }

  @Override
  public ReturnSkat selectSkat(Card[] skat) {
    Contract potentialContract = chooseContract();


    if (skat[0].getTrickValue() == 2 && skat[1].getTrickValue() == 2
        && potentialContract.equals(Contract.GRAND)) {

      Card[] cardsArray = sortCardsIncreasing(hand.getCards(), null);
      ArrayList<Card> cards = new ArrayList<Card>(Arrays.asList(cardsArray));


      cards.add(skat[0]);
      cards.add(skat[1]);

      Card[] newSkat = new Card[2];

      newSkat[0] = cards.get(0);
      newSkat[1] = cards.get(1);

      cards.remove(0);
      cards.remove(0);

      Card[] temp = new Card[10];
      for (int i = 0; i < temp.length; i++) {
        temp[i] = cards.get(i).copy();
      }

      return new ReturnSkat(new Hand(temp), newSkat);

    } else if (skat[0].getTrickValue() <= NINE && skat[1].getTrickValue() <= NINE
        && potentialContract.equals(Contract.NULL)) {

      Card[] cardsArray = sortCardsIncreasing(hand.getCards(), null);
      ArrayList<Card> cards = new ArrayList<Card>(Arrays.asList(cardsArray));

      Card toSkat1 = cards.get(cards.size() - 1);
      Card toSkat2 = cards.get(cards.size() - 2);

      cards.remove(cards.size() - 1);
      cards.remove(cards.size() - 1);

      cards.add(skat[0]);
      cards.add(skat[1]);

      Card[] newSkat = new Card[2];

      newSkat[0] = toSkat1;
      newSkat[1] = toSkat2;

      Card[] temp = new Card[10];
      for (int i = 0; i < temp.length; i++) {
        temp[i] = cards.get(i).copy();
      }

      return new ReturnSkat(new Hand(temp), newSkat);

    } else if (potentialContract.equals(Contract.DIAMONDS) && skat[0].getSuit() == Suit.DIAMONDS
        && skat[1].getSuit() == Suit.DIAMONDS) {

      Card[] cardsArray = sortCardsIncreasing(hand.getCards(), Suit.DIAMONDS);
      ArrayList<Card> cards = new ArrayList<Card>(Arrays.asList(cardsArray));

      cards.add(skat[0]);
      cards.add(skat[1]);

      Card[] newSkat = new Card[2];

      Card toSkat1 = cards.get(0);
      Card toSkat2 = cards.get(1);

      newSkat[0] = toSkat1;
      cards.remove(toSkat1);

      newSkat[1] = toSkat2;
      cards.remove(toSkat2);

      Card[] temp = new Card[10];

      for (int i = 0; i < temp.length; i++) {
        temp[i] = cards.get(i).copy();
      }

      return new ReturnSkat(new Hand(temp), newSkat);
    } else if (potentialContract.equals(Contract.HEARTS) && skat[0].getSuit() == Suit.HEARTS
        && skat[1].getSuit() == Suit.HEARTS) {

      Card[] cardsArray = sortCardsIncreasing(hand.getCards(), Suit.HEARTS);
      ArrayList<Card> cards = new ArrayList<Card>(Arrays.asList(cardsArray));

      cards.add(skat[0]);
      cards.add(skat[1]);

      Card[] newSkat = new Card[2];

      Card toSkat1 = cards.get(0);
      Card toSkat2 = cards.get(1);

      newSkat[0] = toSkat1;
      cards.remove(toSkat1);

      newSkat[1] = toSkat2;
      cards.remove(toSkat2);

      Card[] temp = new Card[10];

      for (int i = 0; i < temp.length; i++) {
        temp[i] = cards.get(i).copy();
      }

      return new ReturnSkat(new Hand(temp), newSkat);
    } else if (potentialContract.equals(Contract.SPADES) && skat[0].getSuit() == Suit.SPADES
        && skat[1].getSuit() == Suit.SPADES) {

      Card[] cardsArray = sortCardsIncreasing(hand.getCards(), Suit.SPADES);
      ArrayList<Card> cards = new ArrayList<Card>(Arrays.asList(cardsArray));

      cards.add(skat[0]);
      cards.add(skat[1]);

      Card[] newSkat = new Card[2];

      Card toSkat1 = cards.get(0);
      Card toSkat2 = cards.get(1);

      newSkat[0] = toSkat1;
      cards.remove(toSkat1);

      newSkat[1] = toSkat2;
      cards.remove(toSkat2);

      Card[] temp = new Card[10];

      for (int i = 0; i < temp.length; i++) {
        temp[i] = cards.get(i).copy();
      }

      return new ReturnSkat(new Hand(temp), newSkat);
    } else if (potentialContract.equals(Contract.CLUBS) && skat[0].getSuit() == Suit.CLUBS
        && skat[1].getSuit() == Suit.CLUBS) {

      Card[] cardsArray = sortCardsIncreasing(hand.getCards(), Suit.CLUBS);
      ArrayList<Card> cards = new ArrayList<Card>(Arrays.asList(cardsArray));

      cards.add(skat[0]);
      cards.add(skat[1]);

      Card[] newSkat = new Card[2];

      Card toSkat1 = cards.get(0);
      Card toSkat2 = cards.get(1);

      newSkat[0] = toSkat1;
      cards.remove(toSkat1);

      newSkat[1] = toSkat2;
      cards.remove(toSkat2);

      Card[] temp = new Card[10];

      for (int i = 0; i < temp.length; i++) {
        temp[i] = cards.get(i).copy();
      }

      return new ReturnSkat(new Hand(temp), newSkat);
    } else {
      return new ReturnSkat(this.hand, skat);
    }
  }

  	/**
	 * Gives the best possible Card back which is not a trump when the player is in
	 * Forehand position.
	 * 
	 * @return instance of Card.
	 */
  private Card playForeHand() {
    return playMostExpensiveCardThatIsNotTrumpIfPossible();
  }

  	/**
	 * Gives the best possible Card back when the player is in Midddlehand position.
	 * 
	 * @return Instance of Card.
	 */
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

  	/**
	 * Gives the best possible Card back when the player is in Rearhand position.
	 * 
	 * @return Instance of Card.
	 */
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

  	/**
	 * Gets the number of jacks in the given hand back.
	 * 
	 * @return number of jacks.
	 */
  private int countJacks() {
    return getAllCardsOfValue(Value.JACK).size();
  }


  	/**
	 * Gets the number of trumps in the given hand.
	 * 
	 * @return number of trumps.
	 */
  private int countTrumps(Contract contract) {
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
	 * Gets the number of aces in the given hand.
	 * 
	 * @return number of aces.
	 */
  private int countAces() {
    return getAllCardsOfValue(Value.ACE).size();
  }


  	/**
	 * Gets the number of kings in the given hand.
	 * 
	 * @return number of aces.
	 */
  private int countKings() {
    return getAllCardsOfValue(Value.KING).size();
  }

  	/**
	 * Gets the number of queen in the given hand.
	 * 
	 * @return number of aces.
	 */
  private int countQueens() {
    return getAllCardsOfValue(Value.QUEEN).size();
  }


  	/**
	 * Gets the number of Nines in the given hand.
	 * 
	 * @return number of aces.
	 */
  private int countNines() {
    return getAllCardsOfValue(Value.NINE).size();
  }

  	/**
	 * Gets the number of Eights in the given hand.
	 * 
	 * @return number of aces.
	 */
  private int countEights() {
    return getAllCardsOfValue(Value.EIGHT).size();
  }

  	/**
	 * Gets the number of Sevens in the given hand.
	 * 
	 * @return number of aces.
	 */
  private int countSevens() {
    return getAllCardsOfValue(Value.SEVEN).size();
  }

  	/**
	 * Gets the number of Tens in the given hand.
	 * 
	 * @return number of Tens.
	 */
  private int countTens() {
    return getAllCardsOfValue(Value.TEN).size();
  }

	/**
	 * Gets the highest Card in the given hand.
	 * 
	 * @return number of Tens.
	 */
  private Card getHighestCard() {
    Card[] cards = hand.getCards();
    Card output = null;
    for (Card card : cards) {
      if (output == null || card.getTrickValue() > output.getTrickValue()) {
        output = card;
      }
    }
    return output;
  }

  	/**
	 * Sorts cards increasingly by Trick Value and puts Jacks to the right.
	 * 
	 * @return sorted Card Array.
	 */
  private Card[] sortCardsIncreasing(Card[] cards, Suit trump) {
    // 7,8,9,q,k,10,a, trumps, j
    for (int i = 0; i < cards.length; i++) {
      for (int j = 0; j < cards.length - 1; j++) {
        if (cards[j].getTrickValue() == 2 && cards[j + 1].getTrickValue() != 2) {
          Card temp = cards[j];
          cards[j] = cards[j + 1];
          cards[j + 1] = temp;
        } else if (cards[j].getSuit() == trump && cards[j + 1].getSuit() != trump
            && cards[j + 1].getTrickValue() != 2) {
          Card temp = cards[j];
          cards[j] = cards[j + 1];
          cards[j + 1] = temp;
        } else if (cards[j].getTrickValue() > cards[j + 1].getTrickValue()
            && cards[j + 1].getTrickValue() != 2) {
          Card temp = cards[j];
          cards[j] = cards[j + 1];
          cards[j + 1] = temp;
        }
      }
    }
    return cards;
  }

  	/**
	 * Gives the Values of Cards in the Hand back.
	 * 
	 * @param value
	 * 
	 *            The parameter represents the Value of the Card.
	 * @return ArrayList.
	 */
  private ArrayList<Card> getAllCardsOfValue(Value value) {
    ArrayList<Card> jacksList = new ArrayList<Card>();
    Card[] cards = hand.getCards();

    for (Card card : cards) {
      if (card.getValue() == value) {
        jacksList.add(card);
      }
    }
    return jacksList;
  }

  	/**
	 * Gives the highest playable Card that is not a trump.
	 * 
	 * @return Instance of Card.
	 */
  private Card getHighestPlayableCardExcludeTrumpIfPossible() {
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

  	/**
	 * Gives the lowest playable Card that is not a trump.
	 * 
	 * @return Instance of Card.
	 */
  private Card getLowestPlayableCardExcludeTrumpIfPossible() {
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

  	/**
	 * Gives a valid card back.
	 * 
	 * @return instance of Card.
	 */
  private Card getAnyPlayableCard() {
    Card[] cards = hand.getCards();
    for (Card card : cards) {
      if (card.isPlayable()) {
        return card;
      }
    }
    return null;
  }

  	/**
	 * Checks if the selected Grand should be used as Contract.
	 * 
	 * @return true if player has good cards for this suit false if not.
	 */
  private boolean checkGrand() {
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

  	/**
	 * Checks if the selected Null should be used as Contract.
	 * 
	 * @return true if player has good cards for this suit false if not.
	 */
  private boolean checkNull() {
    int noOfAces = countAces();
    int noOfKings = countKings();
    int noOfQueens = countQueens();
    int noOfLusche = countSevens() + countEights() + countNines();
    boolean nullB = false;

    if (noOfLusche >= 6 && noOfAces == 0 && noOfKings == 0 && noOfQueens == 0) {
      nullB = true;
    }
    return nullB;
  }

  	/**
	 * Checks if the selected Suit Contract should be used as Contract.
	 * 
	 * @param suit
	 *            The parameter represents the actual contract that is checked.
	 * 
	 * @return true if player has good cards for this suit false if not.
	 */
  private boolean checkSuit(Suit suit) {
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
	 * Tellst if the the Trick is going to be win by Teammate.
	 *
	 * 
	 **/
  private boolean willTrickBeWonByTeammate() {
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

  	/**
	 * Gives the position of the Solo player in the trick back.
	 * 
	 * @return Instance of Position.
	 */
  private Position getSoloPosition() {
    initializePlayers();
    if (enemyOne.isSolo()) {
      return enemyOne.getPosition();
    } else if (enemyTwo.isSolo()) {
      return enemyTwo.getPosition();
    } else {
      return localClient.getPosition();
    }
  }


	/**
	 * Gives the position of the Teammate back.
	 * 
	 * @return Instance of Position.
	 */
  private Position getTeammatePosition() {
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

  /**
   * Tells player if card is first Card from Teammate.
   * 
   * @return True if card is First Card in Trick from Teammate false if not.
   */
  private boolean isFirstCardInTrickFromTeammate() {
    if (getTeammatePosition() == Position.FOREHAND) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Gives the most expensive Card that is not a trump back if it´s possible in the game.
   * 
   * @return Instance of Card.
   */
  private Card playMostExpensiveCardThatIsNotTrumpIfPossible() {

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

  /**
   * Gives the least expensive Card that is not a trump back if it´s possible in the game.
   * 
   * @return instance of Card.
   */
  private Card playLeastExpensiveCardThatIsNotTrumpIfPossible() {

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
   * Tells the player if this Card has a high value.
   *
   * @return true if the card has a high value false if not.
   **/
  private boolean isCardExpensive(Card card) {

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

  	/**
	 * Gives back the Suit Contract.
	 */
  private Suit convertContractToTrump() {
    Contract potentialContract = chooseContract();
    // changed contract with potentialContract for testing
    switch (potentialContract) {
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
	 * Checks if the Trick can be be beaten by the Ai player is in middle hand.Only
	 * called if the Ai player position is in middle hand.
	 * 
	 * @return Instance of Card.
	 * 
	 **/
  private boolean canTrickBeBeatenByMiddleHand() {

    Card[] cards = hand.getCards();
    for (Card card : cards) {
      if (card.beats(contract, trick[0])) {
        return true;
      }
    }

    return false;
  }

  /**
   * To use only after canTrickBeBeatenByMiddleHand.
   * 
   * @return Instance of Card.
   * 
   **/
  private Card winTrickAsMiddleHand() {

    Card[] cards = hand.getCards();
    for (Card card : cards) {
      if (card.beats(contract, trick[0])) {
        return card;
      }
    }
    return null;
  }

  	/**
	 * Checks if the Trick can be be beaten by the Ai player is in rear hand.Only
	 * called if the Ai player position is in rear hand.
	 * 
	 * @return Instance of Card.
	 * 
	 **/
  private boolean canTrickBeBeatenByRearHand() {

    Card[] cards = hand.getCards();
    for (Card card : cards) {
      if (card.beats(contract, trick[0]) && card.beats(contract, trick[1])) {
        return true;
      }
    }

    return false;
  }

  	/**
	 * To use only after canTrickBeBeatenByRearHand.
	 * 
	 * @return Instance of Card.
	 * 
	 **/
  private Card winTrickAsRearHand() {
    Card[] cards = hand.getCards();
    for (Card card : cards) {
      if (card.beats(contract, trick[0]) && card.beats(contract, trick[1])) {
        return card;
      }
    }
    return null;
  }

  public Hand getHand() {
    return this.hand;
  }

  private Card getLowestCard() {
    Card[] cards = sortCardsIncreasing(hand.getCards(), null);
    return cards[0];
  }
}


