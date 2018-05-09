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
import de.skat3.gamelogic.Position;


@SuppressWarnings("serial")
public class RandomAi extends Ai implements Serializable {
  private boolean acceptHandGame;
  private AiHelper aiHelper;
  private Hand hand;
  private Contract contract;

  /**
   * Random generator for decision making.
   */
  private final Random random = new Random();

  /**
   * Creates a new instance of AIPlayer.
   */
  public RandomAi() {
    aiHelper = new AiHelper();
    setHandGame(random.nextBoolean());
    contract = selectContract();
  }

  private void setHandGame(boolean handGame) {
    acceptHandGame = handGame;
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
    boolean random1 = random.nextBoolean();
    boolean random2 = random.nextBoolean();
    boolean random3 = random.nextBoolean();
    boolean random4 = random.nextBoolean();
    boolean random5 = random.nextBoolean();

    if (acceptHandGame()) {
      if (contract == Contract.NULL) {
        if (random1 && random2 && random3 && random4) {
          return aiHelper.getHandGameNoMultipliers();
        } else {
          aiHelper.getHandGameNoMultipliers();
        }
      } else if (contract == Contract.GRAND) {
        if (random1 && random2 && random3 && random4) {
          return aiHelper.getHandGameNoMultipliers();
        } else {
          aiHelper.getNoHandGameNoMultipliers();
        }
      } else {
        if (random1 && random2 && random3 && random4 && random5) {
          return aiHelper.getSchneider();
        } else if (random1 && random2 && random3 && random4) {
          return aiHelper.getHandGameNoMultipliers();
        } else {
          aiHelper.getNoHandGameNoMultipliers();
        }
      }
    }
    return aiHelper.getNoHandGameNoMultipliers();
  }



  @Override
  public Contract chooseContract() {
    return contract;
  }

  @Override
  public Card chooseCard() {
    int i;
    ArrayList<Card> temp = new ArrayList<Card>();
    for (Card c : this.hand.cards) {
      if (c.isPlayable()) {
        temp.add(c.copy());
      }
    }
    i = random.nextInt(temp.size());
    return temp.get(i);
  }

  @Override
  public ReturnSkat selectSkat(Card[] skat) {
    ArrayList<Card> cards = new ArrayList<Card>();
    for (int i = 0; i < 10; i++) {
      cards.add(hand.getCards()[i]);
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
    return new ReturnSkat(new Hand(temp), newSkat);
  }

  @Override
  public void setHand(Hand hand) {
    this.hand = new Hand(hand.cards);
  }

  @Override
  public void setPosition(Position position) {
    // Position is irrelevant
  }

  @Override
  public void setIsSolo(boolean isSolo) {
    // isSolo is irrelevant
  }

  @Override
  public Hand getHand() {
    return this.hand;
  }

  private Contract selectContract() {
    Contract[] possibleValues = Contract.class.getEnumConstants();
    int randomIndex = random.nextInt(possibleValues.length);

    return possibleValues[randomIndex];
  }

}
