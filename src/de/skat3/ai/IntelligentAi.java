package de.skat3.ai;

import de.skat3.gamelogic.AdditionalMultipliers;
import de.skat3.gamelogic.Card;
import de.skat3.gamelogic.Contract;
import de.skat3.gamelogic.Hand;
import de.skat3.gamelogic.Player;
import de.skat3.gamelogic.Position;
import de.skat3.gamelogic.Suit;
import de.skat3.gamelogic.Value;
import de.skat3.main.LocalGameState;

public class IntelligentAi extends Ai {

  /**
   * private static final long serialVersionUID = 1L;
   */
  AiHelper aiHelper;
  Contract contract;
  LocalGameState lgs;

  public IntelligentAi() {
    this.aiHelper = new AiHelper();
  }


  public void setPosition(Position position) {
    this.position = position;
  }

  @Override
  public boolean acceptBid(int bid) {

    int maxBid = hand.getMaximumBid(chooseContract());
    int noOfJacks = AiHelper.countJacks(this.hand.cards);
    int noOfTrumps = AiHelper.countTrumps(this.hand.cards, chooseContract());
    Suit mostFrequentSuitColor = aiHelper.getMostFrequentSuit();

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

    if (maxBid > 0) {
      switch (mostFrequentSuitColor) {
        case CLUBS:
          contract = Contract.CLUBS;
          break;
        case SPADES:
          contract = Contract.SPADES;
          break;
        case HEARTS:
          contract = Contract.HEARTS;
          break;
        case DIAMONDS:
          contract = Contract.DIAMONDS;
          break;
      }
    }
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
    // Recherchieren bei welcher anzahl von Karten es sinnvoll ist offen zu spielen
    int noOfTrumps = AiHelper.countTrumps(this.hand.cards, chooseContract());
    int noOfAces = AiHelper.countAces(this.hand.cards);
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
  public Contract chooseContract() {
    return contract;
  }

  @Override
  public ReturnSkat selectSkat(Card[] skat) {
    // TODO Auto-generated method stub
    return null;
  }


  private Card playForeHand() {
    // 1: check, if there are still trump cards out

    // FIXME
    // if (card.getValue() == Value.JACK || card.isTrump(contract)) {
    // return card;
    // }

    // do i have any aces?
    for (int i = 0; i < this.hand.cards.length; i++) {
      if (this.hand.cards[i].getValue() == Value.ACE) {
        return this.hand.cards[i];
      } else if (this.hand.cards[i].getValue() == Value.TEN) {
        return this.hand.cards[i];
      } else if (this.hand.cards[i].getValue() == Value.KING)
        return this.hand.cards[i];
    }

    int help = 0;
    // fallback: just play the first valid card
    for (int i = 0; i < this.hand.cards.length; i++) {
      if (this.hand.cards[i].isPlayable())
        help = i;
    }
    return this.hand.cards[help];
  }

  private Card playMiddlehandCard() {
    // FIXME
    if (position.equals(Position.MIDDLEHAND)) {
      // "kurzer Weg, lange Farbe zum Freund"
      Suit longSuit = aiHelper.getMostCommonSuit();
      int firstIndex = aiHelper.getFirstIndexOfSuit(longSuit);
      if (longSuit != null && this.hand.cards[firstIndex].getValue() == Value.ACE) {
        return this.hand.cards[firstIndex];
      }
      int lastIndex = aiHelper.getLastIndexOfSuit(aiHelper.getMostFrequentSuit(longSuit));
      return this.hand.cards[lastIndex];
    } else if (position == Position.REARHAND) {
      // "langer Weg, kurze Farbe"
      int minCount = 9;
      Card result = null;
      for (Card card : this.hand.cards) {
        if (result == null || result.isTrump(lgs.getContract())) {
          result = card;
          continue;
        }


        int amountOfCardsOfSameSuit = aiHelper.getAmountOfCardsWithSameSuit(card);
        if (amountOfCardsOfSameSuit < minCount
            && !(amountOfCardsOfSameSuit == 1 && card.getValue() == Value.TEN)) {
          result = card;
          minCount = amountOfCardsOfSameSuit;
          continue;
        }
        if (amountOfCardsOfSameSuit == minCount
            && !(amountOfCardsOfSameSuit == 1 && card.getValue() == Value.ACE)) {
          result = card;
          continue;
        }
        if (card.getSuit() == result.getSuit() && amountOfCardsOfSameSuit == minCount) {
          result = card;
          continue;
        }
      }
      return result;
    }
    // fallback: take the first valid card (which is a trump, if there still
    // is one)
    for (Card card : this.hand.cards) {
      if (card.isPlayable()) {
        return card;
      }
    }

    return this.hand.cards[0];

  }

  private Card playRearhandCard() {
    // fallback: take the first valid card (which is a trump, if there still
    // is one)
    for (Card c : this.hand.cards) {
      if (c.isPlayable()) {
        return c;
      }
    }

    return this.hand.cards[0];
  }


  @Override
  public void setHand(Hand hand) {
    this.hand = new Hand(hand.cards);

  }


}
