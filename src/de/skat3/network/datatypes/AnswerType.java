package de.skat3.network.datatypes;

public enum AnswerType implements SubType {

  /**
   * Player tells the server his bid
   */
  BID_ANSWER,
  /**
   * Player tells the server his played card
   */
  PLAY_ANSWER,
  /**
   * Player tells the server if the wants to take up the skat
   */
  HAND_ANSWER,
  /**
   * Player tells the server what cards he wants to put into the skat (if he picked up the skat)
   */
  THROW_ANSWER,
  /**
   * Player tells the server what contract he wants to play
   */
  CONTRACT_ANSWER,
  /**
   * for future use reserved /XXX
   */
  ROUND_ANSWER,
  /**
   * for future use reserved /XXX
   */
  MATCH_ANSWER,
  /**
   * tells the server that his basic player info during joining
   */
  GAME_ANSWER,
  
  KONTRA_ANSWER,
  
  REKONTRA_ANSWER;
}
