package de.skat3.gamelogic;

public class TrainingModeThread extends Thread {

  TrainingController tc;
  Object lock = new Object();

  public TrainingModeThread(TrainingController tc) {

    this.tc = tc;
  }

  @Override
  public void run() {
    synchronized (lock) {
      tc.startNewRound();
    }
  }
}

