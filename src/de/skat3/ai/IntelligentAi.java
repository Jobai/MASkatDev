package de.skat3.ai;

import java.util.ArrayList;
import java.util.Arrays;
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
	final int NINE = 0;
	final int KING = 4;
	final int QUEEN = 3;
	final int ACE = 11;
	final int TEN = 10;
	final int JACK = 2;
	/**
	 * private static final long serialVersionUID = 1L;
	 */
	AiHelper aiHelper;
	LocalGameState lgs;

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
	public void setPosition(Position position) {
		this.position = position;
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

	// TODO check if return null possible
	@Override
	public Contract chooseContract() {
		Card[] cards = hand.getCards();
		Contract contract = null;

  @Override
  public Contract chooseContract() {
    // TODO
    return null;
  }
		if (aiHelper.checkGrand(cards)) {
			contract = Contract.GRAND;
			return contract;
		} else if (aiHelper.checkNull(cards)) {
			contract = Contract.NULL;
			return contract;
		} else if (aiHelper.checkSuit(cards, Suit.SPADES)) {
			contract = Contract.SPADES;
			return contract;
		} else if (aiHelper.checkSuit(cards, Suit.DIAMONDS)) {
			contract = Contract.DIAMONDS;
			return contract;

		} else if (aiHelper.checkSuit(cards, Suit.HEARTS)) {
			contract = Contract.HEARTS;
			return contract;
		} else if (aiHelper.checkSuit(cards, Suit.CLUBS)) {
			contract = Contract.CLUBS;
			return contract;
		}
		return contract;
}

	@Override
	public boolean acceptBid(int bid) {

    /*
     * if (maxBid > 0) { switch (mostFrequentSuitColor) { case CLUBS: contract = Contract.CLUBS;
     * break; case SPADES: contract = Contract.SPADES; break; case HEARTS: contract =
     * Contract.HEARTS; break; case DIAMONDS: contract = Contract.DIAMONDS; break; } }
     */
    return false;
  }
		Card[] cards = this.hand.getCards();
		int maxBidSpades = hand.getMaximumBid(Contract.SPADES);
		int maxBidDiamonds = hand.getMaximumBid(Contract.DIAMONDS);
		int maxBidHearts = hand.getMaximumBid(Contract.HEARTS);
		int maxBidClubs = hand.getMaximumBid(Contract.CLUBS);
		int maxBidGrand = hand.getMaximumBid(Contract.GRAND);
		int maxBidNull = hand.getMaximumBid(Contract.NULL);
		
		 if(bid < maxBidGrand && aiHelper.checkGrand(cards)) {
			return true;
		 } else if (bid < maxBidNull && aiHelper.checkNull(cards)) {
			 return true;
		} else if (bid < maxBidSpades && aiHelper.checkSuit(cards, Suit.SPADES)) {
			return true;
		} else if (bid < maxBidDiamonds && aiHelper.checkSuit(cards, Suit.DIAMONDS)) {
			return true;
		} else if (bid < maxBidHearts && aiHelper.checkSuit(cards, Suit.HEARTS)) {
			return true;
		} else if (bid < maxBidClubs && aiHelper.checkSuit(cards, Suit.CLUBS)) {
			return true;
		} else {
			return false;
		}
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
		ArrayList<Card> list = new ArrayList<Card>(Arrays.asList(cards));
		boolean handGame = false;
		int noOfTrumps = aiHelper.countTrumps(cards, chooseContract());
		int noOfJacks = aiHelper.countJacks(cards);
		int noOfAces = aiHelper.countAces(cards);
		int noOfTens = aiHelper.countTens(cards);
		int noOfLusche = aiHelper.countSevens(cards) + aiHelper.countEights(cards) + aiHelper.countNines(cards);
		Card lowestCard = aiHelper.getLowestCard(list);
		Card highestCard = aiHelper.getHighestCard(list);

		if (noOfJacks == 4) {
				handGame = true;
		} else if (noOfJacks >= 3 && noOfAces >= 3 && noOfTens >= 2) {
				handGame = true;
		} else if (noOfJacks >= 2 && noOfAces >= 3 && lowestCard.getTrickValue() >= QUEEN) {
				handGame = true;
		} else if (noOfLusche >= 7) {
				handGame = true;
		} else if (noOfLusche >= 5 && highestCard.getTrickValue() <= QUEEN) {
				handGame = true;
		} else if ((noOfTrumps >= 7)) {
				handGame = true;
		} else if (noOfTrumps >= 5 && noOfAces >= 3) {
				handGame = true;
		} else if (noOfTrumps >= 4 && lowestCard.getTrickValue() >= KING) {
				handGame = true;
		}
		return handGame;
	}

	@Override
	public AdditionalMultipliers chooseAdditionalMultipliers() {
		// TODO Auto-generated method stub
		return null;
	}

	// TODO change first check
	@Override
	public ReturnSkat selectSkat(Card[] skat) {
		ArrayList<Card> cards = new ArrayList<Card>();

		
			if (skat[0].getTrickValue() != 2 && skat[1].getTrickValue() != 2) {
				return new ReturnSkat(hand, skat);
			} else if (skat[0].getTrickValue() <= NINE && skat[1].getTrickValue() <= NINE) {
				for (int i = 0; i < 10; i++) {
					cards.add(this.hand.cards[i]);
				}
				cards.add(skat[0]);
				cards.add(skat[1]);
				Card[] newSkat = new Card[2];
				Card highestCard = aiHelper.getHighestCard(cards);
				newSkat[0] = highestCard;
				cards.remove(highestCard);
				Card highestCard2 = aiHelper.getLowestCard(cards);
				newSkat[1] = highestCard2;
				cards.remove(highestCard2);
				newSkat[0] = skat[0];
				newSkat[1] = skat[1];
				Card[] temp = new Card[10];
				for (int i = 0; i < temp.length; i++) {
					temp[i] = cards.get(i).copy();
				}
				return new ReturnSkat(new Hand(temp), newSkat);
			}else {
				for (int i = 0; i < 10; i++) {
				cards.add(this.hand.cards[i]); // FIXME NullPointer
				}
				cards.add(skat[0]);
				cards.add(skat[1]);
				Card[] newSkat = new Card[2];
				Card lowestCard = aiHelper.getLowestCard(cards);
				newSkat[0] = lowestCard;
				cards.remove(lowestCard);
				Card lowestCard2 = aiHelper.getLowestCard(cards);
				newSkat[1] = lowestCard2;
				cards.remove(lowestCard2);
				Card[] temp = new Card[10];
				for (int i = 0; i < temp.length; i++) {
					temp[i] = cards.get(i).copy();
				}
				return new ReturnSkat(new Hand(temp), newSkat);
		}
	}

	private Card playForeHand() {
		Card[] cards = hand.getCards();
		// 1: check, if there are still trump cards out

		// FIXME
		// if (card.getValue() == Value.JACK || card.isTrump(contract)) {
		// return card;
		// }

  private Card playForeHand() {
    Card[] cards = hand.getCards();
    return aiHelper.playMostExpensiveCardThatIsNotTrumpIfPossible(cards, lgs.getContract());
  }
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
		Card[] cards = hand.getCards();
		// "kurzer Weg, lange Farbe zum Freund"
		Suit biggestSuit = aiHelper.getMostFrequentSuit(cards);
		int firstIndex = aiHelper.getFirstIndexOfSuit(cards, biggestSuit);
		if (cards[firstIndex].getValue() == Value.ACE) {
			return cards[firstIndex];
		} else {
			Suit secondBiggestSuit = aiHelper.getMostFrequentSuit(cards, biggestSuit);
			int lastIndex = aiHelper.getLastIndexOfSuit(cards, secondBiggestSuit);
			return cards[lastIndex];
		}
	}

	private Card playRearhandCard() {
		Card[] cards = hand.getCards();
		// "langer Weg, kurze Farbe"
		int minCount = 9;
		Card result = null;
		for (Card card : hand.cards) {
			if (result == null || result.isTrump(lgs.getContract())) {
				result = card;
				continue;
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
			int amountOfCardsOfSameSuit = aiHelper.getAmountOfCardsWithSameSuit(card, hand.getCards());
			if (amountOfCardsOfSameSuit < minCount && !(amountOfCardsOfSameSuit == 1 && card.getValue() == Value.TEN)) {
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

		return aiHelper.getAnyPlayableCard(cards);
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
