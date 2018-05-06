package de.skat.ai.RandomAiPlayer

/**
 * Author Emre Cura
 */
import java.util.Random;
import de.skat3.gamelogic.Player;
import de.skat3.gamelogic.Card;
import de.skat3.gamelogic.CardDeck;

public class RandomAI implements AbstractAIPlayer{
	CardDeck decks;
	Player ai;
	
	
	/**
	 * Random generator for decision making.
	 */
	private final Random random = new Random();

	/**
	 * Creates a new instance of AIPlayer.
	 */
	public RandomAI(Player ai) {

		this.ai = ai;
	}
	@Override
	public boolean pickUpSkat() {
		return random.nextBoolean();
	}

	@Override
	public boolean playGrandHand() {
		return random.nextBoolean();
	}

	@Override
	public void getContract() {
		//TODO
	}

	@Override
	public Integer bidMore( int nextBidValue) {
		int result = -1;

		if (random.nextBoolean()) {

			result = nextBidValue;
		}

		return result;
	}

	@Override
	public Boolean holdBid( int currBidValue) {
		return random.nextBoolean();
	}

	@Override
	public void startGame() {
		// do nothing
	}

	@Override
	public Card playCard() {


		// first find all possible cards
		 Card[] possibleCards = decks.getCards();

		// then choose a random one
		int index = random.nextInt(possibleCards.length);

		

		return possibleCards[index];
	}
	
	

	@Override
	public CardDeck getCardsToDiscard() {
		 

		// just discard two random cards
		//TODO

		return null;
	}

	@Override
	public void preparateForNewGame() {
		// nothing to do for AIPlayer
	}

	@Override
	public void finalizeGame() {
		// nothing to do for AIPlayer
	}

	@Override
	public Boolean callContra() {
		return random.nextBoolean();
	}

	@Override
	public Boolean callRe() {
		return random.nextBoolean();
	}
	
	
	
}
