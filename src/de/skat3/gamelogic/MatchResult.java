package de.skat3.gamelogic;

import java.io.Serializable;

public class MatchResult implements Serializable {


  private static final long serialVersionUID = 7478908957800866050L;
  int[] playerOneHistory;
  int[] playerTwoHistory;
  int[] playerThreeHistory;
  int[] playerFourHistory;

  /**
   * 
   * @param allPlayers
   */
  public MatchResult(Player[] allPlayers) {
    // TODO


  }
}
