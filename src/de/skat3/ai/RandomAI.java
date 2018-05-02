package de.skat3.ai;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
/**
 * Author Emre Cura
 */
import java.util.Random;
import de.skat3.gamelogic.AdditionalMultipliers;
import de.skat3.gamelogic.Card;
import de.skat3.gamelogic.Contract;
import de.skat3.gamelogic.Hand;
import de.skat3.gamelogic.Player;
import de.skat3.main.SkatMain;


public class RandomAI extends Ai implements Serializable {
  boolean acceptHandGame;
  AiHelper aiHelper;


  /**
   * Random generator for decision making.
   */
  private final Random random = new Random();

  /**
   * Creates a new instance of AIPlayer.
   */
  public RandomAI() {
    aiHelper = new AiHelper();
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
    for (Card c : this.hand.cards) {
      System.out.println(c + " " + c.isPlayable());
      if (c.isPlayable()) {
        temp.add(c.copy());
      }
    }
    Random rand = new Random();
    i = rand.nextInt(temp.size());
    this.hand.remove(temp.get(i));
    return temp.get(i);
  }

  @Override
  public ReturnSkat selectSkat(Card[] skat) {
    ArrayList<Card> cards = new ArrayList<Card>();
    for (int i = 0; i < 10; i++) {
      cards.add(this.hand.cards[i]);
    }
    cards.add(skat[0]);
    cards.add(skat[1]);
    Collections.shuffle(cards);
    Card[] temp = new Card[10];
    for (int i = 0; i < temp.length; i++) {
      temp[i] = cards.get(i).copy();
    }
    Card[] newSkat = new Card[2];
    newSkat[0] = cards.get(10);
    newSkat[1] = cards.get(11);
    return new ReturnSkat(new Hand(temp),newSkat);
  }

  @Override
  public void setHand(Hand hand) {
    this.hand = new Hand(hand.cards);

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
