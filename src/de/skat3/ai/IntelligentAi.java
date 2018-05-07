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

  // just for testing
  // private Contract contract;
  // private Card[] trick;
  // private Player enemyOne;
  // private Player enemyTwo;
  // private Player localClient;
  //
  // public void setContractTest(Contract contract) {
  // this.contract = contract;
  // }
  //

  private void initializePlayers() {
     enemyOne = SkatMain.lgs.getEnemyOne();
     enemyTwo = SkatMain.lgs.getEnemyTwo();
     localClient = SkatMain.lgs.getLocalClient();
  }
  
  private void initializeTrick() {
  trick = SkatMain.lgs.getTrick();
  }
  
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

  /**
   * @param acceptedHandGame Tells if a hand game was accepted
   * 
   * @return if a handGame was accepted - returns array consisting of only one element -
   *         multiplierOpen else retuns an array consisting of 4 elements : noMultipliers,
   *         multiplierSchneider, multiplierSchwarz, multiplierHand
   * 
   */
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

  private Suit getMostFrequentSuitExcluding(Suit exclude) {
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
  private int countJacks() {
    return getAllCardsOfValue(Value.JACK).size();
  }


  /**
   * Gets the number of trumps in the given hand
   * 
   * @param cards a hand
   * @return number of trumps
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
   * Gets the number of aces in the given hand
   * 
   * @param cards a hand
   * @return number of aces
   */
  private int countAces() {
    return getAllCardsOfValue(Value.ACE).size();
  }


  /**
   * Gets the number of kings in the given hand
   * 
   * @param cards a hand
   * @return number of aces
   */
  private int countKings() {
    return getAllCardsOfValue(Value.KING).size();
  }

  /**
   * Gets the number of queen in the given hand
   * 
   * @param cards a hand
   * @return number of aces
   */
  private int countQueens() {
    return getAllCardsOfValue(Value.QUEEN).size();
  }


  /**
   * Gets the number of Nines in the given hand
   * 
   * @param cards a hand
   * @return number of aces
   */
  private int countNines() {
    return getAllCardsOfValue(Value.NINE).size();
  }

  /**
   * Gets the number of Eights in the given hand
   * 
   * @param cards a hand
   * @return number of aces
   */
  private int countEights() {
    return getAllCardsOfValue(Value.EIGHT).size();
  }

  /**
   * Gets the number of Sevens in the given hand
   * 
   * @param cards a hand
   * @return number of aces
   */
  private int countSevens() {
    return getAllCardsOfValue(Value.SEVEN).size();
  }

  /**
   * Gets the number of Tens in the given hand
   * 
   * @param cards a hand
   * @return number of Tens
   */
  private int countTens() {
    return getAllCardsOfValue(Value.TEN).size();
  }



  private Card getHighestCard1() {

    return new Card();
  }

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
   * Sorts cards increasingly by Trick Value and puts Jacks to the right
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

  private int getAmountOfCardsWithSameSuit(Card card) {
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

  private ArrayList<Card> getAllPlayableCardsOfValue(Value value) {
    Card[] cards = hand.getCards();

    ArrayList<Card> jacksList = new ArrayList<Card>();
    for (Card card : cards) {
      if (card.getValue() == value && card.isPlayable()) {
        jacksList.add(card);
      }
    }
    return jacksList;
  }

  private ArrayList<Card> getAllPlayableCardsOfSuit(Suit suit) {
    Card[] cards = hand.getCards();
    ArrayList<Card> suitList = new ArrayList<Card>();
    for (Card card : cards) {
      if (card.getSuit() == suit && card.isPlayable()) {
        suitList.add(card);
      }
    }
    return suitList;
  }

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


  private Card getAnyPlayableCard() {
    Card[] cards = hand.getCards();
    for (Card card : cards) {
      if (card.isPlayable()) {
        return card;
      }
    }
    return null;
  }

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

  private boolean checkNull() {
    int noOfAces = countAces();
    int noOfKings = countKings();
    int noOfQueens = countQueens();
    int noOfLusche = countSevens() + countEights() + countNines();
    boolean nullB = false;

    if (noOfLusche >= 6) {
      nullB = true;
    } else if (noOfLusche >= 3 && noOfAces == 0 && noOfKings == 0 && noOfQueens == 0) {
      nullB = true;
    }
    return nullB;
  }

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
   * Position == REARHAND!!!!!!!
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
   * 
   * !!!!! Only call if you are not solo!!!!! !!!!! Only call if your position is MIDDLEHAND or
   * REARHAND !!!!!! returns position of my teammate
   * 
   * @param lgs
   * @param myPosition
   * @return position of my teammate
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

  private boolean isFirstCardInTrickFromTeammate() {
    if (getTeammatePosition() == Position.FOREHAND) {
      return true;
    } else {
      return false;
    }
  }

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
   *
   *
   * 
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
   * Position == MiddleHand!!!!!!!
   *
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
   * To use only after canTrickBeBeatenByMiddleHand
   *
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
   * Position == REARHAND!!!!!!!
   *
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
   * To use only after canTrickBeBeatenByRearHand
   *
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


