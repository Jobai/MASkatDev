package de.skat3.gamelogic;

import de.skat3.network.server.ServerLogicController;

public class TrainingController extends GameController implements GameLogicInterface {

  private int scenario;
  private TrainingModeThread trainingModeThread;

  public TrainingController(int scenario) {
    super();
    this.scenario = scenario;
    this.numberOfPlayers = 3;
    this.players = new Player[numberOfPlayers];
    this.trainingModeThread = new TrainingModeThread(this);
  }

  @Override

  public void startGame(Player[] players, ServerLogicController slc) {

    if (players.length != 3 && players.length != 4) {
      if (players.length < 3) {
        System.err.println("LOGIC: Not enough players connected: " + players.length);
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
    for (int i = 0; i < players.length; i++) {
      this.players[i] = players[i];
    }
    this.slc = slc;
    this.trainingModeThread.start();
  }


  void startNewRound() {
    this.roundInstance =
        new TrainingRoundInstance(this.scenario, slc, this.players);
    try {
      this.roundInstance.startRound();
    } catch (InterruptedException e) {
      System.err.println("LOGIC: Runde konnte nicht gestartet werden: " + e);
    }

  }

}
