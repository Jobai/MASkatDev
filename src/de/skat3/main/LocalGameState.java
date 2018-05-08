package de.skat3.main;

import de.skat3.gamelogic.AdditionalMultipliers;
import de.skat3.gamelogic.Card;
import de.skat3.gamelogic.Contract;
import de.skat3.gamelogic.Hand;
import de.skat3.gamelogic.LogicAnswers;
import de.skat3.gamelogic.Player;

/**
 * 
 * @author kai29
 *
 */
public class LocalGameState {

  private boolean gameActive;
  private boolean singlePlayerGame;
  private int gameId;
  private int timerInSeconds;
  private Contract contract;
  private AdditionalMultipliers additionalMultipliers;
  private int trickcount;
  private Player localClient;
  private Player enemyOne;
  private Player enemyTwo;
  private Player dealer;
  private Card[] trick;
  private Card[] skat;
  private int localPosition;
  private int lastDealerPositon;
  public LogicAnswers currentRequestState;

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
    this.setSinglePlayerGame(singlePlayerGame);
    this.setTimerInSeconds(timerInSeconds);
    this.setTrickcount(0);
    this.setTrick(new Card[3]);
    this.setSkat(new Card[2]);
    SkatMain.mainController.reinitializePlayers();
  }

  /**
   * 
   */


  public void addPlayer() {
    if (SkatMain.mainController.currentLobby.numberOfPlayers == 3) {

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
            break;
          default:
            System.err.println("addPlayer klappt net ");
            break;
        }
      }

    } else {
      if (SkatMain.mainController.currentLobby.currentPlayers == 2) {
        switch (this.localPosition) {
          case 1:
            this.setEnemyOne(SkatMain.mainController.currentLobby.players[1].copyPlayer());
            break;
          case 2:
            this.setDealer(SkatMain.mainController.currentLobby.players[0].copyPlayer());
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
            this.setEnemyTwo(SkatMain.mainController.currentLobby.players[0].copyPlayer());
            this.setDealer(SkatMain.mainController.currentLobby.players[1].copyPlayer());
            break;
          default:
            System.err.println("addPlayer klappt net ");
            break;
        }
      }
      if (SkatMain.mainController.currentLobby.currentPlayers == 4) {
        switch (this.localPosition) {
          case 1:
            this.setDealer(SkatMain.mainController.currentLobby.players[3].copyPlayer());
            break;
          case 2:
            this.setEnemyTwo(SkatMain.mainController.currentLobby.players[3].copyPlayer());
            break;
          case 3:
            this.setEnemyOne(SkatMain.mainController.currentLobby.players[3].copyPlayer());
            break;
          case 4:
            this.setEnemyOne(SkatMain.mainController.currentLobby.players[0].copyPlayer());
            this.setEnemyTwo(SkatMain.mainController.currentLobby.players[1].copyPlayer());
            this.setDealer(SkatMain.mainController.currentLobby.players[2].copyPlayer());
            break;
          default:
            System.err.println("addPlayer klappt net ");
            break;
        }
      }


    }


    SkatMain.mainController.reinitializePlayers();



  }



  /**
   * Adds a played card to the trick.
   * 
   * @param card the played card.
   */

  public void addToTrick(Card card) {
    this.getTrick()[this.getTrickcount()] = card;
    this.setTrickcount((getTrickcount() + 1) % 3);
  }

  public Card getCurrentCardInTrick() {
    for (int i = 0; i < this.getTrick().length; i++) {
      if (this.getTrick()[i] == null && i - 1 > 0) {
        return this.getTrick()[i - 1];
      }
    }
    return null;
  }


  public Card getFirstCardPlayed() {
    if (this.getTrick()[0] != null && this.getTrickcount() != 0) {
      return this.getTrick()[0];
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
    if (this.getDealer().equals(player)) {
      return this.getDealer();
    }

    System.err.println("No Player found");
    return null;
  }

  public Player getLocalClient() {
    return localClient;
  }

  public Player getEnemyOne() {
    return enemyOne;
  }

  public void setEnemyOne(Player enemyOne) {
    this.enemyOne = enemyOne;
  }

  public void setDealer(Player dealer) {
    this.dealer = dealer;
  }

  public Player getEnemyTwo() {
    return this.enemyTwo;
  }

  public Player getDealer() {
    return this.dealer;
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
    if (this.getDealer() != null) {
      if (player.equals(this.getDealer())) {
        this.setDealer(null);
      }
    }
    SkatMain.mainController.reinitializePlayers();
  }


  void setDealerAndRotatePlayers(Player player) {
    Player temp;
    if (player.equals(SkatMain.mainController.currentLobby.players[0])) {
      this.lastDealerPositon = 1;
    }
    if (player.equals(SkatMain.mainController.currentLobby.players[1])) {
      this.lastDealerPositon = 2;
    }
    if (player.equals(SkatMain.mainController.currentLobby.players[2])) {
      this.lastDealerPositon = 3;
    }
    if (player.equals(SkatMain.mainController.currentLobby.players[3])) {
      this.lastDealerPositon = 4;
    }
    if (player.equals(this.getLocalClient())) {
      temp = this.getDealer();
      this.dealer = this.localClient;
      this.localClient = temp;
      return;
    }
    if (player.equals(this.getEnemyOne())) {
      temp = this.getDealer();
      this.dealer = this.enemyOne;
      this.enemyOne = this.enemyTwo;
      this.enemyTwo = temp;
      return;
    }
    if (player.equals(this.getEnemyTwo())) {
      temp = this.getDealer();
      this.dealer = this.getEnemyTwo();
      this.enemyTwo = temp;
      return;
    }
  }

  void rotatePlayers() {
    Player temp;
    if (this.localPlayerIsDealer()) {
      temp = this.dealer;
      this.dealer = this.enemyOne;
      this.enemyOne = this.enemyTwo;
      this.enemyTwo = this.localClient;
      this.localClient = temp;

    } else {
      switch (this.lastDealerPositon) {
        case 1:
          switch (this.localPosition) {
            case 2:
              temp = this.localClient;
              this.localClient = this.dealer;
              this.dealer = temp;
              break;
            case 3:
              temp = this.enemyTwo;
              this.enemyTwo = this.dealer;
              this.dealer = temp;
              break;
            case 4:
              temp = this.dealer;
              this.dealer = this.enemyOne;
              this.enemyOne = temp;
              break;
            default:
              System.err.println("Error in rotatePlayers");
              break;
          }
          break;
        case 2:
          switch (this.localPosition) {
            case 1:
              temp = this.enemyOne;
              this.enemyOne = this.dealer;
              this.dealer = temp;
              break;
            case 3:
              temp = this.localClient;
              this.localClient = this.dealer;
              this.dealer = temp;
              break;
            case 4:
              temp = this.enemyTwo;
              this.enemyTwo = this.dealer;
              this.dealer = temp;
              break;
            default:
              System.err.println("Error in rotatePlayers");
              break;
          }
          break;
        case 3:
          switch (this.localPosition) {
            case 1:
              temp = this.enemyTwo;
              this.enemyTwo = this.dealer;
              this.dealer = temp;
              break;
            case 2:
              temp = this.enemyOne;
              this.enemyOne = this.dealer;
              this.dealer = temp;
              break;
            case 4:
              temp = this.dealer;
              this.dealer = this.localClient;
              this.localClient = temp;
              break;
            default:
              System.err.println("Error in rotatePlayers");
              break;
          }
          break;
        case 4:
          switch (this.localPosition) {
            case 1:
              temp = this.dealer;
              this.dealer = localClient;
              this.localClient = temp;
              break;
            case 2:
              temp = this.enemyTwo;
              this.enemyTwo = this.dealer;
              this.dealer = temp;
              break;
            case 3:
              temp = this.enemyOne;
              this.enemyOne = this.getDealer();
              this.dealer = temp;
              break;
            default:
              System.err.println("Error in rotatePlayers");
              break;
          }
          break;
        default:
          System.err.println("Error in rotatePlayers");
          break;
      }
    }

    this.lastDealerPositon = (this.lastDealerPositon + 1);
    if (this.lastDealerPositon == 5) {
      this.lastDealerPositon = 1;
    }
  }

  public Card[] getSkat() {
    return skat;
  }

  public void setSkat(Card[] skat) {
    this.skat = skat;
  }

  public Card[] getTrick() {
    return trick;
  }

  public void setTrick(Card[] trick) {
    this.trick = trick;
  }

  public int getTrickcount() {
    return trickcount;
  }

  public void setTrickcount(int trickcount) {
    this.trickcount = trickcount;
  }

  public AdditionalMultipliers getAdditionalMultipliers() {
    return additionalMultipliers;
  }

  public void setAdditionalMultipliers(AdditionalMultipliers additionalMultipliers) {
    this.additionalMultipliers = additionalMultipliers;
  }

  public void setContract(Contract contract) {
    this.contract = contract;
  }

  public int getTimerInSeconds() {
    return timerInSeconds;
  }

  public void setTimerInSeconds(int timerInSeconds) {
    this.timerInSeconds = timerInSeconds;
  }

  public boolean isSinglePlayerGame() {
    return singlePlayerGame;
  }

  public void setSinglePlayerGame(boolean singlePlayerGame) {
    this.singlePlayerGame = singlePlayerGame;
  }

  public int getGameId() {
    return gameId;
  }

  public void setGameId(int gameId) {
    this.gameId = gameId;
  }

  public boolean isGameActive() {
    return gameActive;
  }

  public void setGameActive(boolean gameActive) {
    this.gameActive = gameActive;
  }

  public boolean localPlayerIsDealer() {
    if (SkatMain.mainController.currentLobby.numberOfPlayers == 3) {
      return false;
    } else {
      return (this.localClient.getUuid()
          .equals(SkatMain.ioController.getLastUsedProfile().getUuid())) ? false : true;
    }
  }

  void clearAfterRound() {
    this.contract = null;
    this.additionalMultipliers = null;
    this.skat = new Card[2];
    this.trick = new Card[3];
    this.getEnemyOne().setHand(new Hand());
    this.getEnemyTwo().setHand(new Hand());
    if (this.localPlayerIsDealer()) {
      this.getLocalClient().setHand(new Hand());
    }

  }


  Player getCurrentPlayer() {
    if (this.localClient.getPosition().ordinal() == trickcount) {
      return this.localClient;
    }
    if (this.enemyOne.getPosition().ordinal() == trickcount) {
      return this.enemyOne;
    }
    if (this.enemyTwo.getPosition().ordinal() == trickcount) {
      return this.enemyTwo;
    }
    System.err.println("No Player found");
    return null;
  }
}


