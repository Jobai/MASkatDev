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

public class RandomAI implements AiControllerInterface {
  String name;
  CardDeck decks;
  Player ai;

  // Dummy Werte

  // wenn Ki gewonnen hat shwarz schneider oder offen
  ArrayList<AdditionalMultipliers> additionalMultiplayerList;
  //
  ArrayList<Contract> contractList;
  //
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
    this.ai = ai;
    // TODO initialize those lists
    additionalMultiplayerList = null;
    contractList = null;
    cardList = null;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public boolean acceptBid(int bid) {
    return random.nextBoolean();
  }

  @Override
  public boolean acceptHandGame() {
    return random.nextBoolean();
  }

  @Override
  public AdditionalMultipliers chooseAdditionalMultipliers() {
    int randomIndex = random.nextInt(additionalMultiplayerList.size());
    return additionalMultiplayerList.get(randomIndex);
  }

  @Override
  public Contract chooseContract() {
    int randomIndex = random.nextInt(contractList.size());
    return contractList.get(randomIndex);
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

}
