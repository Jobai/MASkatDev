package de.skat3.main;

import de.skat3.gamelogic.AdditionalMultipliers;
import de.skat3.gamelogic.Card;
import de.skat3.gamelogic.Contract;
import de.skat3.gamelogic.Hand;
import de.skat3.gamelogic.Player;


public class LocalGameState {

  public boolean gameActive;
  public boolean singlePlayerGame;
  public int gameId;
  public int timerInSeconds;
  public Contract contract;
  public AdditionalMultipliers additionalMultipliers;
  public int trickcount;
  private Player localClient;
  private Player enemyOne;
  private Player enemyTwo;
  public Player dealer;
  public Card[] trick;
  public Card[] skat;
  private int localPosition;

  /**
   * The current state of a game.
   * 
   * @param singlePlayerGame
   * 
   */
  public LocalGameState(int numberOfPlayers, int timerInSeconds, boolean singlePlayerGame) {
    this.localPosition = SkatMain.mainController.currentLobby.currentPlayers;
    this.localClient =
        SkatMain.mainController.currentLobby.players[this.localPosition - 1].copyPlayer();
    this.singlePlayerGame = singlePlayerGame;
    this.timerInSeconds = timerInSeconds;
    this.trickcount = 0;
    this.trick = new Card[3];
    this.skat = new Card[2];
    SkatMain.mainController.reinitializePlayers();
  }

  /**
   * 
   */


  public void addPlayer() {


    if (SkatMain.mainController.currentLobby.currentPlayers == 2) {
      switch (this.localPosition) {
        case 1:
          this.setEnemyOne(SkatMain.mainController.currentLobby.players[1].copyPlayer());
          break;
        case 2:
          this.setEnemyTwo(SkatMain.mainController.currentLobby.players[0].copyPlayer());
          break;

        default:
          System.err.println("addPlayer klappt net ");
          break;
      }

    }
    if (SkatMain.mainController.currentLobby.currentPlayers == 3) {
      switch (this.localPosition) {
        case 1:
          this.setEnemyTwo(SkatMain.mainController.currentLobby.players[2].copyPlayer());
          break;
        case 2:
          this.setEnemyOne(SkatMain.mainController.currentLobby.players[2].copyPlayer());
          break;
        case 3:
          this.setEnemyOne(SkatMain.mainController.currentLobby.players[0].copyPlayer());
          this.setEnemyTwo(SkatMain.mainController.currentLobby.players[1].copyPlayer());
        default:
          System.err.println("addPlayer klappt net ");
          break;
      }


      // case 4: // TODO
    }

    SkatMain.mainController.reinitializePlayers();



  }



  /**
   * Adds a played card to the trick.
   * 
   * @param card the played card.
   */

  public void addToTrick(Card card) {
    this.trick[this.trickcount] = card;
    this.trickcount = (trickcount + 1) % 3;
  }

  public Card getCurrentCardInTrick() {
    for (int i = 0; i < this.trick.length; i++) {
      if (this.trick[i] == null && i - 1 > 0) {
        return this.trick[i - 1];
      }
    }
    return null;
  }


  public Card getFirstCardPlayed() {
    if (this.trick[0] != null && this.trickcount != 0) {
      return this.trick[0];
    } else {
      return null;
    }
  }

  public Hand getLocalHand() {
    return this.getLocalClient().getHand();
  }

  /**
   * 
   * @param bot
   * @return
   */

  /**
   * 
   * @return
   */
  public Player getPlayer(Player player) {

    if (this.getLocalClient().equals(player)) {
      return this.getLocalClient();
    }
    if (this.getEnemyOne().equals(player)) {
      return this.getEnemyOne();
    }
    if (this.getEnemyTwo().equals(player)) {
      return this.getEnemyTwo();
    }
    if (this.dealer.equals(player)) {
      return this.dealer;
    }

    System.err.println("No Player found");
    return null;
  }

  public Player getLocalClient() {
    try {
      throw new Exception();
    } catch (Exception e) {
      // System.out.println("-------getLocalClient--------");
      // e.printStackTrace();
      // System.out.println("-------end of getLocalClient--------");
    }
    return localClient;
  }

  public Player getEnemyOne() {
    return enemyOne;
  }

  public void setEnemyOne(Player enemyOne) {
    this.enemyOne = enemyOne;
  }

  public Player getEnemyTwo() {
    return enemyTwo;
  }

  public void setEnemyTwo(Player enemyTwo) {
    this.enemyTwo = enemyTwo;
  }

  public Contract getContract() {
    return this.contract;
  }

  public void removePlayer(Player player) {
    if (this.enemyOne != null) {
      if (player.equals(this.enemyOne)) {
        this.enemyOne = null;
      }
    }
    if (this.enemyTwo != null) {
      if (player.equals(this.enemyTwo)) {
        this.enemyTwo = null;
      }
    }
    if (this.dealer != null) {
      if (player.equals(this.dealer)) {
        this.dealer = null;
      }
    }
    SkatMain.mainController.reinitializePlayers();
  }
}


