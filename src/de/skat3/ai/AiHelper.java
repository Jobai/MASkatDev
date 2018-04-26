package de.skat3.ai;

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


public class AiHelper extends Ai{
	
	public AiHelper(Player player) {
		super(player);
	}

	CardDeck cd;
	/**
	 * Gets the suit with the most Cards in the CardsDeck (without considering
	 * the jacks!), without considering the given suit
	 *
	 * @param exclude
	 *            suit to exclude from calculating the most frequent suit
	 *            (normally the trump suit)
	 * @return Suit with most Cards in the CardList,<br>
	 *         the highest ranking suit, if there the highest count gives more
	 *         than one suit
	 */
	public Suit getMostFrequentSuit(final Suit exclude) {

		int maxCount = 0;
		Suit mostFrequentSuitColor = null;
		for (Suit suit : Suit.values()) {
			if (exclude != null && suit == exclude) {
				continue;
			}
			int cardCount = Suit.length;
			if (cardCount > maxCount) {
				mostFrequentSuitColor = suit;
				maxCount = cardCount;
			}
		}

		return mostFrequentSuitColor;
	}
	
	/**
	 * Gets the suit with the most Cards in the CardList (without considering
	 * the jacks!)
	 *
	 * @return Suit with most Cards in the CardList,<br>
	 *         the highest ranking suit, if there the highest count gives more
	 *         than one suit
	 */
	public Suit getMostFrequentSuit() {
		return getMostFrequentSuit(null);
	}

	/**
	 * Returns the first index of a card with the given suit.
	 *
	 * @param suit
	 *            Suit to search
	 * @param includeJacks
	 *            flag whether to include jacks in the result
	 * @return First index of a card with the given suit, -1 if there is no such
	 *         card
	 */
	public int getFirstIndexOfSuit(final Suit suit,
			final boolean includeJacks) {
		Card[] cards = cd.getCards();
		int result = -1;
		int index = 0;
		for (int i = 0;i< cards.length;i++) {
			if (result == -1 && cards[i].getSuit() == suit) {
				if (cards[i].getValue() != Value.JACK || cards[i].getValue() == Value.JACK
						&& includeJacks) {
					result = index;
				}
			}
			index++;
		}
		return result;
	}
	
	/**
	 * Returns the first index of a card with the given suit (including jacks!)
	 *
	 * @param suit
	 *            Suit to search
	 * @return First index of a card with the given suit, -1 if there is no such
	 *         card
	 */
	public int getFirstIndexOfSuit(final Suit suit) {

		return getFirstIndexOfSuit(suit, true);
	}

	/**
	 * Returns the last index of a card with the given suit (including jacks!)
	 *
	 * @param suit
	 *            Suit to search
	 * @return Last index of a card with the given suit, -1 if there is no such
	 *         card
	 */
	public int getLastIndexOfSuit(final Suit suit) {

		return getLastIndexOfSuit(suit, true);
	}

	/**
	 * Returns the last index of a card with the given suit
	 *
	 * @param suit
	 *            Suit to search
	 * @param includeJacks
	 *            flag whether to include jacks in the result
	 * @return Last index of a card with the given suit, -1 if there is no such
	 *         card
	 */
	public int getLastIndexOfSuit(final Suit suit, final boolean includeJacks) {

		int result = -1;
		int index = 0;
		Card[] cards = cd.getCards();
		for (int i = 0;i< cards.length;i++) {
			if (result == -1 && cards[i].getSuit() == suit) {
				if (cards[i].getValue() != Value.JACK || cards[i].getValue() == Value.JACK
						&& includeJacks) {
					result = index;
				}
			}
			index++;
		}
		return result;
	}

	@Override
	public boolean acceptBid(int bid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean acceptHandGame() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public AdditionalMultipliers chooseAdditionalMultipliers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Contract chooseContract() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Card chooseCard() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Player getPlayer() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Card[] selectSkat(Card[] skat) {
		// TODO Auto-generated method stub
		return null;
	}
}
