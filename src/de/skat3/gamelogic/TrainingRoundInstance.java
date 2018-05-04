package de.skat3.gamelogic;

import de.skat3.network.server.ServerLogicController;

/**
 * A scripted scenario for the training mode.
 * 
 * @author kai29
 *
 */
public class TrainingRoundInstance extends RoundInstance {

  int startHandSize = 6;
  int scenarioRoundLength; // amount of rounds in the specific scenario
  int scenario;
  int delay;

  /**
   * Creates the training mode scenario.
   * 
   * @param scenario number of scenario
   */
  public TrainingRoundInstance(int scenario, ServerLogicController slc, Player[] players) {
    this.scenario = scenario;
    this.slc = slc;
    this.delay = 1500;
    this.players = new Player[players.length];
    for (int i = 0; i < players.length; i++) {
      this.players[i] = players[i];
    }
    switch (scenario) {
      case 0:
        break;
      case 1:
        this.scenarioRoundLength = 2;
        break;
      default:
        System.err.println("Scenario does not exist: " + scenario);
        break;
    }
  }

  @Override
  void startRound() throws InterruptedException {

    this.initializeAuction();
    this.setSoloAndContract();
    this.updatePlayer();
    this.updateEnemies();
    slc.broadcastRoundStarted();
    this.setStartingPlayer();
    this.startGame();
  }

  /**
   * Sets the player that plays the first card in the first round of the scenario.
   */
  private void setStartingPlayer() {
    switch (this.scenario) {

      case 1: // localClient starts
        break;
      case 2:
        this.setStartPlayer(1); // wenn in scenario 2 zb ai1 starten soll
        break;
      default:
        System.err.println("Scenario does not exist: " + this.scenario);
        break;


    }

  }

  @Override
  void startGame() throws InterruptedException {
    synchronized (this.lock) {
      this.trick = new Card[3];

      for (int currentRound = 0; currentRound < this.startHandSize; currentRound++) {

        this.updatePlayer();
        this.updateEnemies();

        if (this.players[0].isBot) {
          Thread.sleep(this.delay);
          Card current = this.getScriptedCard(currentRound, 0);
          this.addCardtoTrick(current);
        } else {
          slc.callForSpecificPlay(this.players[0].copyPlayer(),
              this.getScriptedCard(currentRound, 0)); // XXX
          this.current = LogicAnswers.CARD;
          this.lock.wait();
        }
        slc.updatePlayerDuringRound(this.players[0].copyPlayer());
        slc.sendPlayedCard(this.players[0].copyPlayer(), this.trick[0].copy());


        if (this.players[1].isBot) {
          Thread.sleep(this.delay);
          Card current = this.getScriptedCard(currentRound, 1);
          this.addCardtoTrick(current);
        } else {
          slc.callForSpecificPlay(this.players[1].copyPlayer(),
              this.getScriptedCard(currentRound, 1));
          this.current = LogicAnswers.CARD;
          this.lock.wait();
        }
        slc.updatePlayerDuringRound(this.players[1].copyPlayer());
        slc.sendPlayedCard(this.players[1].copyPlayer(), this.trick[1].copy());


        if (this.players[2].isBot) {
          Thread.sleep(this.delay);
          Card current = this.getScriptedCard(currentRound, 2);
          this.addCardtoTrick(current);
        } else {
          slc.callForSpecificPlay(this.players[2].copyPlayer(),
              this.getScriptedCard(currentRound, 2));
          this.current = LogicAnswers.CARD;
          this.lock.wait();
        }
        slc.updatePlayerDuringRound(this.players[2].copyPlayer());
        slc.sendPlayedCard(this.players[2].copyPlayer(), this.trick[2].copy());

        Player trickWinner = this.determineTrickWinner();
        for (Card card : this.trick) {
          trickWinner.wonTricks.add(card);
        }
        slc.broadcastTrickResult(trickWinner);
        if (this.scenarioRoundLength - 1 == currentRound) {
          break;
          // exit game TODO
        }
      }

    }
  }


  // CASES TODO ARTEM, EMRE

