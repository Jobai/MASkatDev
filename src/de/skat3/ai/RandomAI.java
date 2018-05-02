package de.skat3.ai;


import java.io.Serializable;
import java.util.ArrayList;
/**
 * Author Emre Cura
 */
import java.util.Random;
import de.skat3.gamelogic.AdditionalMultipliers;
import de.skat3.gamelogic.Card;
import de.skat3.gamelogic.Contract;
import de.skat3.gamelogic.Player;
import de.skat3.main.SkatMain;


public class RandomAI extends Ai implements Serializable {
  Card[] cards;
  boolean acceptHandGame;
  AiHelper aiHelper;


  /**
   * Random generator for decision making.
   */
  private final Random random = new Random();

  /**
   * Creates a new instance of AIPlayer.
   */
  public RandomAI(Player player) {
    super(player);
    aiHelper = new AiHelper(player);
    cards = ai.getHand().getCards();
    acceptHandGame = random.nextBoolean();
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
    AdditionalMultipliers[] multipliersArray = aiHelper.getAllPossibleMultipliers(acceptHandGame);
    int randomIndex = random.nextInt(multipliersArray.length);

    return multipliersArray[randomIndex];
  }

  @Override
  public Contract chooseContract() {
    Contract[] possibleValues = Contract.class.getEnumConstants();
    int randomIndex = random.nextInt(possibleValues.length);

    return possibleValues[randomIndex];
  }

  @Override
  public Card chooseCard() {
    int i;
    ArrayList<Card> temp = new ArrayList<Card>();
    for (Card c : SkatMain.lgs.getLocalHand().cards) {
      if (c.isPlayable()) {
        temp.add(c.copy());
      }
    }
    Random rand = new Random();
    i = rand.nextInt(temp.size());
    return temp.get(i);
  }

  @Override
  public Player getPlayer() {
    return this.ai;
  }

  @Override
  public Card[] selectSkat(Card[] skat) {
    // TODO Auto-generated method stub
    return null;
  }


  // 2. was braucht RandomAi von lgs

  // 3. was ist kontra/rekontra? braucht RandomAI etwas davon?
  // soll Ai kotra/rekotra "announcen" können?

  // 4. getPlayer() right?)

  // 5. how exaclty selectSkat works

  //
  // public boolean pickUpSkat() {
  // return random.nextBoolean();
  // }
  //
  // public boolean playGrandHand() {
  // return random.nextBoolean();
  // }
  //
  // public void getContract() {
  // // TODO
  // }
  //
  // public Integer bidMore(int nextBidValue) {
  // int result = 0;
  //
  // if (random.nextBoolean()) {
  //
  // result = nextBidValue;
  // }
  //
  // return result;
  // }
  //
  // public Boolean holdBid(int currBidValue) {
  // return random.nextBoolean();
  // }
  //
  // public void startGame() {
  // // do nothing
  // }
  //
  // public Card playCard() {
  // // first find all possible cards
  // Card[] possibleCards = decks.getCards();
  //
  // // then choose a random one
  // int index = random.nextInt(possibleCards.length);
  //
  // return possibleCards[index];
  // }
  //
  // public CardDeck getCardsToDiscard() {
  // // just discard two random cards
  // // TODO
  //
  // return null;
  // }
  //
  // public void announceGame() {
  // // TODO
  // // Select Game Mode
  // // select a random game type (without RAMSCH and PASSED_IN)
  // }
  //
  // public void preparateForNewGame() {
  // // nothing to do for AIPlayer
  // }
  //
  //
  // public void finalizeGame() {
  // // nothing to do for AIPlayer
  // }
  //
  // public Boolean callContra() {
  // return random.nextBoolean();
  // }
  //
  // public Boolean callRe() {
  // return random.nextBoolean();
  // }
  //
  // @Override
  // public Player getPlayer() {
  // // TODO Auto-generated method stub
  // return null;
  // }
  //
  // @Override
  // public Card[] selectSkat(Card[] skat) {
  // //
  // return null;
  // }
  //
}
