package de.skat3.ai;

import java.util.ArrayList;
import java.util.Arrays;
import de.skat3.gamelogic.AdditionalMultipliers;
import de.skat3.gamelogic.Card;
import de.skat3.gamelogic.Contract;
import de.skat3.gamelogic.Hand;
import de.skat3.gamelogic.Position;
import de.skat3.gamelogic.Suit;
import de.skat3.main.LocalGameState;

@SuppressWarnings("serial")
public class IntelligentAi extends Ai {

  /**
   * private static final long serialVersionUID = 1L;
   */
  AiHelper aiHelper;
  LocalGameState lgs;
  boolean isSolo;

  public IntelligentAi() {
    this.aiHelper = new AiHelper();
  }

  @Override
  public void setIsSolo(boolean isSolo) {
    this.isSolo = isSolo;
  }

  @Override
  public void setPosition(Position position) {
    this.position = position;
  }

  @Override
  public void setHand(Hand hand) {
    this.hand = new Hand(hand.getCards());
  }

  @Override
  public Contract chooseContract() {
    // TODO
    return null;
  }

  // FIXME always return false
  @Override
  public boolean acceptBid(int bid) {
    Card[] cards = hand.getCards();
    int maxBid = hand.getMaximumBid(chooseContract());
    int noOfJacks = aiHelper.countJacks(cards);
    int noOfTrumps = aiHelper.countTrumps(cards, chooseContract());
    Suit mostFrequentSuitColor = aiHelper.getMostFrequentSuit(cards);

    // only play, if I have at least 1 jack and 4 color cards or
    // 2 jacks and 3 color cards
    if (noOfJacks < 3 && noOfTrumps < 4) {
      maxBid = 0;
    } else if (noOfJacks < 2 && noOfTrumps < 5) {
      maxBid = 0;
    } else if (noOfJacks < 1 && noOfTrumps < 6) {
      maxBid = 0;
    } else {
      maxBid = 0;
    }

    /*
     * if (maxBid > 0) { switch (mostFrequentSuitColor) { case CLUBS: contract = Contract.CLUBS;
     * break; case SPADES: contract = Contract.SPADES; break; case HEARTS: contract =
     * Contract.HEARTS; break; case DIAMONDS: contract = Contract.DIAMONDS; break; } }
     */
    return false;
  }


  @Override
  public Card chooseCard() {

    if (position.equals(Position.FOREHAND)) {
      return playForeHand();
    } else if (position.equals(Position.MIDDLEHAND)) {
      return playMiddlehandCard();
    } else {
      return playRearhandCard();
    }
  }

  @Override
  public boolean acceptHandGame() {
    Card[] cards = hand.getCards();
    // Recherchieren bei welcher anzahl von Karten es sinnvoll ist offen zu spielen
    int noOfTrumps = aiHelper.countTrumps(cards, chooseContract());
    int noOfAces = aiHelper.countAces(cards);
    if (noOfTrumps >= 8) {
      return true;
    } else if (noOfTrumps + noOfAces >= 8) {
      return true;
    } else {
      return false;
    }
  }

  @Override
  public AdditionalMultipliers chooseAdditionalMultipliers() {
    // TODO Auto-generated method stub
    return null;
  }



  @Override
  public ReturnSkat selectSkat(Card[] skat) {
    // TODO Auto-generated method stub
    return null;
  }


  private Card playForeHand() {
    Card[] cards = hand.getCards();
    return aiHelper.playMostExpensiveCardThatIsNotTrumpIfPossible(cards, lgs.getContract());
  }

  private Card playMiddlehandCard() {
    Card[] cards = hand.getCards();
    Card[] trick = lgs.getTrick();
    Contract contract = lgs.getContract();
    ArrayList<Card> cardList = new ArrayList<Card>(Arrays.asList(cards));

    boolean firstCardFromTeammate = aiHelper.isFirstCardInTrickFromTeammate(lgs, position);
    if (firstCardFromTeammate) {
      if (aiHelper.isCardExpensive(lgs.getTrick()[0],contract)) {
        return aiHelper.getHighestPlayableCardExcludeSuitIfPossible(cardList, null);
      } else {
        return aiHelper.getLowestPlayableCardExcludeSuitIfPossible(cardList, contract);
      }
    } else {
      boolean canBeBeaten = aiHelper.canTrickBeBeatenByMiddleHand(cards, trick, contract);
      if (canBeBeaten) {
        return aiHelper.winTrickAsMiddleHand(cards, trick, contract);
      } else {
        return aiHelper.getLowestPlayableCardExcludeSuitIfPossible(cardList, contract);
      }
    }
  }


  private Card playRearhandCard() {
    Card[] cards = hand.getCards();
    Contract contract = lgs.getContract();

    boolean teammateWinTrick = aiHelper.willTrickBeWonByTeammate(lgs, cards, position, isSolo);
    if (teammateWinTrick) {
      return aiHelper.playMostExpensiveCardThatIsNotTrumpIfPossible(cards, contract);
    } else {
      boolean canTrickBeBeaten =
          aiHelper.canTrickBeBeatenByRearHand(cards, lgs.getTrick(), contract);
      if (canTrickBeBeaten) {
        return aiHelper.winTrickAsRearHand(cards, lgs.getTrick(), contract);
      } else {
        return aiHelper.playLeastExpensiveCardThatIsNotTrumpIfPossible(cards, contract);
      }
    }
  }

}
