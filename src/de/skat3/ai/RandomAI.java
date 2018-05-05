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
public class RandomAI extends Ai implements Serializable {
  private boolean acceptHandGame;
  private AiHelper aiHelper;
  private Hand hand;

  /**
   * Random generator for decision making.
   */
  private final Random random = new Random();

  /**
   * Creates a new instance of AIPlayer.
   */
  public RandomAI() {
    aiHelper = new AiHelper();
    setHandGame(random.nextBoolean());
  }

  private void setHandGame(boolean handGame) {
    acceptHandGame= handGame;
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

}
