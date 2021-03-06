package de.skat3.network.datatypes;

/**
 * ENUMS that describe a sever message related to the game.
 * 
 * @author Jonas Bauer
 *
 */
public enum CommandType implements SubType {

  /**
   * User is ordered to make a bid.
   */
  BID_REQUEST,
  /**
   * User is informed of a bid from a different player.
   */
  BID_INFO,
  /**
   * User is ordered to play a card.
   */
  PLAY_REQUEST,
  /**
   * User is informed of a played card by a different player.
   */
  PLAY_INFO,
  /**
   * User is asked if he wants to play a hand game (Take up the skat).
   */
  HAND_REQUEST,
  /**
   * User is informed about the skat and ordered to put 2 cards back into the skat (in case of a
   * hand game).
   */
  SKAT_INFO_REQUEST,
  /**
   * User is asked what contract (game mode) he wants to play.
   */
  CONTRACT_REQUEST,

  /**
   * User is informed about who won the last trick.
   */
  TRICK_INFO,
  /**
   * User is informed what cards he gets for this round.
   */
  ROUND_START_INFO,
  /**
   * User is informed who won the round and how many points the single player won / lost.
   */
  ROUND_END_INFO,
  /**
   * User is informed about who won the match (including leaderboard scoring).
   */
  MATCH_INFO,
  /**
   * User is informed about the joining / leaving of a player and his player profile.
   */
  GAME_INFO,

  KONTRA_ANNOUNCED_INFO,

  REKONTRA_ANNOUNCED_INFO,

  KONTRA_SHOW_OPTION_INFO,

  REKONTRA_SHOW_OPTION_INFO,

  AUCTION_WINNER_INFO,

  CONTRACT_INFO, ROUND_GENERAL_INFO, UPDATE_ENEMY_INFO, TRAINING_CALL_FOR_SPECIFIC_PLAY, SET_DEALER;
}
