package de.skat3.ai;


import java.util.ArrayList;
/**
 * Author Emre Cura
 */
import java.util.Random;
import de.skat3.gamelogic.AdditionalMultipliers;
import de.skat3.gamelogic.Card;
import de.skat3.gamelogic.CardDeck;
import de.skat3.gamelogic.Contract;
import de.skat3.gamelogic.Player;

public class RandomAI extends Ai {
  CardDeck decks;
  boolean acceptHandGame;


  ArrayList<Card> cardList;
  //


  /**
   * Random generator for decision making.
   */
  private final Random random = new Random();

  /**
   * Creates a new instance of AIPlayer.
   */
  public RandomAI(Player ai) {
    super(ai);
    acceptHandGame = random.nextBoolean();
    // TODO initialize those lists
    cardList = null;
  }

  @Override
  public boolean acceptBid(int bid) {
    return random.nextBoolean();
  }

  @Override
  public boolean acceptHandGame() {
    return acceptHandGame;
  }

  @Override
  public AdditionalMultipliers chooseAdditionalMultipliers() {
    AdditionalMultipliers[] multipliersArray = getAllPossibleMultipliers(acceptHandGame);
    int randomIndex = random.nextInt(multipliersArray.length);

    return multipliersArray[randomIndex];
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
    if (acceptHandGame) {
      AdditionalMultipliers multiplierOpen = new AdditionalMultipliers();
      multiplierOpen.setHandGame(true);
      AdditionalMultipliers[] multiplierOpenArray = new AdditionalMultipliers[1];
      multiplierOpenArray[0] = multiplierOpen;
      return multiplierOpenArray;
    } else {
      AdditionalMultipliers noMultipliers = new AdditionalMultipliers(false, false, false);
      AdditionalMultipliers multiplierSchneider = new AdditionalMultipliers(true, false, false);
      AdditionalMultipliers multiplierSchwarz = new AdditionalMultipliers(true, true, false);
      AdditionalMultipliers multiplierHand = new AdditionalMultipliers(true, true, true);

      AdditionalMultipliers[] multiplayers = new AdditionalMultipliers[4];

      multiplayers[0] = noMultipliers;
      multiplayers[1] = multiplierSchneider;
      multiplayers[2] = multiplierSchwarz;
      multiplayers[3] = multiplierHand;

      return multiplayers;
    }
  }

  @Override
  public Contract chooseContract() {
    Contract[] possibleValues = Contract.class.getEnumConstants();
    int randomIndex = random.nextInt(possibleValues.length);

    return possibleValues[randomIndex];
  }

  @Override
  public Card chooseCard() {
    int randomIndex = random.nextInt(cardList.size());
    return cardList.get(randomIndex);
  }


  public boolean pickUpSkat() {
    return random.nextBoolean();
  }

  public boolean playGrandHand() {
    return random.nextBoolean();
  }

  public void getContract() {
    // TODO
  }

  public Integer bidMore(int nextBidValue) {
    int result = 0;

    if (random.nextBoolean()) {

      result = nextBidValue;
    }

    return result;
  }

  public Boolean holdBid(int currBidValue) {
    return random.nextBoolean();
  }

  public void startGame() {
    // do nothing
  }

  public Card playCard() {
    // first find all possible cards
    Card[] possibleCards = decks.getCards();

    // then choose a random one
    int index = random.nextInt(possibleCards.length);

    return possibleCards[index];
  }

  public CardDeck getCardsToDiscard() {
    // just discard two random cards
    // TODO

    return null;
  }

  public void announceGame() {
    // TODO
    // Select Game Mode
    // select a random game type (without RAMSCH and PASSED_IN)
  }

  public void preparateForNewGame() {
    // nothing to do for AIPlayer
  }


  public void finalizeGame() {
    // nothing to do for AIPlayer
  }

  public Boolean callContra() {
    return random.nextBoolean();
  }

  public Boolean callRe() {
    return random.nextBoolean();
  }

  @Override
  public Player getPlayer() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Card[] selectSkat() {
    // TODO Auto-generated method stub
    return null;
  }

}
