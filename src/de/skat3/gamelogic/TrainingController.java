package de.skat3.gamelogic;

import de.skat3.network.server.ServerLogicController;

public class TrainingController extends GameController implements GameLogicInterface {

  private int scenario;
  private TrainingModeThread trainingModeThread;
  private TrainingRoundInstance TrainingRoundInstance;

  public TrainingController(int scenario) {
    super();
    this.scenario = scenario;
  }
  
  @Override
  
  public void startGame(Player[] players, ServerLogicController slc) {

    if (players.length != 3 && players.length != 4) {
      if (players.length < 3) {
        System.err.println("LOGIC: Not enough layers connected: " + players.length);
        return;
      } else {
        System.err.println("LOGIC: Too many players connected: " + players.length);
        return;
      }
    }
    for (int i = 0; i < players.length; i++) {
      for (int j = i + 1; j < players.length; j++) {
        if (players[i].equals(players[j])) {
          System.err
              .println("LOGIC: Duplicate Player: " + players[i].name + ", " + players[i].getUuid());
          return;
        }
      }
    }
    this.numberOfPlayers = players.length;
    this.allPlayers = new Player[numberOfPlayers];
    for (int i = 0; i < players.length; i++) {
      this.allPlayers[i] = players[i].copyPlayer();
    }
    this.slc = slc;
    this.trainingModeThread.start();
  }

  
  void startNewRound() {
    this.TrainingRoundInstance = new TrainingRoundInstance(slc, this.players, this.trainingModeThread,
        this.kontraRekontraEnabled, this.mode);
    try {
      this.roundInstance.startRound();
    } catch (InterruptedException e) {
      System.err.println("LOGIC: Runde konnte nicht gestartet werden: " + e);
    }

  }

}
