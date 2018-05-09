package de.skat3.io.profile;

public interface ProfileStatisticsInterface {

  // Time

  public long getPlayerGameTime();

  public void setPlayerGameTime(long playerGameTime);

  // Singleplayer

  // Games

  public int getSinglePlayerTotalGames();

  public int getSinglePlayerTotalGamesWon();

  public int getSinglePlayerTotalGamesLost();

  /**
   * Increments singlePlayerTotalGamesWon and singlePlayerTotalGames.
   */
  public void incrementSinglePlayerTotalGamesWon();

  /**
   * Increments singlePlayerTotalGamesLost and singlePlayerTotalGames.
   */
  public void incrementSinglePlayerTotalGamesLost();

  // Rounds

  public int getSinglePlayerTotalRounds();

  public int getSinglePlayerTotalRoundsGrand();

  public int getSinglePlayerTotalRoundsNull();

  public int getSinglePlayerTotalRoundsSuit();

  public int getSinglePlayerRoundsWon();

  public int getSinglePlayerRoundsWonGrand();

  /**
   * Increments attributes: singlePlayerRoundsWonGrand, singlePlayerTotalRoundsGrand and
   * singlePlayerRoundsWon.
   */
  public void incrementSinglePlayerRoundsWonGrand();

  public int getSinglePlayerRoundsWonNull();

  /**
   * Increments attributes: singlePlayerRoundsWonNull, singlePlayerTotalRoundsNull and
   * singlePlayerRoundsWon.
   */
  public void incrementSinglePlayerRoundsWonNull();

  public int getSinglePlayerRoundsWonSuit();

  /**
   * Increments attributes: singlePlayerRoundsWonSuit, singlePlayerTotalRoundsSuit and
   * singlePlayerRoundsWon.
   */
  public void incrementSinglePlayerRoundsWonSuit();

  public int getSinglePlayerRoundsLost();

  public int getSinglePlayerRoundsLostGrand();


  /**
   * Increments attributes: singlePlayerRoundsLostGrand, singlePlayerTotalRoundsGrand and
   * singlePlayerRoundsLost.
   */
  public void incrementSinglePlayerRoundsLostGrand();

  public int getSinglePlayerRoundsLostNull();

  /**
   * Increments attributes: singlePlayerRoundsLostNull, singlePlayerTotalRoundsNull and
   * singlePlayerRoundsLost.
   */
  public void incrementSinglePlayerRoundsLostNull();

  public int getSinglePlayerRoundsLostSuit();

  /**
   * Increments attributes: singlePlayerRoundsLostSuit, singlePlayerTotalRoundsSuit and
   * singlePlayerRoundsLost.
   */
  public void incrementSinglePlayerRoundsLostSuit();

  // Score

  public int getSinglePlayerHighestScore();

  /**
   * Checks if parameter potentiallyNewScore is higher that attribute singlePlayerHighestScore and
   * if so updates it, otherwise - nothing happens.
   * 
   * @param potentiallyNewScore the score that is potentially new highest singleplayer score.
   */
  public void setSinglePlayerHighestScore(int potentiallyNewScore);

  public int getSinglePlayerLowestScore();

  /**
   * Checks if parameter potentiallyNewScore is lower that attribute singlePlayerLowestScore and if
   * so updates it, otherwise - nothing happens.
   * 
   * @param potentiallyNewScore the score that is potentially new lowest singleplayer score.
   */
  public void setSinglePlayerLowestScore(int potentiallyNewScore);


  // Multiplayer

  // Games

  public int getMultiPlayerTotalGames();

  public int getMultiPlayerTotalGamesWon();

  public int getMultiPlayerTotalGamesLost();

  public void incrementMultiPlayerTotalGamesWon();

  public void incrementMultiPlayerTotalGamesLost();

  // Rounds

  public int getMultiPlayerTotalRounds();

  public int getMultiPlayerTotalRoundsGrand();

  public int getMultiPlayerTotalRoundsNull();

  public int getMultiPlayerTotalRoundsSuit();

  public int getMultiPlayerRoundsWon();

  public int getMultiPlayerRoundsWonGrand();

  /**
   * Increments attributes: multiPlayerRoundsWonGrand, multiPlayerTotalRoundsGrand and
   * multiPlayerRoundsWon.
   */
  public void incrementMultiPlayerRoundsWonGrand();

  public int getMultiPlayerRoundsWonNull();

  /**
   * Increments attributes: multiPlayerRoundsWonNull, multiPlayerTotalRoundsNull and
   * multiPlayerRoundsWon.
   */
  public void incrementMultiPlayerRoundsWonNull();

  public int getMultiPlayerRoundsWonSuit();

  /**
   * Increments attributes: multiPlayerRoundsWonSuit, multiPlayerTotalRoundsSuit and
   * multiPlayerRoundsWon.
   */
  public void incrementMultiPlayerRoundsWonSuit();

  public int getMultiPlayerRoundsLost();

  public int getMultiPlayerRoundsLostGrand();

  /**
   * Increments attributes: multiPlayerRoundsLostGrand, multiPlayerTotalRoundsGrand and
   * multiPlayerRoundsLost.
   */
  public void incrementMultiPlayerRoundsLostGrand();

  public int getMultiPlayerRoundsLostNull();

  /**
   * Increments attributes: multiPlayerRoundsLostNull, multiPlayerTotalRoundsNull and
   * multiPlayerRoundsLost.
   */
  public void incrementMultiPlayerRoundsLostNull();

  public int getMultiPlayerRoundsLostSuit();

  /**
   * Increments attributes: multiPlayerRoundsLostSuit, multiPlayerTotalRoundsSuit and
   * multiPlayerRoundsLost.
   */
  public void incrementMultiPlayerRoundsLostSuit();

  // Score

  public int getMultiPlayerHighestScore();

  /**
   * Checks if parameter potentiallyNewScore is higher that attribute multiPlayerHighestScore and if
   * so updates it, otherwise - nothing happens.
   * 
   * @param potentiallyNewScore the score that is potentially new highest multiplayer score.
   */
  public void setMultiPlayerHighestScore(int potentiallyNewScore);

  public int getMultiPlayerLowestScore();

  /**
   * Checks if parameter potentiallyNewScore is lower that attribute multiPlayerLowestScore and if
   * so updates it, otherwise - nothing happens.
   * 
   * @param potentiallyNewScore the score that is potentially new lowest multiplayer score.
   */
  public void setMultiPlayerLowestScore(int potentiallyNewScore);
}
