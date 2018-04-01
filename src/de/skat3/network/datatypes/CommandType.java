package de.skat3.network.datatypes;

public enum CommandType implements SubType {
  
	/**
	 * User is ordered to make a bid
	 */
    BID_REQUEST,
    /**
	 * User is informed of a bid from a different player
	 */
    BID_INFO,
    /**
     * User is informed that is last bid was illegal and he is ordered to redo.
     */
    BID_REDO,
    
    /**
	 * User is ordered to play a card
	 */
    PLAY_REQUEST,
    /**
   	 * User is informed of a played card by a different player
   	 */
    PLAY_INFO,
    /**
     * User is informed that is last played card was illegal and he is ordered to redo.
     */
    PLAY_REDO,
    /**
     * User is asked if he wants to play a hand game (Take up the skat)
     */
    HAND_REQUEST,
    /**
     * User is informed about the skat and ordered to put 2 cards back into the skat (in case of a hand game)
     */
    SKAT_INFO_REQUEST,
    /**
     * User is asked what contract (game mode) he wants to play
     */
    CONTRACT_REQUEST,
    
    /**
     * User is informed about who won the last trick
     */
    TRICK_INFO,
    /**
     *  User is informed what cards he gets for this round
     */
    ROUND_START_INFO,
    /**
     *  User is informed who won the round and how many points the single player won / lost
     */
    ROUND_END_INFO,
    /**
     * User is informed about who won the match (including leaderboard scoring)
     */
    MATCH_INFO,
    /**
     * User is informed about the joining / leaving of a player and his player profile
     */
    GAME_INFO;
}
