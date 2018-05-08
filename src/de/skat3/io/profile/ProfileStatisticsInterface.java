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
   *
   */
  public void incrementSinglePlayerRoundsWonGrand();

  public int getSinglePlayerRoundsWonNull();

  /**
   * 
   */
  public void incrementSinglePlayerRoundsWonNull();

  public int getSinglePlayerRoundsWonSuit();

  /**
   * 
   */
  public void incrementSinglePlayerRoundsWonSuit();

  public int getSinglePlayerRoundsLost();

  public int getSinglePlayerRoundsLostGrand();

  public void incrementSinglePlayerRoundsLostGrand();

  public int getSinglePlayerRoundsLostNull();

  /**
   * 
   */
  public void incrementSinglePlayerRoundsLostNull();

  public int getSinglePlayerRoundsLostSuit();

  /**
   * 
   */
  public void incrementSinglePlayerRoundsLostSuit();

  // Score

  public int getSinglePlayerHighestScore();

  /**
   * @param potentiallyNewScore
   */
  public void setSinglePlayerHighestScore(int potentiallyNewScore);

  public int getSinglePlayerLowestScore();

  /**
   * @param potentiallyNewScore
   */
  public void setSinglePlayerLowestScore(int potentiallyNewScore);


  // Multiplayer

  // Games

  public int getMultiPlayerTotalGames();

  public int getMultiPlayerTotalGamesWon();

  public int getMultiPlayerTotalGamesLost();

  /**
   * 
   */
  public void incrementMultiPlayerTotalGamesWon();

  /**
   * 
   */
  public void incrementMultiPlayerTotalGamesLost();

  // Rounds

  public int getMultiPlayerTotalRounds();

  public int getMultiPlayerTotalRoundsGrand();

  public int getMultiPlayerTotalRoundsNull();

  public int getMultiPlayerTotalRoundsSuit();

  public int getMultiPlayerRoundsWon();

  public int getMultiPlayerRoundsWonGrand();

  /**
   * 
   */
  public void incrementMultiPlayerRoundsWonGrand();

  public int getMultiPlayerRoundsWonNull();

  /**
   * 
   */
  public void incrementMultiPlayerRoundsWonNull();

  public int getMultiPlayerRoundsWonSuit();

  /**
   * 
   */
  public void incrementMultiPlayerRoundsWonSuit();

  public int getMultiPlayerRoundsLost();

  public int getMultiPlayerRoundsLostGrand();

  /**
   * 
   */
  public void incrementMultiPlayerRoundsLostGrand();

  public int getMultiPlayerRoundsLostNull();

  /**
   * 
   */
  public void incrementMultiPlayerRoundsLostNull();

  public int getMultiPlayerRoundsLostSuit();

  /**
   * 
   */
  public void incrementMultiPlayerRoundsLostSuit();

  // Score

  public int getMultiPlayerHighestScore();

  /**
   * @param potentiallyNewScore
   */
  public void setMultiPlayerHighestScore(int potentiallyNewScore);

  public int getMultiPlayerLowestScore();

  /**
   * @param potentiallyNewScore
   */
  public void setMultiPlayerLowestScore(int potentiallyNewScore);
}
