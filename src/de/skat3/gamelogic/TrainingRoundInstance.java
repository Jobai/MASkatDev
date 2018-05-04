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
		case 2:// localClient starts
			break;
		case 3:
			this.setStartPlayer(2);// ai 2 starts
			break;
		case 4:// localClient starts
			break;
		case 5:// localClient starts
			break;
		default:
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
      case 1: //Trumpf Monopoly
        switch (currentRound) {
          case 0:
            switch (player) {
              case 0:
                return new Card(Suit.HEARTS, Value.JACK);
              case 1:
                return new Card(Suit.HEARTS, Value.TEN);
              case 2:
					return new Card(Suit.HEARTS, Value.QUEEN);
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
                return null;
            }
          case 2:
            switch (player) {
            case 0:
                return new Card(Suit.HEARTS, Value.NINE);
              case 1:
                return new Card(Suit.SPADES, Value.JACK);
              case 2:
                return new Card(Suit.CLUBS, Value.ACE);
              default:
                return null;
            }
			case 3:
				switch (player) {
				case 0:
					return new Card(Suit.HEARTS, Value.SEVEN);
				case 1:
					return new Card(Suit.DIAMONDS, Value.ACE);
				case 2:
					return new Card(Suit.CLUBS, Value.NINE);
				default:
					return null;
				}
			case 4:
				switch (player) {
				case 0:
					return new Card(Suit.SPADES, Value.TEN);
				case 1:
					return new Card(Suit.CLUBS, Value.EIGHT);
				case 2:
					return new Card(Suit.CLUBS, Value.ACE);
				default:
					return null;
				}
			case 5:
				switch (player) {
				case 0:
					return new Card(Suit.SPADES, Value.EIGHT);
				case 1:
					return new Card(Suit.DIAMONDS, Value.SEVEN);
				case 2:
					return new Card(Suit.DIAMONDS, Value.TEN);
				default:
					return null;
				}
          default:
            return null;
        }
      case 2: //Press Skat
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
		case 3:// Offer Points for your player
        switch (currentRound) {
          case 0:
            switch (player) {
              case 0:
					return new Card(Suit.CLUBS, Value.ACE);
              case 1:
					return new Card(Suit.SPADES, Value.JACK);
              case 2:
					return new Card(Suit.HEARTS, Value.JACK);
              default:
					return null;
            }
          case 1:
            switch (player) {
				case 0:
					return new Card(Suit.DIAMONDS, Value.TEN);
				case 1:
					return new Card(Suit.HEARTS, Value.NINE);
				case 2:
					return new Card(Suit.DIAMONDS, Value.ACE);
				default:
					return null;
            }
			case 2:
				switch (player) {
				case 0:
					return new Card(Suit.HEARTS, Value.EIGHT);
				case 1:
					return new Card(Suit.HEARTS, Value.KING);
				case 2:
					return new Card(Suit.HEARTS, Value.ACE);
				default:
					return null;
				}
			case 3:
				switch (player) {
				case 0:
					return new Card(Suit.DIAMONDS, Value.KING);
				case 1:
					return new Card(Suit.HEARTS, Value.SEVEN);
				case 2:
					return new Card(Suit.HEARTS, Value.TEN);
				default:
					return null;
				}
			case 4:
				switch (player) {
				case 0:
					return new Card(Suit.DIAMONDS, Value.EIGHT);
				case 1:
					return new Card(Suit.SPADES, Value.QUEEN);
				case 2:
					return new Card(Suit.DIAMONDS, Value.NINE);
				default:
					return null;
				}
			case 5:
				switch (player) {
				case 0:
					return new Card(Suit.CLUBS, Value.NINE);
				case 1:
					return new Card(Suit.SPADES, Value.TEN);
				case 2:
					return new Card(Suit.CLUBS, Value.EIGHT);
				default:
					return null;
				}
			default:
	            return null;
	        }

		case 4: // Play short to your friend
        switch (currentRound) {
          case 0:
            switch (player) {
              case 0:
					return new Card(Suit.SPADES, Value.ACE);
              case 1:
					return new Card(Suit.HEARTS, Value.NINE);
              case 2:
					return new Card(Suit.SPADES, Value.TEN);
              default:
                return null;
            }
          case 1:
            switch (player) {
				case 0:
					return new Card(Suit.SPADES, Value.QUEEN);
				case 1:
					return new Card(Suit.CLUBS, Value.NINE);
				case 2:
					return new Card(Suit.HEARTS, Value.ACE);
				default:
					return null;
            }
          case 2:
              switch (player) {
  				case 0:
  					return new Card(Suit.DIAMONDS, Value.ACE);
  				case 1:
  					return new Card(Suit.DIAMONDS, Value.NINE);
  				case 2:
  					return new Card(Suit.CLUBS, Value.KING);
  				default:
  					return null;
              }
          case 3:
              switch (player) {
  				case 0:
  					return new Card(Suit.CLUBS, Value.ACE);
  				case 1:
  					return new Card(Suit.CLUBS, Value.SEVEN);
  				case 2:
  					return new Card(Suit.CLUBS, Value.EIGHT);
  				default:
  					return null;
              }
          case 4:
              switch (player) {
  				case 0:
  					return new Card(Suit.DIAMONDS, Value.TEN);
  				case 1:
  					return new Card(Suit.DIAMONDS, Value.SEVEN);
  				case 2:
  					return new Card(Suit.HEARTS, Value.EIGHT);
  				default:
  					return null;
              }
          case 5:
              switch (player) {
  				case 0:
  					return new Card(Suit.HEARTS, Value.TEN);
  				case 1:
  					return new Card(Suit.CLUBS, Value.QUEEN);
  				case 2:
  					return new Card(Suit.HEARTS, Value.SEVEN);
  				default:
  					return null;
              }
        }
		case 5: // play long to the enemy
        switch (currentRound) {
          case 0:
            switch (player) {
              case 0:
					return new Card(Suit.HEARTS, Value.ACE);
              case 1:
					return new Card(Suit.HEARTS, Value.SEVEN);
              case 2:
					return new Card(Suit.HEARTS, Value.NINE);
              default:
                return null;
            }
          case 1:
              switch (player) {
                case 0:
  					return new Card(Suit.HEARTS, Value.TEN);
                case 1:
  					return new Card(Suit.HEARTS, Value.EIGHT);
                case 2:
  					return new Card(Suit.DIAMONDS, Value.ACE);
                default:
                  return null;
              }
          case 2:
              switch (player) {
                case 0:
  					return new Card(Suit.HEARTS, Value.KING);
                case 1:
  					return new Card(Suit.DIAMONDS, Value.NINE);
                case 2:
  					return new Card(Suit.CLUBS, Value.ACE);
                default:
                  return null;
              }
          case 3:
              switch (player) {
                case 0:
  					return new Card(Suit.SPADES, Value.ACE);
                case 1:
  					return new Card(Suit.SPADES, Value.QUEEN);
                case 2:
  					return new Card(Suit.SPADES, Value.TEN);
                default:
                  return null;
              }
			case 4:
				switch (player) {
				case 0:
					return new Card(Suit.SPADES, Value.KING);
				case 1:
					return new Card(Suit.DIAMONDS, Value.SEVEN);
				case 2:
					return new Card(Suit.DIAMONDS, Value.TEN);
				default:
					return null;
				}
			case 5:
				switch (player) {
				case 0:
					return new Card(Suit.DIAMONDS, Value.EIGHT);
				case 1:
					return new Card(Suit.CLUBS, Value.NINE);
				case 2:
					return new Card(Suit.CLUBS, Value.EIGHT);
				default:
					return null;
				}
              
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
		case 1: // Trumpf monopoly
			this.solo = this.players[0];
			this.solo.setSolo(true);
			Player[] team = this.getTeamPlayer();
			team[0].setSolo(false);
			team[1].setSolo(false);
			this.contract = Contract.HEARTS;
			break;
		case 2: // Press Skat
			this.solo = this.players[0];
			this.solo.setSolo(true);
			Player[] team2 = this.getTeamPlayer();
			team2[0].setSolo(false);
			team2[1].setSolo(false);
			this.contract = Contract.GRAND;
			break;
		case 3:// Offer Points to player
			this.solo = this.players[1];
			this.solo.setSolo(true);
			Player[] team3 = this.getTeamPlayer();
			team3[0].setSolo(false);
			team3[1].setSolo(false);
			this.contract = Contract.GRAND;
          break;
		case 4: // short to friend
			this.solo = this.players[1];
			this.solo.setSolo(true);
			Player[] team4 = this.getTeamPlayer();
			team4[0].setSolo(false);
			team4[1].setSolo(false);
			this.contract = Contract.GRAND;
			break;
		case 5: // long to enemy
			this.solo = this.players[1];
			this.solo.setSolo(true);
			Player[] team5 = this.getTeamPlayer();
			team5[0].setSolo(false);
			team5[1].setSolo(false);
			this.contract = Contract.GRAND;
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
			 * Players Hand: Herz_Bube, Herz_9, Herz_Ass, Herz_7, Pik_8, Pik_10 Ai 1 Hand:
			 * Herz_König, Herz_10, Pik_Ass, Pik_Bube, Karo_7, Kreuz_8 Ai 2 Hand: Karo_8,
			 * Herz_Dame, Karo_10, Kreuz_9, Karo_Ass, Kreuz_Ass
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
        enemyOneCards[3] = new Card(Suit.SPADES, Value.JACK);
        enemyOneCards[4] = new Card(Suit.DIAMONDS, Value.SEVEN);
        enemyOneCards[5] = new Card(Suit.CLUBS, Value.EIGHT);


        Card[] enemyTwoCards = new Card[this.startHandSize];
        enemyTwoCards[0] = new Card(Suit.DIAMONDS, Value.EIGHT);
		enemyTwoCards[1] = new Card(Suit.HEARTS, Value.QUEEN);
        enemyTwoCards[2] = new Card(Suit.DIAMONDS, Value.TEN);
        enemyTwoCards[3] = new Card(Suit.CLUBS, Value.NINE);
        enemyTwoCards[4] = new Card(Suit.DIAMONDS, Value.ACE);
        enemyTwoCards[5] = new Card(Suit.CLUBS, Value.ACE);

        this.players[0].setHand(new Hand(playerCards));
        this.players[1].setHand(new Hand(enemyOneCards));
        this.players[2].setHand(new Hand(enemyTwoCards));
        break;
      case 2:
			/*
			 * Players Hand: Herz_Bube, Herz_9, Herz_Ass, Herz_7, Pik_Dame, Pik_10 Ai 1
			 * Hand: Herz_König, Herz_10, Pik_Ass, Karo_9, Karo_10, Kreuz_8 Ai 2 Hand:
			 * Karo_8, Herz_9, Karo_10, Kreuz_9, Karo_Ass, Kreuz_Ass
			 */
			Card[] playerCards2 = new Card[this.startHandSize];
			playerCards2[0] = new Card(Suit.HEARTS, Value.JACK);
			playerCards2[1] = new Card(Suit.HEARTS, Value.NINE);
			playerCards2[2] = new Card(Suit.HEARTS, Value.ACE);
			playerCards2[3] = new Card(Suit.HEARTS, Value.SEVEN);
			playerCards2[4] = new Card(Suit.SPADES, Value.QUEEN);
			playerCards2[5] = new Card(Suit.SPADES, Value.TEN);


          Card[] enemyOneCards2 = new Card[this.startHandSize];
			enemyOneCards2[0] = new Card(Suit.HEARTS, Value.KING);
			enemyOneCards2[1] = new Card(Suit.HEARTS, Value.TEN);
			enemyOneCards2[2] = new Card(Suit.SPADES, Value.ACE);
			enemyOneCards2[3] = new Card(Suit.DIAMONDS, Value.NINE);
			enemyOneCards2[4] = new Card(Suit.DIAMONDS, Value.TEN);
			enemyOneCards2[5] = new Card(Suit.CLUBS, Value.EIGHT);


			Card[] enemyTwoCards2 = new Card[this.startHandSize];
			enemyTwoCards2[0] = new Card(Suit.DIAMONDS, Value.EIGHT);
			enemyTwoCards2[1] = new Card(Suit.HEARTS, Value.NINE);
			enemyTwoCards2[2] = new Card(Suit.DIAMONDS, Value.TEN);
			enemyTwoCards2[3] = new Card(Suit.CLUBS, Value.NINE);
			enemyTwoCards2[4] = new Card(Suit.DIAMONDS, Value.ACE);
			enemyTwoCards2[5] = new Card(Suit.CLUBS, Value.ACE);

			this.players[0].setHand(new Hand(playerCards2));
			this.players[1].setHand(new Hand(enemyOneCards2));
			this.players[2].setHand(new Hand(enemyTwoCards2));
        break;
      case 3:
			/*
			 Player Hand: Karo_8, Herz_König, Herz_Ass, Herz_10, Pik_ass, Pik_10
			 Ai 2 Hand: Pik_10, Karo_Ass; Herz_9, Karo_10, Kreuz_Ass, Kreuz_8
			 Ai 1 Hand: Herz 8, Karo_7, Karo_9, Kreuz_9, Pik_Dame, Herz_7
			 */
    		Card[] playerCards3 = new Card[this.startHandSize];
			playerCards3[0] = new Card(Suit.DIAMONDS, Value.EIGHT);
			playerCards3[1] = new Card(Suit.HEARTS, Value.KING);
			playerCards3[2] = new Card(Suit.HEARTS, Value.ACE);
			playerCards3[3] = new Card(Suit.HEARTS, Value.TEN);
			playerCards3[4] = new Card(Suit.SPADES, Value.ACE);
			playerCards3[5] = new Card(Suit.SPADES, Value.TEN);
			
			Card[] enemyOneCards3 = new Card[this.startHandSize];
			enemyOneCards3[0] = new Card(Suit.HEARTS, Value.EIGHT);
			enemyOneCards3[1] = new Card(Suit.DIAMONDS, Value.SEVEN);
			enemyOneCards3[2] = new Card(Suit.DIAMONDS, Value.NINE);
			enemyOneCards3[3] = new Card(Suit.CLUBS, Value.NINE);
			enemyOneCards3[4] = new Card(Suit.SPADES, Value.QUEEN);
			enemyOneCards3[5] = new Card(Suit.HEARTS, Value.SEVEN);

			Card[] enemyTwoCards3 = new Card[this.startHandSize];
			enemyTwoCards3[0] = new Card(Suit.SPADES, Value.TEN);
			enemyTwoCards3[1] = new Card(Suit.DIAMONDS, Value.ACE);///xxx
			enemyTwoCards3[2] = new Card(Suit.HEARTS, Value.NINE);
			enemyTwoCards3[3] = new Card(Suit.DIAMONDS, Value.TEN);
			enemyTwoCards3[4] = new Card(Suit.CLUBS, Value.ACE);
			enemyTwoCards3[5] = new Card(Suit.CLUBS, Value.EIGHT);

			this.players[0].setHand(new Hand(playerCards3));
			this.players[1].setHand(new Hand(enemyOneCards3));
			this.players[2].setHand(new Hand(enemyTwoCards3));
        break;
      case 4:
			/* Player Hand: Pik_Ass, Herz_10, Pik_Dame Karo_Ass, Karo_10, Kreuz_Ass 
			 * Ai 2 Hand: Pik_Ten, Herz_8, Herz_Ass, Herz_7, Kreuz_8, Kreuz_König
			 * Ai 1 Hand: Karo_9,Herz_9,Karo_Dame, Kreuz_9, Karo_7, Kreuz_7
			 */
			Card[] playerCards4 = new Card[this.startHandSize];
			playerCards4[0] = new Card(Suit.SPADES, Value.ACE);
			playerCards4[1] = new Card(Suit.HEARTS, Value.TEN);
			playerCards4[2] = new Card(Suit.CLUBS, Value.ACE);
			playerCards4[3] = new Card(Suit.DIAMONDS, Value.ACE);
			playerCards4[4] = new Card(Suit.DIAMONDS, Value.TEN);
			playerCards4[5] = new Card(Suit.SPADES, Value.QUEEN);
			
			Card[] enemyOneCards4 = new Card[this.startHandSize];
			enemyOneCards4[0] = new Card(Suit.DIAMONDS, Value.NINE);
			enemyOneCards4[1] = new Card(Suit.HEARTS, Value.NINE);
			enemyOneCards4[2] = new Card(Suit.DIAMONDS, Value.QUEEN);
			enemyOneCards4[3] = new Card(Suit.CLUBS, Value.NINE);
			enemyOneCards4[4] = new Card(Suit.DIAMONDS, Value.ACE);
			enemyOneCards4[5] = new Card(Suit.CLUBS, Value.SEVEN);
			
			Card[] enemyTwoCards4 = new Card[this.startHandSize];
			enemyTwoCards4[0] = new Card(Suit.SPADES, Value.TEN);
			enemyTwoCards4[1] = new Card(Suit.HEARTS, Value.EIGHT);
			enemyTwoCards4[2] = new Card(Suit.HEARTS, Value.ACE);
			enemyTwoCards4[3] = new Card(Suit.HEARTS, Value.SEVEN);
			enemyTwoCards4[4] = new Card(Suit.CLUBS, Value.EIGHT);
			enemyTwoCards4[5] = new Card(Suit.CLUBS, Value.KING);

			this.players[0].setHand(new Hand(playerCards4));
			this.players[1].setHand(new Hand(enemyOneCards4));
			this.players[2].setHand(new Hand(enemyTwoCards4));

        break;
      case 5:
			// Player Hand: Herz_7, Herz_9, Herz_Ass, Herz_8, Pik_Dame, Pik_König
			// Ai 2 Hand: Pik_10, Herz_10, Herz_König, Karo_9, Karo_10, Kreuz_8
			// Ai 1 Hand: Karo_Ass, Karo_7, Karo_10, Kreuz_9, Pik_Ass, Kreuz_Ass

			Card[] playerCards5 = new Card[this.startHandSize];
			playerCards5[0] = new Card(Suit.HEARTS, Value.SEVEN);
			playerCards5[1] = new Card(Suit.HEARTS, Value.NINE);
			playerCards5[2] = new Card(Suit.HEARTS, Value.EIGHT);
			playerCards5[3] = new Card(Suit.SPADES, Value.QUEEN);
			playerCards5[4] = new Card(Suit.SPADES, Value.KING);
			playerCards5[5] = new Card(Suit.HEARTS, Value.ACE);

			Card[] enemyOneCards5 = new Card[this.startHandSize];
			enemyOneCards5[0] = new Card(Suit.DIAMONDS, Value.ACE);
			enemyOneCards5[1] = new Card(Suit.DIAMONDS, Value.SEVEN);
			enemyOneCards5[2] = new Card(Suit.DIAMONDS, Value.TEN);
			enemyOneCards5[3] = new Card(Suit.CLUBS, Value.NINE);
			enemyOneCards5[4] = new Card(Suit.SPADES, Value.ACE);
			enemyOneCards5[5] = new Card(Suit.CLUBS, Value.ACE);

			Card[] enemyTwoCards5 = new Card[this.startHandSize];
			enemyTwoCards5[0] = new Card(Suit.SPADES, Value.TEN);
			enemyTwoCards5[1] = new Card(Suit.HEARTS, Value.TEN);
			enemyTwoCards5[2] = new Card(Suit.HEARTS, Value.KING);
			enemyTwoCards5[3] = new Card(Suit.DIAMONDS, Value.NINE);
			enemyTwoCards5[4] = new Card(Suit.DIAMONDS, Value.EIGHT);
			enemyTwoCards5[5] = new Card(Suit.CLUBS, Value.EIGHT);

			this.players[0].setHand(new Hand(playerCards5));
			this.players[1].setHand(new Hand(enemyOneCards5));
			this.players[2].setHand(new Hand(enemyTwoCards5));

        break;

      default:
        System.err.println(this.scenario + "th scenario does not exist.");
    }

  }

}
