
public interface AbstractAIPlayer {
	
	void finalizeGame();

	Boolean callContra();

	Boolean callRe();

	void preparateForNewGame();

	CardDeck  getCardsToDiscard();

	Card playCard();

	void startGame();

	Boolean holdBid(int currBidValue);

	Integer bidMore(int nextBidValue);

	boolean playGrandHand();

	boolean pickUpSkat();

	void getContract();
	
}