  /**
   * Returns the card that the player or ai should play in a specific scenario and round.
   * 
   * @return
   */
  private Card getScriptedCard(int currentRound, int player) {
    switch (this.scenario) {
      case 0:
        return null;
      case 1:
        switch (currentRound) {
          case 0:
            switch (player) {
              case 0:
                return new Card(Suit.HEARTS, Value.JACK);
              case 1:
                return new Card(Suit.HEARTS, Value.TEN);
              case 2:
                return new Card(Suit.HEARTS, Value.NINE);
              default:
                return null;
            }
          case 1:
            switch (player) {
              case 0:
                return new Card(Suit.HEARTS, Value.ACE);
              case 1:
                return new Card(Suit.HEARTS, Value.KING);
              case 2:
                return new Card(Suit.DIAMONDS, Value.EIGHT);
              default:
            }
          case 2:
            switch (player) {
              case 0:
                return null;
              case 1:
                return null;
              case 2:
                return null;
              default:
                return null;
            }
          case 3:
            switch (player) {
              case 0:
                return null;
              case 1:
                return null;
              case 2:
                return null;
              default:
                return null;
            }
          case 4:
            switch (player) {
              case 0:
                return null;
              case 1:
                return null;
              case 2:
                return null;
              default:
                return null;
            }
          case 5:
            switch (player) {
              case 0:
                return null;
              case 1:
                return null;
              case 2:
                return null;
              default:
                return null;
            }
          default:
            return null;
        }
      case 2:
        switch (currentRound) {
          case 0:
            switch (player) {
              case 0:
                return null;
              case 1:
                return null;
              case 2:
                return null;
              default:
                return null;
            }
          case 1:
            switch (player) {
              case 0:
                return null;
              case 1:
                return null;
              case 2:
                return null;
              default:
                return null;
            }
          case 2:
            switch (player) {
              case 0:
                return null;
              case 1:
                return null;
              case 2:
                return null;
              default:
                return null;
            }
          case 3:
            switch (player) {
              case 0:
                return null;
              case 1:
                return null;
              case 2:
                return null;
              default:
                return null;
            }
          case 4:
            switch (player) {
              case 0:
                return null;
              case 1:
                return null;
              case 2:
                return null;
              default:
                return null;
            }
          case 5:
            switch (player) {
              case 0:
                return null;
              case 1:
                return null;
              case 2:
                return null;
              default:
                return null;
            }
          default:
            return null;
        }
      case 3:
        switch (currentRound) {
          case 0:
            switch (player) {
              case 0:
                return null;
              case 1:
                return null;
              case 2:
                return null;
              default:
                return null;
            }
          case 1:
            switch (player) {
              case 0:
                return null;
              case 1:
                return null;
              case 2:
                return null;
              default:
                return null;
            }
          case 2:
            switch (player) {
              case 0:
                return null;
              case 1:
                return null;
              case 2:
                return null;
              default:
                return null;
            }
          case 3:
            switch (player) {
              case 0:
                return null;
              case 1:
                return null;
              case 2:
                return null;
              default:
                return null;
            }
          case 4:
            switch (player) {
              case 0:
                return null;
              case 1:
                return null;
              case 2:
                return null;
              default:
                return null;
            }
          case 5:
            switch (player) {
              case 0:
                return null;
              case 1:
                return null;
              case 2:
                return null;
              default:
                return null;
            }
          default:
            return null;
        }
      case 4:
        switch (currentRound) {
          case 0:
            switch (player) {
              case 0:
                return null;
              case 1:
                return null;
              case 2:
                return null;
              default:
                return null;
            }
          case 1:
            switch (player) {
              case 0:
                return null;
              case 1:
                return null;
              case 2:
                return null;
              default:
                return null;
            }
          case 2:
            switch (player) {
              case 0:
                return null;
              case 1:
                return null;
              case 2:
                return null;
              default:
                return null;
            }
          case 3:
            switch (player) {
              case 0:
                return null;
              case 1:
                return null;
              case 2:
                return null;
              default:
                return null;
            }
          case 4:
            switch (player) {
              case 0:
                return null;
              case 1:
                return null;
              case 2:
                return null;
              default:
                return null;
            }
          case 5:
            switch (player) {
              case 0:
                return null;
              case 1:
                return null;
              case 2:
                return null;
              default:
                return null;
            }
          default:
            return null;
        }
      case 5:
        switch (currentRound) {
          case 0:
            switch (player) {
              case 0:
                return null;
              case 1:
                return null;
              case 2:
                return null;
              default:
                return null;
            }
          case 1:
            switch (player) {
              case 0:
                return null;
              case 1:
                return null;
              case 2:
                return null;
              default:
                return null;
            }
          case 2:
            switch (player) {
              case 0:
                return null;
              case 1:
                return null;
              case 2:
                return null;
              default:
                return null;
            }
          case 3:
            switch (player) {
              case 0:
                return null;
              case 1:
                return null;
              case 2:
                return null;
              default:
                return null;
            }
          case 4:
            switch (player) {
              case 0:
                return null;
              case 1:
                return null;
              case 2:
                return null;
              default:
                return null;
            }
          case 5:
            switch (player) {
              case 0:
                return null;
              case 1:
                return null;
              case 2:
                return null;
              default:
                return null;
            }
          default:
            return null;
        }
      default:
        return null;
    }

  }


