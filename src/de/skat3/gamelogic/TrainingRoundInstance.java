package de.skat3.gamelogic;

import de.skat3.main.SkatMain;
import de.skat3.network.server.ServerLogicController;
import javafx.application.Platform;

/**
 * A scripted scenario for the training mode.
 * 
 * @author Kai Baumann, @author Emre Cura
 *
 */
public class TrainingRoundInstance extends RoundInstance {
  int startHandSize = 6;
  int scenario;
  int delay;
  int currentPartInSkatBasics;

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
  }

  @Override
  void startRound() throws InterruptedException {
    synchronized (lock) {
      if (this.scenario == 0) {
        this.startHandSize = 10;

        Platform.runLater(new Runnable() {
          @Override
          public void run() {
            SkatMain.guiController.getInGameController().showTrainingModeInfoText(
                "/trainingPopups/SkatBasics/SkatBasicIntroduction.html", 400, 300,
                TrainingRoundInstance.this);
          }
        });
        lock.wait();
        // 1a)

        Platform.runLater(new Runnable() {
          @Override
          public void run() {
            SkatMain.guiController.getInGameController().showTrainingModeInfoText(
                "/trainingPopups/SkatBasics/Bidding.html", 400, 300, TrainingRoundInstance.this);
          }
        });
        lock.wait();
        this.currentPartInSkatBasics = 0;
        this.initializeAuction();
        this.updatePlayer();
        this.updateEnemies();
        this.slc.broadcastRoundStarted();

        // bidding starts

        this.startBidding();

        Thread.sleep(5000);
        // 1b)


        this.currentPartInSkatBasics = 1;
        this.initializeAuction();
        this.updatePlayer();
        this.updateEnemies();
        this.slc.broadcastRoundStarted();

        // bidding starts

        this.startBidding();

        Thread.sleep(5000);

        // 2a)


        Platform.runLater(new Runnable() {
          @Override
          public void run() {
            SkatMain.guiController.getInGameController().showTrainingModeInfoText(
                "/trainingPopups/SkatBasics/Contracts+Multipliers.html", 400, 300,
                TrainingRoundInstance.this);
          }
        });
        lock.wait();



        this.currentPartInSkatBasics = 2;
        this.initializeAuction();
        this.updatePlayer();
        this.updateEnemies();
        this.slc.broadcastRoundStarted();


        Platform.runLater(new Runnable() {
          @Override
          public void run() {
            SkatMain.guiController.getInGameController().showTrainingModeInfoText(
                "/trainingPopups/SkatBasics/LotsOfClubs.html", 400, 300,
                TrainingRoundInstance.this);
          }
        });
        lock.wait();

        // bidding starts

        Thread.sleep(5000);


        // 2b)
        this.currentPartInSkatBasics = 3;
        this.initializeAuction();
        this.updatePlayer();
        this.updateEnemies();
        this.slc.broadcastRoundStarted();


        Platform.runLater(new Runnable() {
          @Override
          public void run() {
            SkatMain.guiController.getInGameController().showTrainingModeInfoText(
                "/trainingPopups/SkatBasics/LotsOfJacks.html", 400, 300,
                TrainingRoundInstance.this);
          }
        });
        lock.wait();


        // 2c

        this.currentPartInSkatBasics = 4;
        this.initializeAuction();
        this.updatePlayer();
        this.updateEnemies();
        this.slc.broadcastRoundStarted();


        Platform.runLater(new Runnable() {
          @Override
          public void run() {
            SkatMain.guiController.getInGameController().showTrainingModeInfoText(
                "/trainingPopups/SkatBasics/PlayNullOuvert.html", 400, 300,
                TrainingRoundInstance.this);
          }
        });
        lock.wait();


        Platform.runLater(new Runnable() {
          @Override
          public void run() {
            SkatMain.guiController.getInGameController().showTrainingModeInfoText(
                "/trainingPopups/SkatBasics/CalculatePoints.html", 400, 300,
                TrainingRoundInstance.this);
          }
        });
        lock.wait();

        Thread.sleep(5000);



      } else {
        this.initializeAuction();
        this.updatePlayer();
        this.updateEnemies();
        this.setStartingPlayer();
        this.setSoloAndContract();
        slc.broadcastRoundStarted();
        this.startGame();
      }
      Platform.runLater(new Runnable() {
        @Override
        public void run() {
          SkatMain.guiController.getInGameController().showTrainingModeInfoText(
              "/trainingPopups/SuccessPopUp.html", 400, 300, TrainingRoundInstance.this);
        }
      });
      lock.wait();
    }

  }

  private boolean currentAnswer;

  private Player startBiddingOne() throws InterruptedException {
    synchronized (lock) {

      Platform.runLater(new Runnable() {
        @Override
        public void run() {
          SkatMain.guiController.getInGameController().showTrainingModeInfoText(
              "/trainingPopups/SkatBasics/Bidding_firstBid.html", 400, 300,
              TrainingRoundInstance.this);
        }
      });
      lock.wait();
      this.current = LogicAnswers.BID;
      Player bid = this.players[0];

      this.currentBidder = bid;
      this.currentAnswer = true;
      SkatMain.mainController.tutorialBidRequest(BiddingValues.values[this.currentBiddingValue],
          currentAnswer);
      lock.wait();

      this.setBid(currentAnswer);
      Thread.sleep(1000);
      this.broadcastBid();

      Player respond = this.players[1];
      currentAnswer = true;
      this.currentBidder = respond;
      this.setBid(currentAnswer);
      Thread.sleep(2000);
      this.broadcastBid();

      this.currentBiddingValue++;


      this.currentBidder = bid;
      this.currentAnswer = true;
      SkatMain.mainController.tutorialBidRequest(BiddingValues.values[this.currentBiddingValue],
          currentAnswer);
      lock.wait();

      currentAnswer = false;
      this.currentBidder = respond;
      this.setBid(currentAnswer);
      Thread.sleep(2000);
      this.broadcastBid();

      this.currentBiddingValue++;

      bid = players[2];



      this.currentBidder = bid;
      this.currentAnswer = false;

      this.setBid(currentAnswer);
      Thread.sleep(2000);
      this.broadcastBid();

      Platform.runLater(new Runnable() {
        @Override
        public void run() {
          SkatMain.guiController.getInGameController().showTrainingModeInfoText(
              "/trainingPopups/SkatBasics/Bidding_AfterSecondBid.html", 400, 300,
              TrainingRoundInstance.this);
        }
      });
      lock.wait();

      return bid;


    }

  }


  private Player startBiddingTwo() throws InterruptedException {
    synchronized (lock) {

      Player bid = players[2];
      currentAnswer = true;
      this.currentBidder = bid;
      this.setBid(currentAnswer);
      Thread.sleep(2000);
      this.broadcastBid();


      Player respond = players[1];

      currentAnswer = false;
      this.currentBidder = respond;
      this.setBid(currentAnswer);
      Thread.sleep(2000);
      this.broadcastBid();



      bid = players[0];
      Platform.runLater(new Runnable() {
        @Override
        public void run() {
          SkatMain.guiController.getInGameController().showTrainingModeInfoText(
              "/trainingPopups/SkatBasics/Bidding_firstBidBadCards.html", 400, 300,
              TrainingRoundInstance.this);
        }
      });
      lock.wait();

      this.currentBidder = bid;
      this.currentAnswer = false;
      SkatMain.mainController.tutorialBidRequest(BiddingValues.values[this.currentBiddingValue],
          currentAnswer);
      lock.wait();


      Platform.runLater(new Runnable() {
        @Override
        public void run() {
          SkatMain.guiController.getInGameController().showTrainingModeInfoText(
              "/trainingPopups/SkatBasics/Bidding_AfterSecondBadBid.html", 400, 300,
              TrainingRoundInstance.this);
        }
      });
      lock.wait();



      this.currentBiddingValue++;

      return respond;
    }
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
              this.getScriptedCard(currentRound, 0));
          this.showPopUp(currentRound);
          this.current = LogicAnswers.CARD;
          this.lock.wait();
        }
        slc.updatePlayerDuringRound(this.players[0].copyPlayer());
        System.out.println("trick0 : " + this.trick[0]);
        slc.sendPlayedCard(this.players[0].copyPlayer(), this.trick[0].copy());

        System.out.println("2 is bot: " + this.players[1].isBot);
        if (this.players[1].isBot) {
          Thread.sleep(this.delay);
          Card current = this.getScriptedCard(currentRound, 1);
          this.addCardtoTrick(current);
        } else {
          slc.callForSpecificPlay(this.players[1].copyPlayer(),
              this.getScriptedCard(currentRound, 1));
          this.showPopUp(currentRound);
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
          this.showPopUp(currentRound);
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
      }
      Platform.runLater(new Runnable() {
        @Override
        public void run() {
          SkatMain.guiController.getInGameController().showTrainingModeInfoText(
              "/trainingPopups/SuccessPopUp.html", 400, 300, TrainingRoundInstance.this);
        }
      });
      lock.wait();
    }
  }

  @Override
  public Player startBidding() throws InterruptedException {
    Thread.sleep(5000);
    this.currentBiddingValue = 0;
    this.respond = false;
    switch (this.currentPartInSkatBasics) {
      case 0:
        return this.startBiddingOne();
      case 1:
        return this.startBiddingTwo();
      default:
        break;

    }
    return currentBidder;
  }

  @Override
  Player bidDuel(Player bid, Player respond) throws InterruptedException {


    while (this.currentBiddingValue < BiddingValues.values.length) {
      this.currentBidder = bid;
      this.slc.callForBid(bid.copyPlayer(), BiddingValues.values[this.currentBiddingValue]);
      this.current = LogicAnswers.BID;
      lock.wait();
      if (this.bidAccepted) {
        this.currentBidder = respond;
        this.slc.callForBid(respond.copyPlayer(), BiddingValues.values[this.currentBiddingValue]);
        this.current = LogicAnswers.BID;
        lock.wait();
        if (this.bidAccepted) {
          this.currentBiddingValue++;
          continue;
        } else {
          this.currentBiddingValue++;
          return bid;
        }
      } else {
        return respond;
      }
    }
    return bid;

  }



  String filePop = "/trainingPopups";
  private int width = 600;
  private int height = 400;
  boolean popUpShown = false;

  /**
   * Shows the Information PopUp for the specific Scenario.
   * 
   * @param currentRound represents the current Round in the Scenario.
   */
  private void showPopUp(int currentRound) throws InterruptedException {
    switch (this.scenario) {
      case 1:
        switch (currentRound) {
          case 0:
            width = 400;
            height = 300;
            filePop = "/trainingPopups";
            filePop += "/Skat Strategies/Scenario1/Scenario1_Popup1.html";
            break;
          case 1:
            System.out.println("2222");

            width = 400;
            height = 300;
            filePop = "/trainingPopups";
            filePop += "/Skat Strategies/Scenario1/Scenario1_Popup2.html";

            System.out.println("filePop" + "\n" + filePop);

            break;
          case 2:
            width = 400;
            height = 300;
            filePop = "/trainingPopups";
            filePop += "/Skat Strategies/Scenario1/Scenario1_Popup3.html";
            break;
          case 3:
            width = 400;
            height = 300;
            filePop = "/trainingPopups";
            filePop += "/Skat Strategies/Scenario1/Scenario1_Popup4.html";
            break;
          case 4:
            return;
          case 5:
            return;
          default:
            return;
        }
        break;
      case 2:
        switch (currentRound) {
          case 0:
            width = 400;
            height = 300;
            filePop = "/trainingPopups";
            filePop += "/Skat Strategies/Scenario2/Scenario2_Popup1.html";
            break;
          case 1:
            return;
          case 2:
            return;
          case 3:
            return;
          case 4:
            return;
          case 5:
            return;
          default:
            return;
        }
        break;
      case 3:
        switch (currentRound) {
          case 0:
            width = 400;
            height = 300;
            filePop = "/trainingPopups";
            filePop += "/Skat Strategies/Scenario3/Scenario3_Popup1.html";
            break;
          case 1:
            width = 400;
            height = 300;
            filePop = "/trainingPopups";
            filePop += "/Skat Strategies/Scenario3/Scenario3_Popup2.html";
            break;
          case 2:
            width = 400;
            height = 300;
            filePop = "/trainingPopups";
            filePop += "/Skat Strategies/Scenario3/Scenario3_Popup3.html";
            break;
          case 3:
            width = 400;
            height = 300;
            filePop = "/trainingPopups";
            filePop += "/Skat Strategies/Scenario3/Scenario3_Popup4.html";
            break;
          case 4:
            return;
          case 5:
            return;
          default:
            return;
        }
        break;
      case 4:
        switch (currentRound) {
          case 0:
            width = 400;
            height = 300;
            filePop = "/trainingPopups";
            filePop += "/Skat Strategies/Scenario4/Scenario4_Popup1.html";
            break;
          case 1:
            width = 400;
            height = 300;
            filePop = "/trainingPopups";
            filePop += "/Skat Strategies/Scenario4/Scenario4_Popup2.html";
            break;
          case 2:
            width = 400;
            height = 300;
            filePop = "/trainingPopups";
            filePop += "/Skat Strategies/Scenario4/Scenario4_Popup3.html";
            break;
          case 3:
            width = 400;
            height = 300;
            filePop = "/trainingPopups";
            filePop += "/Skat Strategies/Scenario4/Scenario4_Popup4.html";
            break;
          case 4:
            return;
          case 5:
            return;
          default:
            return;
        }
        break;
      case 5:
        switch (currentRound) {
          case 0:
            width = 400;
            height = 300;
            filePop = "/trainingPopups";
            filePop += "/Skat Strategies/Scenario5/Scenario5_Popup1.html";
            break;
          case 1:
            width = 400;
            height = 300;
            filePop = "/trainingPopups";
            filePop += "/Skat Strategies/Scenario5/Scenario5_Popup2.html";
            break;
          case 2:
            width = 400;
            height = 300;
            filePop = "/trainingPopups";
            filePop += "/Skat Strategies/Scenario5/Scenario5_Popup3.html";
            break;
          case 3:
            return;
          case 4:
            return;
          case 5:
            return;
          default:
            return;
        }
        break;

      default:
        return;

    }
    synchronized (lock) {
      Platform.runLater(new Runnable() {
        @Override
        public void run() {
          try {
            SkatMain.guiController.getInGameController().showTrainingModeInfoText(filePop, width,
                height, TrainingRoundInstance.this);
          } catch (NullPointerException ex) {
            System.err.println("No popUp shown" + ex);

          }
        }
      });
      lock.wait();
    }
  }

  /**
   * Returns the card that the player or ai should play in a specific scenario and round.
   * 
   * @return
   */
  private Card getScriptedCard(int currentRound, int player) {
    switch (this.scenario) {
      case 0:
        return null;
      case 1: // Trumpf Monopoly
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
      case 2: // Importance of Trump Card
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
      case 3:// Offer Points for your player
        switch (currentRound) {
          case 0:
            switch (player) {
              case 2:
                return new Card(Suit.CLUBS, Value.ACE);
              case 1:
                return new Card(Suit.SPADES, Value.TEN);
              case 0:
                return new Card(Suit.SPADES, Value.ACE);
              default:
                return null;
            }
          case 1:
            switch (player) {
              case 2:
                return new Card(Suit.DIAMONDS, Value.TEN);
              case 1:
                return new Card(Suit.HEARTS, Value.NINE);
              case 0:
                return new Card(Suit.DIAMONDS, Value.ACE);
              default:
                return null;
            }
          case 2:
            switch (player) {
              case 2:
                return new Card(Suit.DIAMONDS, Value.KING);
              case 1:
                return new Card(Suit.HEARTS, Value.KING);
              case 0:
                return new Card(Suit.HEARTS, Value.TEN);
              default:
                return null;
            }
          case 3:
            switch (player) {
              case 2:
                return new Card(Suit.DIAMONDS, Value.EIGHT);
              case 1:
                return new Card(Suit.HEARTS, Value.ACE);
              case 0:
                return new Card(Suit.DIAMONDS, Value.NINE);
              default:
                return null;
            }
          case 4:
            switch (player) {
              case 2:
                return new Card(Suit.CLUBS, Value.EIGHT);
              case 1:
                return new Card(Suit.HEARTS, Value.SEVEN);
              case 0:
                return new Card(Suit.CLUBS, Value.NINE);
              default:
                return null;
            }
          case 5:
            switch (player) {
              case 2:
                return new Card(Suit.HEARTS, Value.EIGHT);
              case 1:
                return new Card(Suit.SPADES, Value.QUEEN);
              case 0:
                return new Card(Suit.SPADES, Value.SEVEN);
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
          default:
            break;
        }
        break;
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
            System.out.println("111");
            switch (player) {
              case 0:
                System.out.println("222");
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
          default:
            break;
        }
        break;
      default:
        System.out.println("error");
        return null;
    }
    return null;
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
        this.contract = Contract.HEARTS;
        break;
      case 3:// Offer Points to player
        this.solo = this.players[2];
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
    this.addtionalMultipliers = new AdditionalMultipliers();
    System.out.println("contract tr = " + this.contract);
    slc.broadcastContract(this.contract, new AdditionalMultipliers());

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



  /**
   * Deals cards to every player depending on the current scenario. No cards are dealt at random!
   */
  @Override
  void dealCards() {

    switch (this.scenario) {

      case 0:
        switch (this.currentPartInSkatBasics) {
          case 0:
            // good cards
            Card[] playerCards = new Card[this.startHandSize];
            playerCards[0] = new Card(Suit.HEARTS, Value.JACK);
            playerCards[1] = new Card(Suit.DIAMONDS, Value.JACK);
            playerCards[2] = new Card(Suit.CLUBS, Value.JACK);
            playerCards[3] = new Card(Suit.SPADES, Value.JACK);
            playerCards[4] = new Card(Suit.CLUBS, Value.ACE);
            playerCards[5] = new Card(Suit.CLUBS, Value.TEN);
            playerCards[6] = new Card(Suit.SPADES, Value.ACE);
            playerCards[7] = new Card(Suit.SPADES, Value.TEN);
            playerCards[8] = new Card(Suit.HEARTS, Value.ACE);
            playerCards[9] = new Card(Suit.HEARTS, Value.EIGHT);

            // bad cards
            Card[] enemyOneCards = new Card[this.startHandSize];
            enemyOneCards[0] = new Card(Suit.CLUBS, Value.KING);
            enemyOneCards[1] = new Card(Suit.HEARTS, Value.TEN);
            enemyOneCards[2] = new Card(Suit.SPADES, Value.QUEEN);
            enemyOneCards[3] = new Card(Suit.HEARTS, Value.SEVEN);
            enemyOneCards[4] = new Card(Suit.DIAMONDS, Value.SEVEN);
            enemyOneCards[5] = new Card(Suit.CLUBS, Value.QUEEN);
            enemyOneCards[6] = new Card(Suit.SPADES, Value.KING);
            enemyOneCards[7] = new Card(Suit.DIAMONDS, Value.NINE);
            enemyOneCards[8] = new Card(Suit.DIAMONDS, Value.QUEEN);
            enemyOneCards[9] = new Card(Suit.CLUBS, Value.EIGHT);


            // bad cards
            Card[] enemyTwoCards = new Card[this.startHandSize];
            enemyTwoCards[0] = new Card(Suit.DIAMONDS, Value.EIGHT);
            enemyTwoCards[1] = new Card(Suit.HEARTS, Value.QUEEN);
            enemyTwoCards[2] = new Card(Suit.DIAMONDS, Value.TEN);
            enemyTwoCards[3] = new Card(Suit.CLUBS, Value.NINE);
            enemyTwoCards[4] = new Card(Suit.DIAMONDS, Value.ACE);
            enemyTwoCards[5] = new Card(Suit.SPADES, Value.NINE);
            enemyTwoCards[6] = new Card(Suit.CLUBS, Value.SEVEN);
            enemyTwoCards[7] = new Card(Suit.HEARTS, Value.NINE);
            enemyTwoCards[8] = new Card(Suit.SPADES, Value.SEVEN);
            enemyTwoCards[9] = new Card(Suit.SPADES, Value.EIGHT);

            this.players[0].setHand(new Hand(playerCards));
            this.players[1].setHand(new Hand(enemyOneCards));
            this.players[2].setHand(new Hand(enemyTwoCards));
            break;
          case 1:
            // bad cards
            Card[] playerCards1 = new Card[this.startHandSize];
            playerCards1[0] = new Card(Suit.CLUBS, Value.KING);
            playerCards1[1] = new Card(Suit.HEARTS, Value.TEN);
            playerCards1[2] = new Card(Suit.SPADES, Value.QUEEN);
            playerCards1[3] = new Card(Suit.HEARTS, Value.SEVEN);
            playerCards1[4] = new Card(Suit.DIAMONDS, Value.SEVEN);
            playerCards1[5] = new Card(Suit.CLUBS, Value.QUEEN);
            playerCards1[6] = new Card(Suit.SPADES, Value.KING);
            playerCards1[7] = new Card(Suit.DIAMONDS, Value.NINE);
            playerCards1[8] = new Card(Suit.DIAMONDS, Value.QUEEN);
            playerCards1[9] = new Card(Suit.CLUBS, Value.EIGHT);

            // good cards
            Card[] enemyOneCards1 = new Card[this.startHandSize];
            enemyOneCards1[0] = new Card(Suit.HEARTS, Value.JACK);
            enemyOneCards1[1] = new Card(Suit.DIAMONDS, Value.JACK);
            enemyOneCards1[2] = new Card(Suit.CLUBS, Value.JACK);
            enemyOneCards1[3] = new Card(Suit.SPADES, Value.JACK);
            enemyOneCards1[4] = new Card(Suit.CLUBS, Value.ACE);
            enemyOneCards1[5] = new Card(Suit.CLUBS, Value.TEN);
            enemyOneCards1[6] = new Card(Suit.SPADES, Value.ACE);
            enemyOneCards1[7] = new Card(Suit.SPADES, Value.TEN);
            enemyOneCards1[8] = new Card(Suit.HEARTS, Value.ACE);
            enemyOneCards1[9] = new Card(Suit.HEARTS, Value.EIGHT);

            // bad cards
            Card[] enemyTwoCards1 = new Card[this.startHandSize];
            enemyTwoCards1[0] = new Card(Suit.DIAMONDS, Value.EIGHT);
            enemyTwoCards1[1] = new Card(Suit.HEARTS, Value.QUEEN);
            enemyTwoCards1[2] = new Card(Suit.DIAMONDS, Value.TEN);
            enemyTwoCards1[3] = new Card(Suit.CLUBS, Value.NINE);
            enemyTwoCards1[4] = new Card(Suit.DIAMONDS, Value.ACE);
            enemyTwoCards1[5] = new Card(Suit.SPADES, Value.NINE);
            enemyTwoCards1[6] = new Card(Suit.CLUBS, Value.SEVEN);
            enemyTwoCards1[7] = new Card(Suit.HEARTS, Value.NINE);
            enemyTwoCards1[8] = new Card(Suit.SPADES, Value.SEVEN);
            enemyTwoCards1[9] = new Card(Suit.SPADES, Value.EIGHT);

            this.players[0].setHand(new Hand(playerCards1));
            this.players[1].setHand(new Hand(enemyOneCards1));
            this.players[2].setHand(new Hand(enemyTwoCards1));
            break;
          case 2:

            // lots of clubs card
            Card[] playerCards5 = new Card[this.startHandSize];
            playerCards5[0] = new Card(Suit.CLUBS, Value.KING);
            playerCards5[1] = new Card(Suit.CLUBS, Value.ACE);
            playerCards5[2] = new Card(Suit.CLUBS, Value.JACK);
            playerCards5[3] = new Card(Suit.HEARTS, Value.ACE);
            playerCards5[4] = new Card(Suit.DIAMONDS, Value.SEVEN);
            playerCards5[5] = new Card(Suit.CLUBS, Value.QUEEN);
            playerCards5[6] = new Card(Suit.SPADES, Value.KING);
            playerCards5[7] = new Card(Suit.HEARTS, Value.JACK);
            playerCards5[8] = new Card(Suit.CLUBS, Value.TEN);
            playerCards5[9] = new Card(Suit.CLUBS, Value.EIGHT);

            // average cards
            Card[] enemyOneCards5 = new Card[this.startHandSize];
            enemyOneCards5[0] = new Card(Suit.DIAMONDS, Value.NINE);
            enemyOneCards5[1] = new Card(Suit.DIAMONDS, Value.EIGHT);
            enemyOneCards5[2] = new Card(Suit.SPADES, Value.QUEEN);
            enemyOneCards5[3] = new Card(Suit.SPADES, Value.JACK);
            enemyOneCards5[4] = new Card(Suit.HEARTS, Value.TEN);
            enemyOneCards5[5] = new Card(Suit.DIAMONDS, Value.QUEEN);
            enemyOneCards5[6] = new Card(Suit.SPADES, Value.NINE);
            enemyOneCards5[7] = new Card(Suit.SPADES, Value.TEN);
            enemyOneCards5[8] = new Card(Suit.HEARTS, Value.SEVEN);
            enemyOneCards5[9] = new Card(Suit.HEARTS, Value.EIGHT);

            // average cards
            Card[] enemyTwoCards5 = new Card[this.startHandSize];
            enemyTwoCards5[0] = new Card(Suit.DIAMONDS, Value.JACK);
            enemyTwoCards5[1] = new Card(Suit.HEARTS, Value.QUEEN);
            enemyTwoCards5[2] = new Card(Suit.DIAMONDS, Value.TEN);
            enemyTwoCards5[3] = new Card(Suit.CLUBS, Value.NINE);
            enemyTwoCards5[4] = new Card(Suit.DIAMONDS, Value.ACE);
            enemyTwoCards5[5] = new Card(Suit.SPADES, Value.ACE);
            enemyTwoCards5[6] = new Card(Suit.CLUBS, Value.SEVEN);
            enemyTwoCards5[7] = new Card(Suit.HEARTS, Value.NINE);
            enemyTwoCards5[8] = new Card(Suit.SPADES, Value.SEVEN);
            enemyTwoCards5[9] = new Card(Suit.SPADES, Value.EIGHT);

            this.players[0].setHand(new Hand(playerCards5));
            this.players[1].setHand(new Hand(enemyOneCards5));
            this.players[2].setHand(new Hand(enemyTwoCards5));
            break;
          case 3:

            // lost of jacks
            Card[] playerCards6 = new Card[this.startHandSize];
            playerCards6[0] = new Card(Suit.CLUBS, Value.KING);
            playerCards6[1] = new Card(Suit.HEARTS, Value.TEN);
            playerCards6[2] = new Card(Suit.SPADES, Value.QUEEN);
            playerCards6[3] = new Card(Suit.HEARTS, Value.ACE);
            playerCards6[4] = new Card(Suit.DIAMONDS, Value.JACK);
            playerCards6[5] = new Card(Suit.CLUBS, Value.QUEEN);
            playerCards6[6] = new Card(Suit.SPADES, Value.JACK);
            playerCards6[7] = new Card(Suit.HEARTS, Value.JACK);
            playerCards6[8] = new Card(Suit.DIAMONDS, Value.QUEEN);
            playerCards6[9] = new Card(Suit.CLUBS, Value.JACK);

            // average cards
            Card[] enemyOneCards6 = new Card[this.startHandSize];
            enemyOneCards6[0] = new Card(Suit.DIAMONDS, Value.NINE);
            enemyOneCards6[1] = new Card(Suit.DIAMONDS, Value.EIGHT);
            enemyOneCards6[2] = new Card(Suit.CLUBS, Value.EIGHT);
            enemyOneCards6[3] = new Card(Suit.SPADES, Value.KING);
            enemyOneCards6[4] = new Card(Suit.CLUBS, Value.ACE);
            enemyOneCards6[5] = new Card(Suit.CLUBS, Value.TEN);
            enemyOneCards6[6] = new Card(Suit.SPADES, Value.NINE);
            enemyOneCards6[7] = new Card(Suit.SPADES, Value.TEN);
            enemyOneCards6[8] = new Card(Suit.HEARTS, Value.SEVEN);
            enemyOneCards6[9] = new Card(Suit.HEARTS, Value.EIGHT);

            // average cards
            Card[] enemyTwoCards6 = new Card[this.startHandSize];
            enemyTwoCards6[0] = new Card(Suit.DIAMONDS, Value.SEVEN);
            enemyTwoCards6[1] = new Card(Suit.HEARTS, Value.QUEEN);
            enemyTwoCards6[2] = new Card(Suit.DIAMONDS, Value.TEN);
            enemyTwoCards6[3] = new Card(Suit.CLUBS, Value.NINE);
            enemyTwoCards6[4] = new Card(Suit.DIAMONDS, Value.ACE);
            enemyTwoCards6[5] = new Card(Suit.SPADES, Value.ACE);
            enemyTwoCards6[6] = new Card(Suit.CLUBS, Value.SEVEN);
            enemyTwoCards6[7] = new Card(Suit.HEARTS, Value.NINE);
            enemyTwoCards6[8] = new Card(Suit.SPADES, Value.SEVEN);
            enemyTwoCards6[9] = new Card(Suit.SPADES, Value.EIGHT);

            this.players[0].setHand(new Hand(playerCards6));
            this.players[1].setHand(new Hand(enemyOneCards6));
            this.players[2].setHand(new Hand(enemyTwoCards6));
            break;
          case 4:
            // bad cards
            Card[] playerCards7 = new Card[this.startHandSize];
            playerCards7[0] = new Card(Suit.CLUBS, Value.NINE);
            playerCards7[1] = new Card(Suit.HEARTS, Value.TEN);
            playerCards7[2] = new Card(Suit.SPADES, Value.QUEEN);
            playerCards7[3] = new Card(Suit.HEARTS, Value.SEVEN);
            playerCards7[4] = new Card(Suit.DIAMONDS, Value.SEVEN);
            playerCards7[5] = new Card(Suit.CLUBS, Value.QUEEN);
            playerCards7[6] = new Card(Suit.SPADES, Value.SEVEN);
            playerCards7[7] = new Card(Suit.DIAMONDS, Value.NINE);
            playerCards7[8] = new Card(Suit.DIAMONDS, Value.QUEEN);
            playerCards7[9] = new Card(Suit.CLUBS, Value.EIGHT);

            // good cards
            Card[] enemyOneCards7 = new Card[this.startHandSize];
            enemyOneCards7[0] = new Card(Suit.HEARTS, Value.NINE);
            enemyOneCards7[1] = new Card(Suit.DIAMONDS, Value.EIGHT);
            enemyOneCards7[2] = new Card(Suit.CLUBS, Value.JACK);
            enemyOneCards7[3] = new Card(Suit.SPADES, Value.JACK);
            enemyOneCards7[4] = new Card(Suit.CLUBS, Value.ACE);
            enemyOneCards7[5] = new Card(Suit.CLUBS, Value.TEN);
            enemyOneCards7[6] = new Card(Suit.SPADES, Value.ACE);
            enemyOneCards7[7] = new Card(Suit.SPADES, Value.TEN);
            enemyOneCards7[8] = new Card(Suit.HEARTS, Value.ACE);
            enemyOneCards7[9] = new Card(Suit.HEARTS, Value.EIGHT);

            // good cards
            Card[] enemyTwoCards7 = new Card[this.startHandSize];
            enemyTwoCards7[0] = new Card(Suit.DIAMONDS, Value.JACK);
            enemyTwoCards7[1] = new Card(Suit.HEARTS, Value.QUEEN);
            enemyTwoCards7[2] = new Card(Suit.DIAMONDS, Value.TEN);
            enemyTwoCards7[3] = new Card(Suit.CLUBS, Value.KING);
            enemyTwoCards7[4] = new Card(Suit.DIAMONDS, Value.ACE);
            enemyTwoCards7[5] = new Card(Suit.SPADES, Value.NINE);
            enemyTwoCards7[6] = new Card(Suit.CLUBS, Value.SEVEN);
            enemyTwoCards7[7] = new Card(Suit.HEARTS, Value.JACK);
            enemyTwoCards7[8] = new Card(Suit.SPADES, Value.KING);
            enemyTwoCards7[9] = new Card(Suit.SPADES, Value.EIGHT);
            this.players[0].setHand(new Hand(playerCards7));
            this.players[1].setHand(new Hand(enemyOneCards7));
            this.players[2].setHand(new Hand(enemyTwoCards7));
            break;
          default:
            break;
        }
        break;

      case 1:
        /*
         * Players Hand: Herz_Bube, Herz_9, Herz_Ass, Herz_7, Pik_8, Pik_10 Ai 1 Hand: Herz_König,
         * Herz_10, Pik_Ass, Pik_Bube, Karo_7, Kreuz_8 Ai 2 Hand: Karo_8, Herz_Dame, Karo_10,
         * Kreuz_9, Karo_Ass, Kreuz_Ass
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
         * Players Hand: Herz_Bube, Herz_9, Herz_Ass, Herz_7, Pik_8, Pik_10 Ai 1 Hand: Herz_König,
         * Herz_10, Pik_Ass, Pik_Bube, Karo_7, Kreuz_8 Ai 2 Hand: Karo_8, Herz_Dame, Karo_10,
         * Kreuz_9, Karo_Ass, Kreuz_Ass
         */
        Card[] playerCards2 = new Card[this.startHandSize];
        playerCards2[0] = new Card(Suit.HEARTS, Value.JACK);
        playerCards2[1] = new Card(Suit.HEARTS, Value.NINE);
        playerCards2[2] = new Card(Suit.HEARTS, Value.ACE);
        playerCards2[3] = new Card(Suit.HEARTS, Value.SEVEN);
        playerCards2[4] = new Card(Suit.SPADES, Value.EIGHT);
        playerCards2[5] = new Card(Suit.SPADES, Value.TEN);


        Card[] enemyOneCards2 = new Card[this.startHandSize];
        enemyOneCards2[0] = new Card(Suit.HEARTS, Value.KING);
        enemyOneCards2[1] = new Card(Suit.HEARTS, Value.TEN);
        enemyOneCards2[2] = new Card(Suit.SPADES, Value.ACE);
        enemyOneCards2[3] = new Card(Suit.SPADES, Value.JACK);
        enemyOneCards2[4] = new Card(Suit.DIAMONDS, Value.SEVEN);
        enemyOneCards2[5] = new Card(Suit.CLUBS, Value.EIGHT);


        Card[] enemyTwoCards2 = new Card[this.startHandSize];
        enemyTwoCards2[0] = new Card(Suit.DIAMONDS, Value.EIGHT);
        enemyTwoCards2[1] = new Card(Suit.HEARTS, Value.QUEEN);
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
         * Ai 1 Hand: pik_7, Herz_9, Herz_König, Herz_7, Pik_Dame, Pik_10 Ai 2 Hand: Herz_Ass,
         * Herz_10, Pik_Ass, Karo_9 Karo_Ass,, Kreuz_9 Player Hand: Karo_8, Herz_8, Karo_König,
         * Kreuz_8, Karo_10, Kreuz_Ass
         * 
         */
        Card[] enemyTwoCards3 = new Card[this.startHandSize];
        enemyTwoCards3[0] = new Card(Suit.DIAMONDS, Value.EIGHT);
        enemyTwoCards3[1] = new Card(Suit.HEARTS, Value.EIGHT);
        enemyTwoCards3[2] = new Card(Suit.DIAMONDS, Value.KING);
        enemyTwoCards3[3] = new Card(Suit.CLUBS, Value.EIGHT);
        enemyTwoCards3[4] = new Card(Suit.DIAMONDS, Value.TEN);
        enemyTwoCards3[5] = new Card(Suit.CLUBS, Value.ACE);

        Card[] enemyOneCards3 = new Card[this.startHandSize];
        enemyOneCards3[0] = new Card(Suit.HEARTS, Value.ACE);
        enemyOneCards3[1] = new Card(Suit.HEARTS, Value.NINE);
        enemyOneCards3[2] = new Card(Suit.HEARTS, Value.KING);
        enemyOneCards3[3] = new Card(Suit.HEARTS, Value.SEVEN);
        enemyOneCards3[4] = new Card(Suit.SPADES, Value.QUEEN);
        enemyOneCards3[5] = new Card(Suit.SPADES, Value.TEN);

        Card[] playerCards3 = new Card[this.startHandSize];
        playerCards3[0] = new Card(Suit.SPADES, Value.ACE);
        playerCards3[1] = new Card(Suit.HEARTS, Value.TEN);
        playerCards3[2] = new Card(Suit.SPADES, Value.SEVEN);
        playerCards3[3] = new Card(Suit.DIAMONDS, Value.NINE);
        playerCards3[4] = new Card(Suit.DIAMONDS, Value.ACE);
        playerCards3[5] = new Card(Suit.CLUBS, Value.NINE);

        this.players[0].setHand(new Hand(playerCards3));
        this.players[1].setHand(new Hand(enemyOneCards3));
        this.players[2].setHand(new Hand(enemyTwoCards3));
        break;
      case 4:
        /*
         * Player Hand: Pik_Ass, Herz_10, Pik_Dame Karo_Ass, Karo_10, Kreuz_Ass Ai 2 Hand: Pik_Ten,
         * Herz_8, Herz_Ass, Herz_7, Kreuz_8, Kreuz_König Ai 1 Hand: Karo_9,Herz_9,Karo_Dame,
         * Kreuz_9, Karo_7, Kreuz_7
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
        // Player Hand: Herz_Ass, Herz_10, Herz_König, Pik_Ass,Pik_König, Karo_8,
        // Ai 1 Hand: Herz_7, Herz_8, Karo_9,Pik_Dame, Karo_7, Kreuz_9
        // Ai 2 Hand: Herz_9, Karo_Ass, Kreuz_Ass, Pik_10, Karo_10, Kreuz_8

        Card[] playerCards5 = new Card[this.startHandSize];
        playerCards5[0] = new Card(Suit.HEARTS, Value.ACE);
        playerCards5[1] = new Card(Suit.HEARTS, Value.TEN);
        playerCards5[2] = new Card(Suit.HEARTS, Value.KING);
        playerCards5[3] = new Card(Suit.SPADES, Value.ACE);
        playerCards5[4] = new Card(Suit.SPADES, Value.KING);
        playerCards5[5] = new Card(Suit.DIAMONDS, Value.EIGHT);

        Card[] enemyOneCards5 = new Card[this.startHandSize];
        enemyOneCards5[0] = new Card(Suit.HEARTS, Value.SEVEN);
        enemyOneCards5[1] = new Card(Suit.HEARTS, Value.EIGHT);
        enemyOneCards5[2] = new Card(Suit.DIAMONDS, Value.NINE);
        enemyOneCards5[3] = new Card(Suit.SPADES, Value.QUEEN);
        enemyOneCards5[4] = new Card(Suit.DIAMONDS, Value.SEVEN);
        enemyOneCards5[5] = new Card(Suit.CLUBS, Value.NINE);

        Card[] enemyTwoCards5 = new Card[this.startHandSize];
        enemyTwoCards5[0] = new Card(Suit.HEARTS, Value.NINE);
        enemyTwoCards5[1] = new Card(Suit.DIAMONDS, Value.ACE);
        enemyTwoCards5[2] = new Card(Suit.CLUBS, Value.ACE);
        enemyTwoCards5[3] = new Card(Suit.SPADES, Value.TEN);
        enemyTwoCards5[4] = new Card(Suit.DIAMONDS, Value.TEN);
        enemyTwoCards5[5] = new Card(Suit.CLUBS, Value.EIGHT);

        this.players[0].setHand(new Hand(playerCards5));
        System.out.println(players[0].getHand());
        this.players[1].setHand(new Hand(enemyOneCards5));
        System.out.println(players[1].getHand());
        this.players[2].setHand(new Hand(enemyTwoCards5));
        System.out.println(players[2].getHand());


        break;

      default:
        System.err.println(this.scenario + "the scenario does not exist.");
    }

  }

  @Override
  public void notifyRoundInstance(LogicAnswers answer) {
    synchronized (lock) {
      this.lock.notify();
    }
  }

}
