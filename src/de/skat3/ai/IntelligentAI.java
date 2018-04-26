package de.skat3.ai;
/**
 * @author emrecura
 */
import java.util.ArrayList;
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
  
  
  public IntelligentAI(Player player) {
    super(player);
  }
  AiHelper aiHelper;
  Player p;
  Hand h;
  Card c;
  CardDeck cd;
  RoundInstance r;
  GameController gc;
  String name;
  CardDeck decks;
  Contract con;

  // Dummy Werte

  // wenn Ki gewonnen hat shwarz schneider oder offen
  ArrayList<AdditionalMultipliers> additionalMultiplayerList;
  //
  ArrayList<Contract> contractList;
  //
  ArrayList<Card> cardList;



	
	public CardDeck discardSkat() {
		//TODO
		return null;
	}

	
	public Card playCard() {
		
	if (r.getFirstCard() == null && r.getSecondCard()  == null && r.getThirdCard() == null) {
		if (c.getTrickValue() < 1) {
			return chooseCard();
		}
		return chooseTrickCard();
	}
	if (r.determineTrickWinner().equals(r.getMiddlehand())) {
		return playMiddlehandCard();
	}
	return playRearhandCard();

}

	@Override
	public Card chooseCard() {
		// at present, game is always opened with trump - using a jack, if
		// possible
		Card[] cards = h.getCards();
		for(int i = 0;i< cards.length;i++) {
			if(cards[i].isTrump(chooseContract())) {
				return cards[i];
			}
		}
			if (c == null) {
				// should not happen - if there is no jack, there should be at
				// least one trump suit card
				// just to make sure that a card is played
				c = cards[0];
				return c;
			}
		
		// from here onwards: first card must be a jack
		if (cards[0].getSuit() == Suit.CLUBS) {
			if (cards[1].getSuit() != Suit.SPADES
					|| cards[1].getValue() != Value.JACK) {
				return cards[0];
			}
			if (cards[2].getSuit() != Suit.HEARTS
					|| cards[2].getValue() != Value.JACK) {
				return cards[1];
			}
			if (cards[3].getSuit() != Suit.DIAMONDS
					|| cards[3].getValue() != Value.JACK) {
				return cards[2];
			}
			return cards[3];
		}
		if (cards[0].getSuit() == Suit.SPADES) {
			if (cards[1].getSuit() != Suit.HEARTS
					|| cards[1].getValue() != Value.JACK) {
				return cards[0];
			}
			if (cards[2].getSuit() != Suit.HEARTS
					|| cards[2].getValue() != Value.JACK) {
				return cards[0];
			}
		}	
		return cards[0];
		
	}
		
	
	public Card chooseTrickCard() {
		Card[] cards = h.getCards();
		// 1: check, if there are still trump cards out
			if (chooseCard().getValue() == Value.JACK) {
				return chooseCard();
			}
			//TODO get TrumpCard
			if (c != null) {
				return c;
			}
			
			// do i have any aces?
		for (int i = 0; i< cards.length;i++) {
			if (c.getValue() == Value.ACE) {
				return c;
			}
			if (c.getValue() == Value.TEN) {
				return c;
			}
			if (c.getValue() == Value.KING)
				return c;
			}
	
	int help = 0;
		// fallback: just play the first valid card
			for(int i = 0; i< cards.length;i++) {
				if(cards[i].isPlayable())
					help = i;
			}
		return cards[help];
	}

	public Card playMiddlehandCard() {
		
		Card[] cards = cd.getCards();
		if (gc.getDealer() == r.getMiddlehand()) {
			// "kurzer Weg, lange Farbe zum Freund"
			Suit longSuit = aiHelper.getMostFrequentSuit(c.getSuit());
			int firstIndex = aiHelper.getFirstIndexOfSuit(longSuit);
			if (longSuit != null &&  cards[firstIndex].getValue() == Value.ACE) {
				return cards[firstIndex];
			}
			int lastIndex = aiHelper.getLastIndexOfSuit(aiHelper.getMostFrequentSuit(longSuit));
			return cards[lastIndex];
		} else if (p.getPosition() == Position.REARHAND) {
			// "langer Weg, kurze Farbe"
			int minCount = 9;
			Card result = null;
			for (Card c : cards) {
				if (result == null || result.isTrump(r.getContract())) {
					result = c;
					continue;
				}
				if (c.getSuit().length < minCount
						&& !(c.getSuit().length == 1 && 
						c.getValue() == Value.TEN)) {
					result = c;
					minCount = c.getSuit().length;
					continue;
				}
				if (c.getSuit().length == minCount
						&& !(c.getSuit().length == 1 && 
						c.getValue() == Value.ACE))  {
					result = c;
					continue;
				}
				if (c.getSuit() == result.getSuit()
						&& c.getSuit().length == minCount) {
					result = c;
					continue;
				}
			}
			return result;
		}
		// fallback: take the first valid card (which is a trump, if there still
		// is one)
		for (Card c : cards) {
			if (c.isPlayable()) {
				return c;
			}
		}
		
		return cards[0];
		
	}

	public Card playRearhandCard() {
		// fallback: take the first valid card (which is a trump, if there still
		// is one)
		Card[] cards = h.getCards();
		for (Card c : cards) {
			if (c.isPlayable()) {
				return c;
			}
		}
		
		return cards[0];
	}

	@Override
	public boolean acceptBid(int bid) {
		
		int maxBid = h.getMaximumBid(chooseContract());
		int noOfJacks = AiHelper.countJacks(cd.getCards());
		int noOfTrumps = AiHelper.countTrumps(cd.getCards(), chooseContract());
		Suit mostFrequentSuitColor = aiHelper.getMostFrequentSuit();

		// only play, if I have at least 1 jack and 4 color cards or
		// 2 jacks and 3 color cards
		if (noOfJacks < 3 && noOfTrumps < 4) {
			maxBid = 0;
		} else if (noOfJacks < 2 && noOfTrumps < 5) {
			maxBid = 0;
		} else if (noOfJacks < 1 && noOfTrumps < 6) {
			maxBid = 0;
		} else  {
			maxBid = 0;
		}
	
		if (maxBid > 0) {
			switch (mostFrequentSuitColor) {
			case CLUBS:
				con = Contract.CLUBS;
				break;
			case SPADES:
				con = Contract.SPADES;
				break;
			case HEARTS:
				con = Contract.HEARTS;
				break;
			case DIAMONDS:
				con = Contract.DIAMONDS;
				break;
			}
		}
		return false;
	}


	@Override
	public boolean acceptHandGame() {
		// Recherchieren bei welcher anzahl von Karten es sinnvoll ist offen zu spielen	
		int noOfTrumps = AiHelper.countTrumps(cd.getCards(), chooseContract());
		int noOfAces = AiHelper.countAces(cd.getCards());
		if(noOfTrumps >= 8) {
			return true;
		}else if(noOfTrumps +noOfAces >= 8) {
			return true;
		}else {
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
		return con;
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
}
