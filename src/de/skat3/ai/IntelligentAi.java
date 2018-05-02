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

  Player player;
  AiHelper aiHelper;
  Hand hand;
  Card cards[];
  Contract contract;
  LocalGameState lgs;

  public IntelligentAi(Player player) {
    super(player);
    this.aiHelper = new AiHelper(player);
    this.hand = player.getHand();
    this.cards = hand.getCards();
    this.player = player;
    this.contract = lgs.getContract();
  }

  @Override
  public boolean acceptBid(int bid) {

    int maxBid = hand.getMaximumBid(chooseContract());
    int noOfJacks = AiHelper.countJacks(cards);
    int noOfTrumps = AiHelper.countTrumps(cards, chooseContract());
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

    if (player.getPosition().equals(Position.FOREHAND)) {
      return playForeHand();
    } else if (player.getPosition().equals(Position.MIDDLEHAND)) {
      return playMiddlehandCard();
    } else {
      return playRearhandCard();
    }
  }

  @Override
  public boolean acceptHandGame() {
    // Recherchieren bei welcher anzahl von Karten es sinnvoll ist offen zu spielen
    int noOfTrumps = AiHelper.countTrumps(cards, chooseContract());
    int noOfAces = AiHelper.countAces(cards);
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
  public Player getPlayer() {
    return this.ai;
  }

  @Override
  public Card[] selectSkat(Card[] skat) {
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
    for (int i = 0; i < cards.length; i++) {
      if (cards[i].getValue() == Value.ACE) {
        return cards[i];
      } else if (cards[i].getValue() == Value.TEN) {
        return cards[i];
      } else if (cards[i].getValue() == Value.KING)
        return cards[i];
    }

    int help = 0;
    // fallback: just play the first valid card
    for (int i = 0; i < cards.length; i++) {
      if (cards[i].isPlayable())
        help = i;
    }
    return cards[help];
  }

  private Card playMiddlehandCard() {
    // FIXME
    if (player.getPosition().equals(Position.MIDDLEHAND)) {
      // "kurzer Weg, lange Farbe zum Freund"
      Suit longSuit = aiHelper.getMostCommonSuit();
      int firstIndex = aiHelper.getFirstIndexOfSuit(longSuit);
      if (longSuit != null && cards[firstIndex].getValue() == Value.ACE) {
        return cards[firstIndex];
      }
      int lastIndex = aiHelper.getLastIndexOfSuit(aiHelper.getMostFrequentSuit(longSuit));
      return cards[lastIndex];
    } else if (player.getPosition() == Position.REARHAND) {
      // "langer Weg, kurze Farbe"
      int minCount = 9;
      Card result = null;
      for (Card card : cards) {
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
    for (Card card : cards) {
      if (card.isPlayable()) {
        return card;
      }
    }

    return cards[0];

  }

  private Card playRearhandCard() {
    // fallback: take the first valid card (which is a trump, if there still
    // is one)
    for (Card c : cards) {
      if (c.isPlayable()) {
        return c;
      }
    }

    return cards[0];
  }


}
