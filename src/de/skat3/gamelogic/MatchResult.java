package de.skat3.gamelogic;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Provides data for the endscreen of a game.
 *
 */
public class MatchResult implements Serializable {


  private PlayerHistory[] list;

  // TODO

  /**
   * 
   */
  public MatchResult(Player[] allPlayers) {
    list = new PlayerHistory[allPlayers.length];
    for (int i = 0; i < this.list.length; i++) {
      list[i] = new PlayerHistory(allPlayers[i]);
    }


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

  /**
   * A tuple of a player and his point history.
   * 
   * @author kai29
   *
   */
  public class PlayerHistory {
    Player player;
    ArrayList<Integer> history;

    PlayerHistory(Player player) {
      this.player = player;
      history = new ArrayList<Integer>();
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
}
