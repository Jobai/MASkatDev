package de.skat3.gamelogic;

import de.skat3.network.server.ServerLogicController;

public class TrainingRoundInstance extends RoundInstance {

  int scenario;
  ServerLogicController slc;
  Player[] players;
  TrainingModeThread trainingModeThread;


  public TrainingRoundInstance(int scenario, ServerLogicController slc, Player[] players,
      TrainingModeThread trainingModeThread) {}

  void startRound() throws InterruptedException {

    this.initializeAuction();
    this.updatePlayer();
    this.updateEnemies();
    slc.broadcastRoundStarted();
    Player winner = this.startBidding();
    if (winner == null) {
      this.roundCancelled = true;
    } else {
      this.setDeclarer(winner);
      this.updatePlayer();
      this.updateEnemies();
      this.startGame();
    }
    this.slc.broadcastRoundResult(new Result(this));


  }

  @Override
  void initializeAuction() {


    switch (this.scenario) {

      case 0:
        /*
         * Players Hand: Herz_Bube, Herz_9, Herz_Ass, Herz_7, Pik_8, Pik_10
         * 
         * Ai 1 Hand: Herz_König, Herz_10, Pik_Ass, Karo_9, Karo_10
         * 
         * Ai 2 Hand: Karo_8, Herz_9, Karo_10, Kreuz_9, Karo_Ass, Kreuz_Ass
         */
        
        
        break;

      case 1:
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
