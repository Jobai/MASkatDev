
package de.skat3.gamelogic;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Provides data for the endscreen of a game.
 *@author Kai Baumann
 */
@SuppressWarnings("serial")
public class MatchResult implements Serializable {


  private PlayerHistory[] list;
  private Player winner;
  private Player loser;
  private boolean isBierlachs;

  /**
   * Creates the Match result with all players that are currently in the lobby.
   * 
   */
  public MatchResult(Player[] allPlayers) {
    list = new PlayerHistory[allPlayers.length];
    for (int i = 0; i < this.list.length; i++) {
      list[i] = new PlayerHistory(allPlayers[i]);
    }


  }

  /**
   * Copies the MatchResult and returns it.
   */
  public MatchResult(MatchResult matchResult) {
    list = new PlayerHistory[matchResult.list.length];
    for (int i = 0; i < this.list.length; i++) {
      list[i] = new PlayerHistory(matchResult.list[i]);
    }
    if (matchResult.isBierlachs) {
      this.loser = matchResult.loser.copyPlayer();
    } else {
      this.winner = matchResult.winner.copyPlayer();
    }
    this.isBierlachs = matchResult.isBierlachs;
  }

  public PlayerHistory[] getData() {
    return this.list;
  }

  void addScoreToHistory(Player player, int score) {
    for (PlayerHistory ph : this.list) {
      if (ph.getPlayer().equals(player)) {
        ph.addScore(score);
        break;
      }
    }
  }


  void calcWinner() {
    Player[] playerCopy = new Player[this.list.length];
    for (int i = 0; i < this.list.length; i++) {
      playerCopy[i] = this.list[i].player;
    }
    Arrays.sort(playerCopy);
    if (this.isBierlachs) {
      this.loser = playerCopy[playerCopy.length - 1];
    } else {
      this.winner = playerCopy[0];
    }
  }

  /**
   * A tuple of a player and his point history.
   * 
   * @author kai29
   *
   */
  public class PlayerHistory implements Serializable {
    Player player;
    ArrayList<Integer> history;

    PlayerHistory(Player player) {
      this.player = player;
      history = new ArrayList<Integer>();
    }

    public PlayerHistory(PlayerHistory playerHistory) {
      this.player = playerHistory.player.copyPlayer();
      this.history = playerHistory.history;
    }

    public String getName() {
      return this.player.name;
    }

    Player getPlayer() {
      return this.player;
    }

    public int getFinalScore() {
      return this.player.points;
    }

    void addScore(int score) {
      this.history.add(score);
    }

    public ArrayList<Integer> getHistory() {
      return this.history;
    }
  }

  public Player getWinner() {
    return this.winner;
  }

  public Player getLoser() {
    return this.loser;
  }

  public boolean isBierlachs() {
    return this.isBierlachs;
  }
}
