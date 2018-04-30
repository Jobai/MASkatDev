package de.skat3.ai;

import de.skat3.gamelogic.AdditionalMultipliers;
import de.skat3.gamelogic.Card;
import de.skat3.gamelogic.CardDeck;
import de.skat3.gamelogic.Contract;
import de.skat3.gamelogic.GameController;
import de.skat3.gamelogic.Hand;
import de.skat3.gamelogic.Player;
import de.skat3.gamelogic.Position;
import de.skat3.gamelogic.RoundInstance;
import de.skat3.gamelogic.Suit;
import de.skat3.gamelogic.Value;


public class IntelligentAI extends Ai {
  AiHelper aiHelper;
  Hand hand;
  Card card;
  Player player;
  Contract contract;

  // TODO how to initialize those?
  CardDeck cardDeck;
  RoundInstance roundInstance;
  GameController gameController;


  public IntelligentAI(Player player) {
    super(player);
    aiHelper = new AiHelper(player);
    hand = player.getHand();
    this.player = player;
    // con = lgs.getContract();

  }

  @Override
  public boolean acceptBid(int bid) {

    int maxBid = hand.getMaximumBid(chooseContract());
    int noOfJacks = AiHelper.countJacks(cardDeck.getCards());
    int noOfTrumps = AiHelper.countTrumps(cardDeck.getCards(), chooseContract());
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
  public boolean acceptHandGame() {
    // Recherchieren bei welcher anzahl von Karten es sinnvoll ist offen zu spielen
    int noOfTrumps = AiHelper.countTrumps(cardDeck.getCards(), chooseContract());
    int noOfAces = AiHelper.countAces(cardDeck.getCards());
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

  @Override
  public Card chooseCard() {
    // at present, game is always opened with trump - using a jack, if
    // possible
    Card[] cards = hand.getCards();
    for (int i = 0; i < cards.length; i++) {
      if (cards[i].isTrump(chooseContract())) {
        return cards[i];
      }
    }
    if (card == null) {
      // should not happen - if there is no jack, there should be at
      // least one trump suit card
      // just to make sure that a card is played
      card = cards[0];
      return card;
    }

    // from here onwards: first card must be a jack
    if (cards[0].getSuit() == Suit.CLUBS) {
      if (cards[1].getSuit() != Suit.SPADES || cards[1].getValue() != Value.JACK) {
        return cards[0];
      }
      if (cards[2].getSuit() != Suit.HEARTS || cards[2].getValue() != Value.JACK) {
        return cards[1];
      }
      if (cards[3].getSuit() != Suit.DIAMONDS || cards[3].getValue() != Value.JACK) {
        return cards[2];
      }
      return cards[3];
    }
    if (cards[0].getSuit() == Suit.SPADES) {
      if (cards[1].getSuit() != Suit.HEARTS || cards[1].getValue() != Value.JACK) {
        return cards[0];
      }
      if (cards[2].getSuit() != Suit.HEARTS || cards[2].getValue() != Value.JACK) {
        return cards[0];
      }
    }
    return cards[0];

  }

  private Card chooseTrickCard() {
    Card[] cards = hand.getCards();
    // 1: check, if there are still trump cards out
    if (chooseCard().getValue() == Value.JACK) {
      return chooseCard();
    }
    // TODO get TrumpCard
    if (card != null) {
      return card;
    }

    // do i have any aces?
    for (int i = 0; i < cards.length; i++) {
      if (card.getValue() == Value.ACE) {
        return card;
      }
      if (card.getValue() == Value.TEN) {
        return card;
      }
      if (card.getValue() == Value.KING)
        return card;
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
    Card[] cards = cardDeck.getCards();
    if (gameController.getDealer() == roundInstance.getMiddlehand()) {
      // "kurzer Weg, lange Farbe zum Freund"
      Suit longSuit = aiHelper.getMostFrequentSuit(card.getSuit());
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
        if (result == null || result.isTrump(roundInstance.getContract())) {
          result = card;
          continue;
        }


        // FIXME c.getSuit().length ist eine Konstante,
        // das ist der richtige Zugriff drauf
        // int suitLength = Suit.values().length;
        // FIXME warum vergleichst du 2 Konstanten?
        if (aiHelper.getAmountOfSuitsByCardSuit(card) < minCount
            && !(card.getSuit().length == 1 && card.getValue() == Value.TEN)) {
          result = card;
          minCount = card.getSuit().length;
          continue;
        }
        if (card.getSuit().length == minCount
            && !(card.getSuit().length == 1 && card.getValue() == Value.ACE)) {
          result = card;
          continue;
        }
        if (card.getSuit() == result.getSuit() && card.getSuit().length == minCount) {
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
    Card[] cards = hand.getCards();
    for (Card c : cards) {
      if (c.isPlayable()) {
        return c;
      }
    }

    return cards[0];
  }

  private Card playCard() {

    if (roundInstance.getFirstCard() == null && roundInstance.getSecondCard() == null
        && roundInstance.getThirdCard() == null) {
      if (card.getTrickValue() < 1) {
        return chooseCard();
      }
      return chooseTrickCard();
    }
    if (roundInstance.determineTrickWinner().equals(roundInstance.getMiddlehand())) {
      return playMiddlehandCard();
    }
    return playRearhandCard();

  }
}
