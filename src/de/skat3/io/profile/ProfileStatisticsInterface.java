package de.skat3.io.profile;

public interface ProfileStatisticsInterface {

  // Time

  public long getPlayerGameTime();

  public void setPlayerGameTime(long playerGameTime);

  // Singleplayer

  // Rounds

  public int getSinglePlayerTotalRounds();

  public int getSinglePlayerTotalRoundsGrand();

  public int getSinglePlayerTotalRoundsNull();

  public int getSinglePlayerTotalRoundsSuit();

  public int getSinglePlayerRoundsWon();

  public int getSinglePlayerRoundsWonGrand();

  public void incrementSinglePlayerRoundsWonGrand();

  public int getSinglePlayerRoundsWonNull();

  public void incrementSinglePlayerRoundsWonNull();

  public int getSinglePlayerRoundsWonSuit();

  public void incrementSinglePlayerRoundsWonSuit();

  public int getSinglePlayerRoundsLost();

  public int getSinglePlayerRoundsLostGrand();

  public void incrementSinglePlayerRoundsLostGrand();

  public int getSinglePlayerRoundsLostNull();

  public void incrementSinglePlayerRoundsLostNull();

  public int getSinglePlayerRoundsLostSuit();

  public void incrementSinglePlayerRoundsLostSuit();

  // Score

  public int getSinglePlayerHighestScore();

  public void setSinglePlayerHighestScore(int potentiallyNewScore);

  public int getSinglePlayerLowestScore();

  public void setSinglePlayerLowestScore(int potentiallyNewScore);


  // Multiplayer

  // Rounds

  public int getMultiPlayerTotalRounds();

  public int getMultiPlayerTotalRoundsGrand();

  public int getMultiPlayerTotalRoundsNull();

  public int getMultiPlayerTotalRoundsSuit();

  public int getMultiPlayerRoundsWon();

  public int getMultiPlayerRoundsWonGrand();

  public void incrementMultiPlayerRoundsWonGrand();

  public int getMultiPlayerRoundsWonNull();

  public void incrementMultiPlayerRoundsWonNull();

  public int getMultiPlayerRoundsWonSuit();

  public void incrementMultiPlayerRoundsWonSuit();

  public int getMultiPlayerRoundsLost();

  public int getMultiPlayerRoundsLostGrand();

  public void incrementMultiPlayerRoundsLostGrand();

  public int getMultiPlayerRoundsLostNull();

  public void incrementMultiPlayerRoundsLostNull();

  public int getMultiPlayerRoundsLostSuit();

  public void incrementMultiPlayerRoundsLostSuit();

  // Score

  public int getMultiPlayerHighestScore();

  public void setMultiPlayerHighestScore(int potentiallyNewScore);

  public int getMultiPlayerLowestScore();

  public void setMultiPlayerLowestScore(int potentiallyNewScore);
}