  // CASES TODO ARTEM,EMRE
  /**
   * Declares the solo/team players in the scenario and sets the contract.
   */
  private void setSoloAndContract() {

    switch (this.scenario) {
      case 0:
        break;
      case 1:
        this.solo = this.players[0];
        this.solo.setSolo(true);
        Player[] team = this.getTeamPlayer();
        team[0].setSolo(false);
        team[1].setSolo(false);
        this.contract = Contract.HEARTS;
        break;
      case 2:
        break;
      case 3:
        break;
      case 4:
        break;
      case 5:
        break;
      default:
        break;
    }


  }

  @Override
  void updateEnemies() {
    Player p1;
    Player p2;
    Player p3;
    p1 = this.players[0].copyPlayer();
    p2 = this.players[1].copyPlayer();
    p3 = this.players[2].copyPlayer();
    p1.wonTricks = null;
    p2.wonTricks = null;
    p3.wonTricks = null;
    p1.ai = null;
    p2.ai = null;
    p3.ai = null;
    slc.updateEnemy(p1);
    slc.updateEnemy(p2);
    slc.updateEnemy(p3);

  }

  // 1= ai1, 2= ai2
  /**
   * Sets the player that starts the first round of the scenario.
   * 
   * @param i 1 = ai1, 2 = ai2, no call = human player starts the first round.
   */
  void setStartPlayer(int i) {

    if (i == 1) {
      Player temp = this.players[0];
      this.players[0] = this.players[1];
      this.players[1] = this.players[2];
      this.players[2] = temp;
    }
    if (i == 2) {
      Player temp = this.players[0];
      this.players[0] = this.players[2];
      this.players[2] = this.players[1];
      this.players[1] = temp;
    }
  }



  // CASES TODO ARTEM,EMRE
  /**
   * Deals cards to every player depending on the current scenario. No cards are dealt at random!
   */
  @Override
  void dealCards() {

    switch (this.scenario) {

      case 0:


        break;

      case 1:
        /*
         * 
         * Players Hand: Herz_Bube, Herz_9, Herz_Ass, Herz_7, Pik_8, Pik_10 Ai 1 Hand: Herz_König,
         * Herz_10, Pik_Ass, Karo_9, Karo_10, Kreuz_8 Ai 2 Hand: Karo_8, Herz_9, Karo_10, Kreuz_9,
         * Karo_Ass, Kreuz_Ass
         */
        Card[] playerCards = new Card[this.startHandSize];
        playerCards[0] = new Card(Suit.HEARTS, Value.JACK);
        playerCards[1] = new Card(Suit.HEARTS, Value.NINE);
        playerCards[2] = new Card(Suit.HEARTS, Value.ACE);
        playerCards[3] = new Card(Suit.HEARTS, Value.SEVEN);
        playerCards[4] = new Card(Suit.SPADES, Value.EIGHT);
        playerCards[5] = new Card(Suit.SPADES, Value.TEN);


        Card[] enemyOneCards = new Card[this.startHandSize];
        enemyOneCards[0] = new Card(Suit.HEARTS, Value.KING);
        enemyOneCards[1] = new Card(Suit.HEARTS, Value.TEN);
        enemyOneCards[2] = new Card(Suit.SPADES, Value.ACE);
        enemyOneCards[3] = new Card(Suit.DIAMONDS, Value.NINE);
        enemyOneCards[4] = new Card(Suit.DIAMONDS, Value.TEN);
        enemyOneCards[5] = new Card(Suit.CLUBS, Value.EIGHT);


        Card[] enemyTwoCards = new Card[this.startHandSize];
        enemyTwoCards[0] = new Card(Suit.DIAMONDS, Value.EIGHT);
        enemyTwoCards[1] = new Card(Suit.HEARTS, Value.NINE);
        enemyTwoCards[2] = new Card(Suit.DIAMONDS, Value.TEN);
        enemyTwoCards[3] = new Card(Suit.CLUBS, Value.NINE);
        enemyTwoCards[4] = new Card(Suit.DIAMONDS, Value.ACE);
        enemyTwoCards[5] = new Card(Suit.CLUBS, Value.ACE);

        this.players[0].setHand(new Hand(playerCards));
        this.players[1].setHand(new Hand(enemyOneCards));
        this.players[2].setHand(new Hand(enemyTwoCards));
        break;
      case 2:
        break;
      case 3:
        break;
      case 4:
        break;
      case 5:
        break;

      default:
        System.err.println(this.scenario + "th scenario does not exist.");
    }

  }

}
